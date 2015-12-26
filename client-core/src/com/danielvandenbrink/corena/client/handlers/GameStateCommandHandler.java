package com.danielvandenbrink.corena.client.handlers;

import com.danielvandenbrink.corena.GameObject;
import com.danielvandenbrink.corena.client.GameObjectManager;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.communication.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

public class GameStateCommandHandler implements CommandHandler<GameStateCommand> {
    private final GameObjectManager gameObjectManager;

    public GameStateCommandHandler(final GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }

    @Override
    public void handle(GameStateCommand command, SocketAddress address) {
        for (final GameObject entity : command.objects()) {
            gameObjectManager.addOrUpdate(entity);
        }
    }
}
