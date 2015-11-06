package com.danielvandenbrink.corena.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.commands.MouseInputCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.managers.PlayerManager;

import java.net.SocketAddress;

public class MouseInputCommandHandler implements CommandHandler<MouseInputCommand> {
    private static final int SPEED = 15;

    private final CommandCommunicator comm;
    private final PlayerManager playerManager;

    public MouseInputCommandHandler(CommandCommunicator comm, PlayerManager playerManager) {
        this.comm = comm;
        this.playerManager = playerManager;
    }

    @Override
    public void handle(MouseInputCommand command, SocketAddress address) {
        final Player player = playerManager.get(command.uuid());
        if (player != null) {
            Vector2 newPos = new Vector2(player.x(), player.y()).sub(command.x(), command.y()).nor();
            player.x(newPos.x);
            player.y(newPos.y);

            comm.send(new GameStateCommand(playerManager.entities()));
        }
    }
}
