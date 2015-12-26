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
import com.danielvandenbrink.corena.server.systems.FrictionSystem;
import com.danielvandenbrink.corena.server.systems.MovementSystem;
import com.danielvandenbrink.corena.util.Time;
import com.danielvandenbrink.xudp.PacketEvent;
import com.danielvandenbrink.xudp.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class GameServer implements CommandCommunicator, Runnable {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final PlayerManager playerManager;
    private final World world;
    private final EntityFactory entityFactory;
    private final CommandParser commandParser;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final UdpSocket<UdpPacket> socket;

    private boolean running;

    public GameServer(final int port) {
        playerManager = new PlayerManager();
        world = new World();
        entityFactory = new EntityFactory();
        commandParser = new CommandParser();
        commandHandlerRegistry = new CommandHandlerRegistry();
        socket = new UdpSocket<UdpPacket>(new UdpPacketEncoder(), new UdpPacketDecoder(), new UdpPacketHandler(),
                new SelectorFactory(), new DatagramChannelFactory(), new UdpPacketFactory(),
                new UdpPacketEventFactory()) {
            @Override
            public void handlePacketEvent(PacketEvent e) {
                Command cmd = commandParser.decode(e.packet().data());
                commandHandlerRegistry.dispatch(cmd, e.from());
            }
        };

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
        // Gaat niet goed wanneer de ticks per seconde hoger is dan de frames per seconde van de client.
        // Maar de client zou dan 2x een game state moeten ontvanger per frame in het geval van 120TPS en 60FPS, toch?
        // De client ontvangt echter 1 game state per frame. Waarom? <- Uitzoeken!
        final float ticksPerSecond = 120.0f;
        final float deltaTime = 1.0f / ticksPerSecond;

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
        //world.addSystem(new FrictionSystem());
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
        socket.read();
        world.update(dt);
        send(new GameStateCommand(world.getObjects()));
        socket.write();
    }
}
