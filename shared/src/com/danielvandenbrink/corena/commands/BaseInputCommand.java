package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.xudp.Protocol;

public abstract class BaseInputCommand implements Command {
    protected long uuid;

    public long uuid() {
        return uuid;
    }

    @Override
    public Protocol protocol() {
        return Protocol.ReliableOrdered;
    }
}
