package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;
import com.danielvandenbrink.xudp.Protocol;

public class AuthorizedCommand implements Command {
    private long uuid;

    public AuthorizedCommand() {

    }

    public AuthorizedCommand(final long uuid) {
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
    public void fromBytes(byte[] bytes) throws CommandException {
        uuid = Convert.byteArrayToLong(bytes);
    }
}
