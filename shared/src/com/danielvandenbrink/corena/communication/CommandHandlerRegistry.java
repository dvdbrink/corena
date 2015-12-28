package com.danielvandenbrink.corena.communication;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public final class CommandHandlerRegistry {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> handlers = new HashMap<>();

    public <T extends Command> void register(final Class<T> type, final CommandHandler<T> handler) {
        if (type == null || handler == null) {
            throw new IllegalArgumentException();
        }
        handlers.put(type, handler);
    }

    // Type safety is assured by the register message and generic bounds
    @SuppressWarnings("unchecked")
    public <T extends Command> void dispatch(final T command, final SocketAddress address) {
        CommandHandler<T> handler = (CommandHandler<T>)handlers.get(command.getClass());
        if (handler != null) {
            handler.handle(command, address);
        }
    }
}
