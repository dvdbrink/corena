package com.danielvandenbrink.corena;

public class GameObject {
    private final long uuid;
    private float x;
    private float y;

    public GameObject(final long uuid, final float x, final float y) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public long uuid() {
        return uuid;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
}
