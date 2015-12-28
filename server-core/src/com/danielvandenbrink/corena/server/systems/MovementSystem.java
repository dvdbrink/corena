package com.danielvandenbrink.corena.server.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danielvandenbrink.corena.server.components.AccelerationComponent;
import com.danielvandenbrink.corena.server.components.PositionComponent;
import com.danielvandenbrink.corena.server.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);

    public MovementSystem () {
        super(Family.all(PositionComponent.class, VelocityComponent.class, AccelerationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        AccelerationComponent acceleration = am.get(entity);

        velocity.x += acceleration.x * deltaTime;
        velocity.y += acceleration.y * deltaTime;

        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }
}
