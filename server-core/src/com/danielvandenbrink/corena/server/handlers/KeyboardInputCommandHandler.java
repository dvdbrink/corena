package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.Action;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.commands.KeyboardInputCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.PlayerManager;

import java.net.SocketAddress;

public class KeyboardInputCommandHandler implements CommandHandler<KeyboardInputCommand> {
    private static final float SPEED = 6f;
    private static final float ROT_SPEED = 4f;

    private final CommandCommunicator comm;
    private final PlayerManager playerManager;

    public KeyboardInputCommandHandler(CommandCommunicator comm, PlayerManager playerManager) {
        this.comm = comm;
        this.playerManager = playerManager;
    }

    @Override
    public void handle(KeyboardInputCommand command, SocketAddress address) {
        final Player player = playerManager.get(command.uuid());

        if (player != null) {
            for (Action action : command.keys()) {
                switch (action) {
                    case FORWARD:
                        player.x(player.x() - (player.direction().x() * SPEED));
                        player.y(player.y() - (player.direction().y() * SPEED));
                        break;
                    case LEFT:
                        player.rotation(player.rotation() + ROT_SPEED);
                        break;
                    case BACKWARD:
                        player.x(player.x() + (player.direction().x() * SPEED));
                        player.y(player.y() + (player.direction().y() * SPEED));
                        break;
                    case RIGHT:
                        player.rotation(player.rotation() - ROT_SPEED);
                        break;
                    case SHOOT:
                        break;
                    default:
                        return;
                }
            }

            playerManager.dirty(true);

            //System.out.println("Sending gamestate");
            //comm.send(new GameStateCommand(playerManager.entities()));
        }
    }
}
