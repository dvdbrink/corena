package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;

public class TextureComponent implements Component {
    public String name;

    public TextureComponent(String name) {
        this.name = name;
    }
}
