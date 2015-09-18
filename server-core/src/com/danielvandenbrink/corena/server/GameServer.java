package com.danielvandenbrink.corena.server;

import com.danielvandenbrink.corena.commands.ConnectCommand;
import com.danielvandenbrink.corena.commands.DisconnectCommand;
import com.danielvandenbrink.corena.commands.InputCommand;
import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandlerRegistry;
import com.danielvandenbrink.corena.communication.CommandParser;
import com.danielvandenbrink.corena.server.handlers.ConnectCommandHandler;
import com.danielvandenbrink.corena.server.handlers.DisconnectCommandHandler;
import com.danielvandenbrink.corena.server.handlers.InputCommandHandler;
import com.danielvandenbrink.corena.server.managers.PlayerManager;
import com.danielvandenbrink.xudp.PacketEvent;
import com.danielvandenbrink.xudp.PacketEventHandler;
import com.danielvandenbrink.xudp.Server;
import com.danielvandenbrink.xudp.impl.UdpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

public class GameServer implements CommandCommunicator {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CommandParser commandParser;
    private final CommandHandlerRegistry commandHandlerRegistry;
    private final Server server;
    private final PlayerManager playerManager;

    public GameServer(final int port) {
        playerManager = new PlayerManager();

        commandParser = new CommandParser();

        commandHandlerRegistry = new CommandHandlerRegistry();
        commandHandlerRegistry.register(ConnectCommand.class, new ConnectCommandHandler(this, playerManager));
        commandHandlerRegistry.register(DisconnectCommand.class, new DisconnectCommandHandler(this, playerManager));
        commandHandlerRegistry.register(InputCommand.class, new InputCommandHandler(this, playerManager));

        server = new UdpServer();
        server.onPacket(new PacketEventHandler() {
            @Override
            public void handle(PacketEvent e) {
                Command cmd = commandParser.decode(e.packet().data());
                commandHandlerRegistry.dispatch(cmd, e.from());
            }
        });
        server.listen(port);
        server.start();
    }

    @Override
    public void send(final Command command) {
        for (final Player player : playerManager) {
            send(command, player.address());
        }
    }

    @Override
    public void send(final Command command, final SocketAddress to) {
        server.send(command.protocol(), commandParser.encode(command), to);
    }

    public void stop() {
        server.stop();
    }
}
