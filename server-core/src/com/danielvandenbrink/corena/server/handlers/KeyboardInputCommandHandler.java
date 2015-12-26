package com.danielvandenbrink.corena.server.handlers;

import com.danielvandenbrink.corena.Action;
import com.danielvandenbrink.corena.commands.KeyboardInputCommand;
import com.danielvandenbrink.corena.communication.CommandHandler;
import com.danielvandenbrink.corena.server.Player;
import com.danielvandenbrink.corena.server.PlayerManager;
import com.danielvandenbrink.corena.server.World;
import com.danielvandenbrink.corena.server.components.AccelerationComponent;
import com.danielvandenbrink.corena.server.components.PositionComponent;

import java.net.SocketAddress;

public class KeyboardInputCommandHandler implements CommandHandler<KeyboardInputCommand> {
    private final PlayerManager playerManager;
    private final World world;

    private static final float MAX_ACCEL = 2f;
    private static final float ACCEL_INCREMENT = 0.025f;

    public KeyboardInputCommandHandler(PlayerManager playerManager, World world) {
        this.playerManager = playerManager;
        this.world = world;
    }

    @Override
    public void handle(KeyboardInputCommand command, SocketAddress address) {
        final Player player = playerManager.get(command.uuid());

        if (player != null) {
            AccelerationComponent acceleration = player.entity().getComponent(AccelerationComponent.class);
            for (Action action : command.keys()) {
                switch (action) {
                    case FORWARD:
                        acceleration.y = Math.min(acceleration.y + ACCEL_INCREMENT, MAX_ACCEL);
                        break;
                    case LEFT:
                        acceleration.x = Math.max(acceleration.x - ACCEL_INCREMENT, -MAX_ACCEL);
                        break;
                    case BACKWARD:
                        acceleration.y = Math.max(acceleration.y - ACCEL_INCREMENT, -MAX_ACCEL);
                        break;
                    case RIGHT:
                        acceleration.x = Math.min(acceleration.x + ACCEL_INCREMENT, MAX_ACCEL);
                        break;
                    case SHOOT:
                        break;
                    default:
                        return;
                }
            }

            world.setDirty(true);
        }
    }
}
