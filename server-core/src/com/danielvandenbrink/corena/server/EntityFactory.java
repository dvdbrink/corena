package com.danielvandenbrink.corena.server;

import com.badlogic.ashley.core.Entity;
import com.danielvandenbrink.corena.server.components.*;

public final class EntityFactory {
    public Entity createPlayer(long uuid) {
        Entity e = new Entity();
        e.add(new UUIDComponent(uuid));
        e.add(new PositionComponent(0, 0));
        e.add(new VelocityComponent(0, 0));
        e.add(new AccelerationComponent(-9.81f, 0));
        e.add(new TextureComponent("playerShip1_blue"));
        return e;
    }
}
