package com.danielvandenbrink.corena.server;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerManager implements Iterable<Player> {
    private final Map<Long, Player> players = new HashMap<>();

    private long nextUuid = 0;

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
}
