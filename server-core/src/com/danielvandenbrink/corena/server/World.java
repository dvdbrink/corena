package com.danielvandenbrink.corena.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.danielvandenbrink.corena.GameObject;
import com.danielvandenbrink.corena.server.components.PositionComponent;
import com.danielvandenbrink.corena.server.components.TextureComponent;
import com.danielvandenbrink.corena.server.components.UUIDComponent;

import java.util.ArrayList;
import java.util.List;

public class World extends Engine {
    private boolean dirty = false;

    public List<GameObject> getObjects() {
        List<GameObject> objects = new ArrayList<>();

        Family family = Family.all(
                UUIDComponent.class,
                PositionComponent.class,
                TextureComponent.class).get();
        ImmutableArray<Entity> entities = getEntitiesFor(family);

        for (Entity e : entities) {
            UUIDComponent uuid = e.getComponent(UUIDComponent.class);
            PositionComponent position = e.getComponent(PositionComponent.class);
            TextureComponent texture = e.getComponent(TextureComponent.class);

            GameObject obj = new GameObject(
                    uuid.uuid(),
                    texture.name,
                    position.x, position.y);

            objects.add(obj);
        }

        return objects;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
