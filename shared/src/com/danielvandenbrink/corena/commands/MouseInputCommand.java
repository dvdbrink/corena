package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;

public class MouseInputCommand extends BaseInputCommand {
    private int x, y;

    public MouseInputCommand()
    {

    }

    public MouseInputCommand(final long uuid, final int x, final int y) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[8 + 4 + 4];
        Convert.longToByteArray(uuid, bytes, 0);
        Convert.intToByteArray(x, bytes, 8);
        Convert.intToByteArray(y, bytes, 12);
        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) throws CommandException {
        uuid = Convert.byteArrayToLong(bytes, 0);
        x = Convert.byteArrayToInt(bytes, 8);
        y = Convert.byteArrayToInt(bytes, 12);
    }
}
