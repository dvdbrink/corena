package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends BaseScreen {
    public static final String BUTTON_ENTER_SFX = "sfx/test.wav";
    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 50;
    public static final int BUTTON_PADDING = 10;

    private Sound buttonEnterSound;

    public MainMenuScreen(final Game game) {
        super(game);

        stage().addActor(createTable(skin(), DEFAULT_STYLE));

        buttonEnterSound = Gdx.audio.newSound(Gdx.files.internal(BUTTON_ENTER_SFX));
    }

    private Table createTable(final Skin skin, final String styleName) {
        Table table = new Table();
        table.setFillParent(true);

        table.add(createCreateGameButton(skin, styleName))
                .width(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT)
                .pad(BUTTON_PADDING);

        table.row();
        table.add(createJoinGameButton(skin, styleName))
                .width(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT)
                .pad(BUTTON_PADDING);

        table.row();
        table.add(createSettingsButton(skin, styleName))
                .width(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT)
                .pad(BUTTON_PADDING);

        table.row();
        table.add(createExitButton(skin, styleName))
                .width(BUTTON_WIDTH)
                .height(BUTTON_HEIGHT)
                .pad(BUTTON_PADDING);

        return table;
    }

    private TextButton createCreateGameButton(final Skin skin, final String styleName) {
        TextButton button = new TextButton("Create Game", skin, styleName);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game().setScreen(new CreateGameScreen(game()));
                dispose();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                onButtonEnter();
            }
        });
        return button;
    }

    private TextButton createJoinGameButton(final Skin skin, final String styleName) {
        TextButton button = new TextButton("Join Game", skin, styleName);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game().setScreen(new JoinGameScreen(game()));
                dispose();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                onButtonEnter();
            }
        });
        return button;
    }

    private TextButton createSettingsButton(final Skin skin, final String styleName) {
        TextButton button = new TextButton("Settings", skin, styleName);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                onButtonEnter();
            }
        });
        return button;
    }

    private TextButton createExitButton(final Skin skin, final String styleName) {
        TextButton button = new TextButton("Exit", skin, styleName);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                onButtonEnter();
            }
        });
        return button;
    }

    private void onButtonEnter() {
        buttonEnterSound.play(1.0f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
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

    @Override
    public void dispose() {
        super.dispose();
    }
}
