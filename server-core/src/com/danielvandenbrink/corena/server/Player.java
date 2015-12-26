package com.danielvandenbrink.corena.server;

import com.badlogic.ashley.core.Entity;

import java.net.SocketAddress;

public class Player {
    private final long uuid;
    private final SocketAddress address;
    private final String name;

    private Entity entity;

    public Player(final long uuid, final SocketAddress address, final String name) {
        this.uuid = uuid;
        this.address = address;
        this.name = name;
    }

    public long uuid() {
        return uuid;
    }

    public SocketAddress address() {
        return address;
    }

    public String name() {
        return name;
    }

    public Entity entity() {
        return entity;
    }

    public void entity(Entity entity) {
        this.entity = entity;
    }
}
