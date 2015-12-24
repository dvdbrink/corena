package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.GameObject;
import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;
import com.danielvandenbrink.xudp.Protocol;

import java.util.ArrayList;
import java.util.List;

public class GameStateCommand implements Command {
    private final List<GameObject> objects;

    public GameStateCommand() {
        objects = new ArrayList<>();
    }

    public GameStateCommand(final List<GameObject> objects) {
        this.objects = objects;
    }

    public List<GameObject> objects() {
        return objects;
    }

    @Override
    public Protocol protocol() {
        return Protocol.UnreliableSequenced;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[4 + (objects.size() * (32 + 16))]; // FIXME
        int offset = 0;

        Convert.intToByteArray(objects.size(), bytes, offset);
        offset += 4;

        for (final GameObject obj : objects) {
            Convert.longToByteArray(obj.uuid(), bytes, offset);
            offset += 8;

            Convert.intToByteArray(obj.textureName().length(), bytes, offset);
            offset += 4;

            byte[] spriteBytes = obj.textureName().getBytes();
            System.arraycopy(spriteBytes, 0, bytes, offset, spriteBytes.length);
            offset += 16; // FIXME

            Convert.floatToByteArray(obj.x(), bytes, offset);
            offset += 4;

            Convert.floatToByteArray(obj.y(), bytes, offset);
            offset += 4;

            Convert.floatToByteArray(obj.scaleX(), bytes, offset);
            offset += 4;

            Convert.floatToByteArray(obj.scaleY(), bytes, offset);
            offset += 4;

            Convert.floatToByteArray(obj.rotation(), bytes, offset);
            offset += 4;
        }

        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) throws CommandException {
        int offset = 0;

        final int objCount = Convert.byteArrayToInt(bytes, offset);
        offset += 4;

        for (int i = 0; i < objCount; ++i) {
            final long uuid = Convert.byteArrayToLong(bytes, offset);
            offset += 8;

            final int textureNameLength = Convert.byteArrayToInt(bytes, offset);
            offset += 4;

            final String textureName = new String(bytes, offset, textureNameLength);
            offset += textureNameLength;

            final float x = Convert.byteArrayToFloat(bytes, offset);
            offset += 4;

            final float y = Convert.byteArrayToFloat(bytes, offset);
            offset += 4;

            final float scaleX = Convert.byteArrayToFloat(bytes, offset);
            offset += 4;

            final float scaleY = Convert.byteArrayToFloat(bytes, offset);
            offset += 4;

            final float rotation = Convert.byteArrayToFloat(bytes, offset);
            offset += 4;

            objects.add(new GameObject(uuid, textureName, x, y, scaleX, scaleY, rotation));
        }
    }
}
