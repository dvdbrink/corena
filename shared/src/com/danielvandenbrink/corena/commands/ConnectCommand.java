package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;
import com.danielvandenbrink.xudp.Protocol;

public class ConnectCommand implements Command {
    private String name;

    public ConnectCommand() {

    }

    public ConnectCommand(final String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public Protocol protocol() {
        return Protocol.ReliableOrdered;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[4 + name.length()];
        Convert.intToByteArray(name.length(), bytes, 0);
        final byte[] nameBytes = name.getBytes();
        System.arraycopy(nameBytes, 0, bytes, 4, name.length());
        return bytes;
    }

    @Override
    public void fromBytes(final byte[] bytes) throws CommandException {
        final int nameLength = Convert.byteArrayToInt(bytes, 0);
        final byte[] nameBytes = new byte[nameLength];
        System.arraycopy(bytes, 4, nameBytes, 0, nameLength);
        name = new String(nameBytes);
    }
}
