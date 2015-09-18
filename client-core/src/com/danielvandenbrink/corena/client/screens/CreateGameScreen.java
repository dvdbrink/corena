package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class CreateGameScreen extends BaseScreen {
    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 50;
    public static final int BUTTON_PADDING = 10;

    public CreateGameScreen(final Game game) {
        super(game);

        final TextField nameTextField = new TextField("Daniel", skin(), DEFAULT_STYLE);
        nameTextField.setAlignment(Align.center);

        final TextField portTextField = new TextField("6543", skin(), DEFAULT_STYLE);
        portTextField.setAlignment(Align.center);

        TextButton createButton = new TextButton("Create", skin(), DEFAULT_STYLE);
        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final String name = nameTextField.getText();
                final int port = Integer.parseInt(portTextField.getText());

                game.setScreen(new GameScreen(game, name, port));
                dispose();
            }
        });

        TextButton backButton = new TextButton("Back", skin(), DEFAULT_STYLE);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.columnDefaults(0).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).pad(BUTTON_PADDING);
        table.add(nameTextField);
        table.row();
        table.add(portTextField);
        table.row();
        table.add(createButton);
        table.row();
        table.add(backButton);

        stage().addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game().setScreen(new MainMenuScreen(game()));
            dispose();
        }

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
