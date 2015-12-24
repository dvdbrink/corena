package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    public String name;

    public PlayerComponent(String name) {
        this.name = name;
    }
}
