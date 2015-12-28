package com.danielvandenbrink.corena.server;

import com.danielvandenbrink.corena.commands.*;
import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandlerRegistry;
import com.danielvandenbrink.corena.communication.CommandParser;
import com.danielvandenbrink.corena.server.handlers.ConnectCommandHandler;
import com.danielvandenbrink.corena.server.handlers.DisconnectCommandHandler;
import com.danielvandenbrink.corena.server.handlers.KeyboardInputCommandHandler;
import com.danielvandenbrink.corena.server.handlers.MouseInputCommandHandler;
import com.danielvandenbrink.corena.server.systems.MovementSystem;
import com.danielvandenbrink.corena.util.Time;
import com.danielvandenbrink.xudp.PacketEvent;
import com.danielvandenbrink.xudp.Socket;
import com.danielvandenbrink.xudp.impl.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class GameServer implements CommandCommunicator, Runnable {
    private static final float TICKS_PER_SECOND = 60.0f;

    private final PlayerManager playerManager;
    private final World world;
    private final EntityFactory entityFactory;
    private final CommandParser commandParser;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final Socket socket;

    private boolean running;

    public GameServer(final int port) {
        playerManager = new PlayerManager();
        world = new World();
        entityFactory = new EntityFactory();
        commandParser = new CommandParser();
        commandHandlerRegistry = new CommandHandlerRegistry();
        socket = new UdpSocket<>(new UdpPacketEncoder(), new UdpPacketDecoder(), new UdpPacketHandler(),
                new SelectorFactory(), new DatagramChannelFactory(), new UdpPacketFactory(),
                new UdpPacketEventFactory());

        running = false;

        registerSystems();
        registerCommandHandlers();
        initSocket(port);
    }

    @Override
    public void send(final Command command) {
        for (final Player player : playerManager) {
            send(command, player.address());
        }
    }

    @Override
    public void send(final Command command, final SocketAddress to) {
        socket.send(command.protocol(), commandParser.encode(command), to);
    }

    @Override
    public void run() {
        final float deltaTime = 1.0f / TICKS_PER_SECOND;

        double accumulator = 0.0;
        double previousTime = 0.0;

        running = true;
        while (running) {
            double currentTime = Time.millis() / 1000.0;
            double tickTime = Math.min(currentTime - previousTime, 0.25);
            previousTime = currentTime;

            accumulator += tickTime;

            while (accumulator >= deltaTime) {
                tick(deltaTime);
                accumulator -= deltaTime;
            }
        }
    }

    private void registerSystems() {
        world.addSystem(new MovementSystem());
    }

    private void registerCommandHandlers() {
        commandHandlerRegistry.register(ConnectCommand.class, new ConnectCommandHandler(this, playerManager, world, entityFactory));
        commandHandlerRegistry.register(DisconnectCommand.class, new DisconnectCommandHandler(this, playerManager, world));
        commandHandlerRegistry.register(KeyboardInputCommand.class, new KeyboardInputCommandHandler(playerManager, world));
        commandHandlerRegistry.register(MouseInputCommand.class, new MouseInputCommandHandler(playerManager, world));
    }

    private void initSocket(final int port) {
        socket.open();
        socket.bind(new InetSocketAddress(port));
    }

    private void tick(float dt) {
        socket.read(e -> {
            Command cmd = commandParser.decode(e.packet().data());
            commandHandlerRegistry.dispatch(cmd, e.from());
        });

        world.update(dt);

        send(new GameStateCommand(world.getObjects()));

        socket.write();
    }
}
