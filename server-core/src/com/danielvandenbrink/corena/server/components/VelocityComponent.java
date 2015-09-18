package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent extends Vector2 implements Component {
    public VelocityComponent() {
        super();
    }

    public VelocityComponent(float x, float y) {
        super(x, y);
    }
}
