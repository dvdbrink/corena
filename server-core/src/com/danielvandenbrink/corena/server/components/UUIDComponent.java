package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;

public class UUIDComponent implements Component {
    public final long uuid;

    public UUIDComponent(final long uuid) {
        this.uuid = uuid;
    }

    public long uuid() {
        return uuid;
    }
}
