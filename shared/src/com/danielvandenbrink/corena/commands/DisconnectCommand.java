package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;
import com.danielvandenbrink.xudp.Protocol;

public class DisconnectCommand implements Command {
    private long uuid;

    public DisconnectCommand() {

    }

    public DisconnectCommand(final long uuid) {
        this.uuid = uuid;
    }

    public long uuid() {
        return uuid;
    }

    @Override
    public Protocol protocol() {
        return Protocol.ReliableOrdered;
    }

    @Override
    public byte[] toBytes() {
        return Convert.longToByteArray(uuid);
    }

    @Override
    public void fromBytes(final byte[] bytes) throws CommandException {
        uuid = Convert.byteArrayToLong(bytes);
    }
}
