package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.commands.DisconnectCommand;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.PlayerManager;
import com.danielvandenbrink.corena.server.World;

import java.net.SocketAddress;

public class DisconnectCommandHandler implements CommandHandler<DisconnectCommand> {
    private final CommandCommunicator comm;
    private final PlayerManager playerManager;
    private final World world;

    public DisconnectCommandHandler(CommandCommunicator comm, PlayerManager playerManager, World world) {
        this.comm = comm;
        this.playerManager = playerManager;
        this.world = world;
    }

    @Override
    public void handle(DisconnectCommand command, SocketAddress address) {
        world.removeEntity(playerManager.get(command.uuid()).entity());
        world.setDirty(true);

        playerManager.remove(command.uuid());

        comm.send(new GameStateCommand(world.getObjects()));
    }
}
