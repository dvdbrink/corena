package com.danielvandenbrink.corena.server.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danielvandenbrink.corena.server.components.AccelerationComponent;

public class FrictionSystem extends IteratingSystem {
    private final ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);

    public FrictionSystem () {
        super(Family.all(AccelerationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AccelerationComponent acceleration = am.get(entity);

        if (acceleration.x > 0) {
            acceleration.x = Math.min(acceleration.x - (0.1f * deltaTime), 0);
        }
        else if (acceleration.x < 0) {
            acceleration.x = Math.max(acceleration.x + (0.1f * deltaTime), 0);
        }

        if (acceleration.y > 0) {
            acceleration.y = Math.min(acceleration.y - (0.1f * deltaTime), 0);
        }
        else if (acceleration.y < 0) {
            acceleration.y = Math.max(acceleration.y + (0.1f * deltaTime), 0);
        }
    }
}
