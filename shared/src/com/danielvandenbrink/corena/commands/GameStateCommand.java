package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.GameObject;
import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;
import com.danielvandenbrink.xudp.Protocol;

import java.util.ArrayList;
import java.util.List;

public class GameStateCommand implements Command {
    private List<GameObject> entities;

    public GameStateCommand() {

    }

    public GameStateCommand(final List<GameObject> entities) {
        this.entities = entities;
    }

    public List<GameObject> entities() {
        return entities;
    }

    @Override
    public Protocol protocol() {
        return Protocol.UnreliableSequenced;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[4 + (entities.size() * 16)];
        int offset = 0;

        Convert.intToByteArray(entities.size(), bytes, offset);
        offset += 4;

        for (final GameObject entity : entities) {
            Convert.longToByteArray(entity.uuid(), bytes, offset);
            offset += 8;

            Convert.intToByteArray(entity.x(), bytes, offset);
            offset += 4;

            Convert.intToByteArray(entity.y(), bytes, offset);
            offset += 4;
        }
        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) throws CommandException {
        entities = new ArrayList<>();

        int offset = 0;

        final int entityCount = Convert.byteArrayToInt(bytes, offset);
        offset += 4;

        for (int i = 0; i < entityCount; ++i) {
            final long uuid = Convert.byteArrayToLong(bytes, offset);
            offset += 8;

            final int x = Convert.byteArrayToInt(bytes, offset);
            offset += 4;

            final int y = Convert.byteArrayToInt(bytes, offset);
            offset += 4;

            entities.add(new GameObject(uuid, x, y));
        }
    }
}
