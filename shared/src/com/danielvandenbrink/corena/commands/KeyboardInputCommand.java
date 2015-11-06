package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;

public class KeyboardInputCommand extends BaseInputCommand {
    private int keycode;

    public KeyboardInputCommand()
    {

    }

    public KeyboardInputCommand(final long uuid, final int keycode) {
        this.uuid = uuid;
        this.keycode = keycode;
    }

    public int keycode() {
        return keycode;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[8 + 4];
        Convert.longToByteArray(uuid, bytes, 0);
        Convert.intToByteArray(keycode, bytes, 8);
        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) throws CommandException {
        uuid = Convert.byteArrayToLong(bytes, 0);
        keycode = Convert.byteArrayToInt(bytes, 8);
    }
}
