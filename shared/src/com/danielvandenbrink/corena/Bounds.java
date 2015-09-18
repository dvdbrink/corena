package com.danielvandenbrink.corena;

public class Bounds {
    public float x;
    public float y;
    public float width;
    public float height;

    public Bounds(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(final Bounds other) {
        return other.x >= this.x && other.x < this.x + this.width &&
               other.y >= this.y && other.y < this.y + this.height;
    }
}
