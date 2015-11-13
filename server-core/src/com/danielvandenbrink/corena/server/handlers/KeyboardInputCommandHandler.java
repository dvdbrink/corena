package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.commands.KeyboardInputCommand;
import com.danielvandenbrink.corena.commands.MouseInputCommand;
import com.danielvandenbrink.corena.communication.CommandCommunicator;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.managers.PlayerManager;

import java.net.SocketAddress;

public class KeyboardInputCommandHandler implements CommandHandler<KeyboardInputCommand> {
    private static final int W = 51;
    private static final int A = 29;
    private static final int S = 47;
    private static final int D = 32;
    private static final int SPEED = 15;

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
            for (int key : command.keys()) {
                switch (key) {
                    case W:
                        player.y(player.y() + SPEED);
                        break;
                    case A:
                        player.x(player.x() - SPEED);
                        break;
                    case S:
                        player.y(player.y() - SPEED);
                        break;
                    case D:
                        player.x(player.x() + SPEED);
                        break;
                    default:
                        return;
                }
            }

            comm.send(new GameStateCommand(playerManager.entities()));
        }
    }
}
