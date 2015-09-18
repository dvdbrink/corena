package com.danielvandenbrink.corena.communication;

import java.net.SocketAddress;

public interface CommandCommunicator {
    void send(Command command);
    void send(Command command, SocketAddress address);
}
