package com.danielvandenbrink.corena;

public class GameObject {
    private final static float DEFAULT_SCALE = 1.0f;
    private final static float DEFAULT_ROTATION = 0.0f;

    private final long uuid;
    private String textureName;
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;
    private float rotation;

    public GameObject(final long uuid, final String textureName, final float x, final float y) {
        this(uuid, textureName, x, y, DEFAULT_SCALE, DEFAULT_SCALE, DEFAULT_ROTATION);
    }

    public GameObject(final long uuid, final String textureName, final float x, final float y, final float scaleX, final float scaleY) {
        this(uuid, textureName, x, y, scaleX, scaleY, DEFAULT_ROTATION);
    }

    public GameObject(final long uuid, final String textureName, final float x, final float y, final float rotation) {
        this(uuid, textureName, x, y, DEFAULT_SCALE, DEFAULT_SCALE, rotation);
    }

    public GameObject(final long uuid, final String textureName, final float x, final float y, final float scaleX, final float scaleY, final float rotation) {
        this.uuid = uuid;
        this.textureName = textureName;
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
    }

    public long uuid() {
        return uuid;
    }

    public String textureName() {
        return textureName;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float scaleX() {
        return scaleX;
    }

    public float scaleY() {
        return scaleY;
    }

    public float rotation() {
        return rotation;
    }
}
