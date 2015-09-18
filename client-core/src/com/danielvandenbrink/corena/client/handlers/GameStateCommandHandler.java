package com.danielvandenbrink.corena.client.handlers;

import com.danielvandenbrink.corena.GameObject;
import com.danielvandenbrink.corena.client.EntityManager;
import com.danielvandenbrink.corena.commands.GameStateCommand;
import com.danielvandenbrink.corena.communication.CommandHandler;

import java.net.SocketAddress;

public class GameStateCommandHandler implements CommandHandler<GameStateCommand> {
    private final EntityManager entityManager;

    public GameStateCommandHandler(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void handle(GameStateCommand command, SocketAddress address) {
        entityManager.clear();
        for (final GameObject entity : command.entities()) {
            entityManager.add(entity);
        }
    }
}
