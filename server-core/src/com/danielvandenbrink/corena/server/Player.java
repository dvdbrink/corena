package com.danielvandenbrink.corena.server;

import com.danielvandenbrink.corena.Vector2f;

import java.net.SocketAddress;

public class Player {
    private final long uuid;
    private final SocketAddress address;
    private final String name;

    private float x;
    private float y;
    private float rotation;

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

    public float rotation() {
        return rotation;
    }

    public Vector2f direction() {
        final float x = (float) Math.cos(Math.toRadians(rotation));
        final float y = (float) Math.sin(Math.toRadians(rotation));

        Vector2f direction = new Vector2f(x, y);
        if (direction.length() > 0) {
            direction.normalise();
        }

        return direction;
    }

    public void rotation(float rotation) {
        this.rotation = rotation;
    }

    public SocketAddress address() {
        return address;
    }

    public String name() {
        return name;
    }
}
