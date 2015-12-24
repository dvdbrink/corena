package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.commands.AuthorizedCommand;
import com.danielvandenbrink.corena.commands.ConnectCommand;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.PlayerManager;

import java.net.SocketAddress;

public class ConnectCommandHandler implements CommandHandler<ConnectCommand> {
    private final CommandCommunicator comm;
    private final PlayerManager playerManager;

    public ConnectCommandHandler(CommandCommunicator comm, PlayerManager playerManager) {
        this.comm = comm;
        this.playerManager = playerManager;
    }

    @Override
    public void handle(ConnectCommand command, SocketAddress address) {
        final Player player = playerManager.add(address, command.name());
        comm.send(new AuthorizedCommand(player.uuid()), player.address());
        comm.send(new GameStateCommand(playerManager.entities()));
    }
}
