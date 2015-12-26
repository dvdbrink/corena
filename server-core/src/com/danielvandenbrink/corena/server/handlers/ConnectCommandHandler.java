package com.danielvandenbrink.corena.server.handlers;

import com.badlogic.ashley.core.Entity;
import com.danielvandenbrink.corena.commands.AuthorizedCommand;
import com.danielvandenbrink.corena.commands.ConnectCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.EntityFactory;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.PlayerManager;
import com.danielvandenbrink.corena.server.World;

import java.net.SocketAddress;

public class ConnectCommandHandler implements CommandHandler<ConnectCommand> {
    private final CommandCommunicator comm;
    private final PlayerManager playerManager;
    private final World world;
    private final EntityFactory entityFactory;

    public ConnectCommandHandler(CommandCommunicator comm, PlayerManager playerManager, World world, EntityFactory entityFactory) {
        this.comm = comm;
        this.playerManager = playerManager;
        this.world = world;
        this.entityFactory = entityFactory;
    }

    @Override
    public void handle(ConnectCommand command, SocketAddress address) {
        final Player player = playerManager.add(address, command.name());
        final Entity entity = entityFactory.createPlayer(player.uuid());

        player.entity(entity);
        world.addEntity(entity);
        world.setDirty(true);

        comm.send(new AuthorizedCommand(player.uuid()), player.address());
    }
}
