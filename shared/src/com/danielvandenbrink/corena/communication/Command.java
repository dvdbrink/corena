package com.danielvandenbrink.corena.communication;

import com.danielvandenbrink.xudp.Protocol;

public interface Command {
    Protocol protocol();
    byte[] toBytes();
    void fromBytes(byte[] bytes) throws CommandException;
}
