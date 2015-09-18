package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;
import com.danielvandenbrink.xudp.Protocol;

public class InputCommand implements Command {
    private long uuid;
    private int keycode;

    public InputCommand() {

    }

    public InputCommand(final long uuid, final int keycode) {
        this.uuid = uuid;
        this.keycode = keycode;
    }

    public long uuid() {
        return uuid;
    }

    public int keycode() {
        return keycode;
    }

    @Override
    public Protocol protocol() {
        return Protocol.ReliableOrdered;
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
