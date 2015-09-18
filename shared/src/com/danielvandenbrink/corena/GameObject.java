package com.danielvandenbrink.corena;

public class GameObject {
    private final long uuid;
    private int x;
    private int y;

    public GameObject(final long uuid, final int x, final int y) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public long uuid() {
        return uuid;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
