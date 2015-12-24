package com.danielvandenbrink.corena;

public class Vector2f {
    private float x;
    private float y;

    public Vector2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public void x(final float x) {
        this.x = x;
    }

    public float y() {
        return y;
    }

    public void y(final float y) {
        this.y = y;
    }

    public float length() {
        return x * x + y * y;
    }

    public void normalise() {
        final float length = length();
        x /= length;
        y /= length;
    }

    @Override
    public String toString() {
        return String.format("Vector2f(%s, %s)", x, y);
    }
}
