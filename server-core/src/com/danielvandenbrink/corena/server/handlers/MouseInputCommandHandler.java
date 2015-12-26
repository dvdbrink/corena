package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.commands.MouseInputCommand;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.PlayerManager;
import com.danielvandenbrink.corena.server.World;

import java.net.SocketAddress;

public class MouseInputCommandHandler implements CommandHandler<MouseInputCommand> {
    private final PlayerManager playerManager;
    private final World world;

    public MouseInputCommandHandler(PlayerManager playerManager, World world) {
        this.playerManager = playerManager;
        this.world = world;
    }

    @Override
    public void handle(MouseInputCommand command, SocketAddress address) {
        final Player player = playerManager.get(command.uuid());
        if (player != null) {

        }
    }
}
