package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class JoinGameScreen extends BaseScreen {
    private static final String DEFAULT_IP_TEXT = "94.214.197.13";
    private static final String DEFAULT_PORT_TEXT = "6543";
    private static final String JOIN_BUTTON_TEXT = "Join";

    public JoinGameScreen(Game game) {
        super(game);

        final TextField nameTextField = new TextField("Daniel", skin(), DEFAULT_STYLE);
        nameTextField.setAlignment(Align.center);

        final TextField ipTextField = new TextField(DEFAULT_IP_TEXT, skin(), DEFAULT_STYLE);
        ipTextField.setAlignment(Align.center);

        final TextField portTextField = new TextField(DEFAULT_PORT_TEXT, skin(), DEFAULT_STYLE);
        portTextField.setAlignment(Align.center);

        final TextButton joinButton = new TextButton(JOIN_BUTTON_TEXT, skin(), DEFAULT_STYLE);
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final String name = nameTextField.getText();
                final String ip = ipTextField.getText();
                final int port = Integer.parseInt(portTextField.getText());

                game().setScreen(new GameScreen(game(), name, ip, port));
                dispose();
            }
        });

        TextButton backButton = new TextButton("Back", skin(), DEFAULT_STYLE);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game().setScreen(new MainMenuScreen(game()));
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.columnDefaults(0).width(300).height(50).pad(5);
        table.add(nameTextField);
        table.row();
        table.add(ipTextField);
        table.row();
        table.add(portTextField);
        table.row();
        table.add(joinButton);
        table.row();
        table.add(backButton);

        stage().addActor(table);
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
