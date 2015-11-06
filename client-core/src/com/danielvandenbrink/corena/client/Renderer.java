package com.danielvandenbrink.corena.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.danielvandenbrink.corena.GameObject;

public class Renderer implements Disposable {
    private final Batch batch;
    private final Camera camera;
    private final EntityManager entityManager;

    private final TextureRegion playerTexture;

    public Renderer(final Batch batch, final Camera camera, final EntityManager entityManager) {
        this.batch = batch;
        this.camera = camera;
        this.entityManager = entityManager;

        playerTexture = new TextureRegion(new Texture("badlogic.jpg"));
    }

    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (final GameObject entity : entityManager) {
            batch.draw(playerTexture, entity.x(), entity.y());
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
