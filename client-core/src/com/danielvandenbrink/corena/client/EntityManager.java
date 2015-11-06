package com.danielvandenbrink.corena.client;

import com.danielvandenbrink.corena.GameObject;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager implements Iterable<GameObject> {
    private final Map<Long, GameObject> entities = new ConcurrentHashMap<>();

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
