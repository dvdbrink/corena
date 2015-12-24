package com.danielvandenbrink.corena.server;

import com.badlogic.ashley.core.Engine;
import com.danielvandenbrink.corena.commands.*;
import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandlerRegistry;
import com.danielvandenbrink.corena.communication.CommandParser;
import com.danielvandenbrink.corena.server.handlers.ConnectCommandHandler;
import com.danielvandenbrink.corena.server.handlers.DisconnectCommandHandler;
import com.danielvandenbrink.corena.server.handlers.KeyboardInputCommandHandler;
import com.danielvandenbrink.corena.server.handlers.MouseInputCommandHandler;
import com.danielvandenbrink.xudp.PacketEvent;
import com.danielvandenbrink.xudp.impl.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameServer implements CommandCommunicator, Runnable {
    public static final int TICKS_PER_SECOND = 120;

    private final PlayerManager playerManager;
    private final Engine engine;
    private final CommandParser commandParser;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final UdpSocket<UdpPacket> socket;

    private boolean running;

    public GameServer(final int port) {
        playerManager = new PlayerManager();
        engine = new Engine();

        commandParser = new CommandParser();
        commandHandlerRegistry = new CommandHandlerRegistry();
        commandHandlerRegistry.register(ConnectCommand.class, new ConnectCommandHandler(this, playerManager));
        commandHandlerRegistry.register(DisconnectCommand.class, new DisconnectCommandHandler(this, playerManager));
        commandHandlerRegistry.register(KeyboardInputCommand.class, new KeyboardInputCommandHandler(this, playerManager));
        commandHandlerRegistry.register(MouseInputCommand.class, new MouseInputCommandHandler(this, playerManager));

        socket = new UdpSocket<UdpPacket>(new UdpPacketEncoder(), new UdpPacketDecoder(), new UdpPacketHandler(),
                new SelectorFactory(), new DatagramChannelFactory(), new UdpPacketFactory(),
                new UdpPacketEventFactory()) {
            @Override
            public void handlePacketEvent(PacketEvent e) {
                Command cmd = commandParser.decode(e.packet().data());
                commandHandlerRegistry.dispatch(cmd, e.from());
            }
        };
        socket.open();
        socket.bind(new InetSocketAddress(port));

        running = false;
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

    private void tick() {
        socket.read();

        if (playerManager.dirty()) {
            send(new GameStateCommand(playerManager.entities()));
            playerManager.dirty(false);
        }

        socket.write();
    }

    public void start() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 1000 / TICKS_PER_SECOND, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            tick();
        }
    }
}
