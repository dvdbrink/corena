package com.danielvandenbrink.corena.client;

import com.danielvandenbrink.corena.GameObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityManager implements Iterable<GameObject> {
    private final Map<Long, GameObject> entities = new HashMap<>();

    public EntityManager() {

    }

    public void add(final GameObject entity) {
        entities.put(entity.uuid(), entity);
    }

    public void clear() {
        entities.clear();
    }

    @Override
    public Iterator<GameObject> iterator() {
        return entities.values().iterator();
    }
}
