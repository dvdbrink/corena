package com.danielvandenbrink.corena.client;

import com.danielvandenbrink.corena.client.handlers.GameStateCommandHandler;
import com.danielvandenbrink.corena.commands.AuthorizedCommand;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.communication.*;
import com.danielvandenbrink.xudp.Client;
import com.danielvandenbrink.xudp.PacketEvent;
import com.danielvandenbrink.xudp.PacketEventHandler;
import com.danielvandenbrink.xudp.impl.UdpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

public class GameClient implements CommandCommunicator {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CommandParser commandParser;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final Client client;

    private long uuid = -1;

    public GameClient(final String host, final int port, final EntityManager entityManager) {
        commandParser = new CommandParser();

        commandHandlerRegistry = new CommandHandlerRegistry();
        commandHandlerRegistry.register(GameStateCommand.class, new GameStateCommandHandler(entityManager));
        commandHandlerRegistry.register(AuthorizedCommand.class, new CommandHandler<AuthorizedCommand>() {
            @Override
            public void handle(AuthorizedCommand command, SocketAddress address) {
                uuid = command.uuid();
            }
        });

        client = new UdpClient();
        client.onPacket(new PacketEventHandler() {
            @Override
            public void handle(PacketEvent e) {
                Command cmd = commandParser.decode(e.packet().data());
                commandHandlerRegistry.dispatch(cmd, e.from());
            }
        });
        client.connect(host, port);
        client.start();
    }

    @Override
    public void send(Command command) {
        client.send(command.protocol(), commandParser.encode(command));
    }

    @Override
    public void send(Command command, SocketAddress address) {
        throw new IllegalStateException("Send(command, address) not available for client");
    }

    public void stop() {
        client.stop();
    }

    public long uuid() {
        return uuid;
    }
}
