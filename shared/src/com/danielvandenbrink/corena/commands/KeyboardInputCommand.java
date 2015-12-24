package com.danielvandenbrink.corena.commands;

import com.danielvandenbrink.corena.Action;
import com.danielvandenbrink.corena.communication.CommandException;
import com.danielvandenbrink.corena.util.Convert;

import java.util.ArrayList;
import java.util.List;

public class KeyboardInputCommand extends BaseInputCommand {
    private List<Action> actions;

    public KeyboardInputCommand()
    {

    }

    public KeyboardInputCommand(final long uuid) {
        this.uuid = uuid;
        this.actions = new ArrayList<>();
    }

    public List<Action> keys() {
        return actions;
    }

    public void add(Action action) {
        actions.add(action);
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = new byte[12 + (actions.size() * 4)];
        Convert.longToByteArray(uuid, bytes, 0);
        Convert.intToByteArray(actions.size(), bytes, 8);
        for (int i = 0; i < actions.size(); ++i) {
            Convert.intToByteArray(actions.get(i).id(), bytes, 12 + (i * 4));
        }
        return bytes;
    }

    @Override
    public void fromBytes(byte[] bytes) throws CommandException {
        uuid = Convert.byteArrayToLong(bytes, 0);
        actions = new ArrayList<>();

        final int numberOfKeys = Convert.byteArrayToInt(bytes, 8);
        for (int i = 0; i < numberOfKeys; ++i) {
            final int id = Convert.byteArrayToInt(bytes, 12 + (i * 4));
            final Action action = Action.fromId(id);
            actions.add(action);
        }
    }
}
