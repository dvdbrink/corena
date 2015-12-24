package com.danielvandenbrink.corena.client;

import com.danielvandenbrink.corena.GameObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameObjectManager implements Iterable<GameObject> {
    private final Map<Long, GameObject> entities = new HashMap<>();

    public GameObjectManager() {

    }

    public void addOrUpdate(final GameObject obj) {
        entities.put(obj.uuid(), obj);
    }

    @Override
    public Iterator<GameObject> iterator() {
        return entities.values().iterator();
    }
}
