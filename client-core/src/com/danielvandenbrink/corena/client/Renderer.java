package com.danielvandenbrink.corena.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.danielvandenbrink.corena.GameObject;

import java.util.HashMap;
import java.util.Map;

public class Renderer implements Disposable {
    private final Batch batch;
    private final Camera camera;
    private final GameObjectManager gameObjectManager;

    private final TextureAtlas textureAtlas;
    private final Map<String, TextureRegion> textureRegions;

    public Renderer(final Batch batch, final Camera camera, final GameObjectManager gameObjectManager) {
        this.batch = batch;
        this.camera = camera;
        this.gameObjectManager = gameObjectManager;

        // FIXME
        textureAtlas = new TextureAtlas(Gdx.files.internal("packed/corena.atlas"));
        textureRegions = new HashMap<>();
        textureRegions.put("playerShip1_blue", textureAtlas.findRegion("playerShip1_blue"));
    }

    public void render() {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (final GameObject obj : gameObjectManager) {
            final TextureRegion textureRegion = textureRegions.get(obj.textureName());
            batch.draw(textureRegion, // Region
                    obj.x(), obj.y(), // Position
                    textureRegion.getRegionWidth() / 2.0f, textureRegion.getRegionHeight() / 2.0f, // Origin
                    textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), // Size
                    1.0f, 1.0f, // Scale
                    obj.rotation() + 90); // Rotation
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
