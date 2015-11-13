package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;

import java.util.ArrayList;
import java.util.List;

public class KeyboardInputCommand extends BaseInputCommand {
    private List<Integer> keys;

    public KeyboardInputCommand()
    {

    }

    public KeyboardInputCommand(final long uuid) {
        this.uuid = uuid;
        this.keys = new ArrayList<>();
    }

    public List<Integer> keys() {
        return keys;
    }

    public void add(int key) {
        keys.add(key);
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[12 + (keys.size() * 4)];
        Convert.longToByteArray(uuid, bytes, 0);
        Convert.intToByteArray(keys.size(), bytes, 8);
        for (int i = 0; i < keys.size(); ++i) {
            Convert.intToByteArray(keys.get(i), bytes, 12 + (i * 4));
        }
        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) throws CommandException {
        uuid = Convert.byteArrayToLong(bytes, 0);
        keys = new ArrayList<>();

        final int numberOfKeys = Convert.byteArrayToInt(bytes, 8);
        for (int i = 0; i < numberOfKeys; ++i) {
            keys.add(Convert.byteArrayToInt(bytes, 12 + (i * 4)));
        }
    }
}
