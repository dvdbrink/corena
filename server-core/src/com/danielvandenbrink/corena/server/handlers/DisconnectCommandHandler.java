package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.commands.DisconnectCommand;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.PlayerManager;

import java.net.SocketAddress;

public class DisconnectCommandHandler implements CommandHandler<DisconnectCommand> {
    private final CommandCommunicator comm;
    private final PlayerManager playerManager;

    public DisconnectCommandHandler(CommandCommunicator comm, PlayerManager playerManager) {
        this.comm = comm;
        this.playerManager = playerManager;
    }

    @Override
    public void handle(DisconnectCommand command, SocketAddress address) {
        playerManager.remove(command.uuid());
        comm.send(new GameStateCommand(playerManager.entities()));
    }
}
