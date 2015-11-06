package com.danielvandenbrink.corena.server;

import java.net.SocketAddress;

public class Player {
    private final long uuid;
    private final SocketAddress address;
    private final String name;

    private float x;
    private float y;

    public Player(final long uuid, final SocketAddress address, final String name) {
        this.uuid = uuid;
        this.address = address;
        this.name = name;
    }

    public long uuid() {
        return uuid;
    }

    public float x() {
        return x;
    }

    public void x(float x) {
        this.x = x;
    }

    public float y() {
        return y;
    }

    public void y(float y) {
        this.y = y;
    }

    public SocketAddress address() {
        return address;
    }

    public String name() {
        return name;
    }
}
