package com.danielvandenbrink.corena.communication;

import java.net.SocketAddress;

public interface CommandHandler<T extends Command> {
    void handle(T command, SocketAddress address);
}
