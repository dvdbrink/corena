package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class AccelerationComponent extends Vector2 implements Component {
    public AccelerationComponent() {
        super();
    }

    public AccelerationComponent(float x, float y) {
        super(x, y);
    }
}