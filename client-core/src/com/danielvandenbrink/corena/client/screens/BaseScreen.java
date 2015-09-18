package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class BaseScreen implements Screen {
    public static final String DEFAULT_SKIN = "skins/default/uiskin.json";
    public static final String DEFAULT_STYLE = "default";
    public static final String BACKGROUND_IMG = "bg.jpg";

    private final Game game;
    private final Stage stage;
    private final Skin skin;

    public BaseScreen(final Game game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal(DEFAULT_SKIN));

        stage.addActor(new Image(new TextureRegion(new Texture(BACKGROUND_IMG))));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    protected Game game() {
        return game;
    }

    protected Stage stage() {
        return stage;
    }

    protected Skin skin() {
        return skin;
    }
}
