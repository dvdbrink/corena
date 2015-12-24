package com.danielvandenbrink.corena.client;

import com.badlogic.gdx.InputAdapter;

import java.util.HashMap;
import java.util.Map;

public class Player extends InputAdapter {
    private final long uuid;
    private final String name;

    private final Map<Integer, Boolean> keys;

    public Player(long uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        keys = new HashMap<>();
    }

    public long uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }

    public boolean keyDown(int keycode) {
        keys.put(keycode, true);
        return true;
    }

    public boolean keyUp(int keycode) {
        keys.put(keycode, false);
        return true;
    }
}
