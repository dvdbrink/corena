package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends Vector2 implements Component {
    public PositionComponent() {
        super();
    }

    public PositionComponent(float x, float y) {
        super(x, y);
    }
}
