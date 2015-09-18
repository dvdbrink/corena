package com.danielvandenbrink.corena.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpatialComponent implements Component {
    public TextureRegion region;

    public SpatialComponent(TextureRegion region) {
        this.region = region;
    }
}
