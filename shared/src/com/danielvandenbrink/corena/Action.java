package com.danielvandenbrink.corena;

public enum Action {
    UNKNOWN(-1),
    FORWARD(0),
    LEFT(1),
    BACKWARD(2),
    RIGHT(3),
    SHOOT(4);

    private final int id;

    Action(final int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static Action fromId(final int id) {
        switch (id) {
            case 0: return FORWARD;
            case 1: return LEFT;
            case 2: return BACKWARD;
            case 3: return RIGHT;
            case 4: return SHOOT;
            default: return UNKNOWN;
        }
    }
}