package com.danielvandenbrink.corena.server;

import com.danielvandenbrink.corena.GameObject;
import com.danielvandenbrink.corena.server.Player;

import java.net.SocketAddress;
import java.util.*;

public class PlayerManager implements Iterable<Player> {
    private final Map<Long, Player> players = new HashMap<>();

    private long nextUuid = 0;
    private boolean dirty = false;

    public Player add(final SocketAddress address, final String name) {
        final Long uuid = nextUuid++;
        final Player player = new Player(uuid, address, name);
        players.put(uuid, player);
        return player;
    }

    public Player get(final long uuid) {
        return players.get(uuid);
    }

    public void remove(final long uuid) {
        players.remove(uuid);
    }

    @Override
    public Iterator<Player> iterator() {
        return players.values().iterator();
    }

    public List<GameObject> entities() {
        final List<GameObject> entities = new ArrayList<>();
        for (final Player p : players.values()) {
            entities.add(new GameObject(p.uuid(), "playerShip1_blue", p.x(), p.y(), p.rotation()));
        }
        return entities;
    }

    public boolean dirty() {
        return dirty;
    }

    public void dirty(boolean dirty) {
        this.dirty = dirty;
    }
}