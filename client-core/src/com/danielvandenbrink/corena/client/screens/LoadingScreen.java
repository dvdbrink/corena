package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class LoadingScreen extends BaseScreen {
    private ProgressBar progressBar;

    private float p = 0;

    public LoadingScreen(Game game) {
        super(game);

        progressBar = new ProgressBar(0f, 100f, 1f, false, skin(), DEFAULT_STYLE);
        progressBar.setWidth(Gdx.graphics.getWidth());
        progressBar.setHeight(4);

        stage().addActor(progressBar);
    }

    @Override
    public void render(float delta) {
        progressBar.setValue(p += .2f);
        super.render(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
