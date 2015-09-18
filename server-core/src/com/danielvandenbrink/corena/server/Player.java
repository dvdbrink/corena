package com.danielvandenbrink.corena.server;

import java.net.SocketAddress;

public class Player {
    private final long uuid;
    private final SocketAddress address;
    private final String name;

    private int x;
    private int y;

    public Player(final long uuid, final SocketAddress address, final String name) {
        this.uuid = uuid;
        this.address = address;
        this.name = name;
    }

    public long uuid() {
        return uuid;
    }

    public int x() {
        return x;
    }

    public void x(int x) {
        this.x = x;
    }

    public int y() {
        return y;
    }

    public void y(int y) {
        this.y = y;
    }

    public SocketAddress address() {
        return address;
    }

    public String name() {
        return name;
    }
}
