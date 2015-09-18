package com.danielvandenbrink.corena.communication;

import com.danielvandenbrink.corena.commands.*;
import com.danielvandenbrink.corena.util.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    public static final int COMMAND_ID_SIZE = 4;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public byte[] encode(final Command command) {
        final int commandId = COMMAND_IDS.get(command.getClass());
        final byte[] commandBytes = command.toBytes();

        final byte[] bytes = new byte[commandBytes.length + COMMAND_ID_SIZE];
        Convert.intToByteArray(commandId, bytes, 0);
        System.arraycopy(commandBytes, 0, bytes, COMMAND_ID_SIZE, commandBytes.length);

        return bytes;
    }

    public Command decode(final byte[] bytes) {
        int commandId = Convert.byteArrayToInt(bytes);
        Command command = null;
        try {
            command = COMMAND_TYPES.get(commandId).newInstance();
            final byte[] commandBytes = new byte[bytes.length - COMMAND_ID_SIZE];
            System.arraycopy(bytes, COMMAND_ID_SIZE, commandBytes, 0, commandBytes.length);
            command.fromBytes(commandBytes);
        } catch (InstantiationException | IllegalAccessException | CommandException e) {
            log.error("Failed to decode command", e);
        }
        return command;
    }

    private static Map<Class<? extends Command>, Integer> COMMAND_IDS;
    private static Map<Integer, Class<? extends Command>> COMMAND_TYPES;

    static {
        Map<Class<? extends Command>, Integer> map = new HashMap<>();
        map.put(ConnectCommand.class, 0);
        map.put(DisconnectCommand.class, 1);
        map.put(InputCommand.class, 2);
        map.put(GameStateCommand.class, 3);
        map.put(AuthorizedCommand.class, 4);

        COMMAND_IDS = Collections.unmodifiableMap(map);
        COMMAND_TYPES = Collections.unmodifiableMap(invert(map));
    }

    private static <V, K> Map<V, K> invert(Map<K, V> map) {
        Map<V, K> inv = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            inv.put(entry.getValue(), entry.getKey());
        }
        return inv;
    }
}
