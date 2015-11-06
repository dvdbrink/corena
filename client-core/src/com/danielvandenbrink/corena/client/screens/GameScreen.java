package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.danielvandenbrink.corena.client.EntityManager;
import com.danielvandenbrink.corena.client.GameClient;
import com.danielvandenbrink.corena.client.Renderer;
import com.danielvandenbrink.corena.commands.*;
import com.danielvandenbrink.corena.server.GameServer;

public class GameScreen implements Screen {
    public static final String LOOPBACK_ADDRESS = "127.0.0.1";

    private final Game game;

    private EntityManager entityManager;
    private Renderer renderer;
    private GameClient client;
    private GameServer server;

    public GameScreen(final Game game, final String name, final int port) {
        this.game = game;

        server = new GameServer(port);

        init(name, LOOPBACK_ADDRESS, port);
    }

    public GameScreen(final Game game, final String name, final String ip, final int port) {
        this.game = game;

        init(name, ip, port);
    }

    private void init(final String name, final String ip, final int port) {
        entityManager = new EntityManager();

        final Batch batch = new SpriteBatch();
        final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new Renderer(batch, camera, entityManager);

        client = new GameClient(ip, port, entityManager);
        client.send(new ConnectCommand(name));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput();

        renderer.render();
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
        client.send(new DisconnectCommand(client.uuid()));
        client.stop();

        renderer.dispose();
    }

    private void handleInput() {
        // Touch
        boolean firstFingerTouching = Gdx.input.isTouched(0);
        if (firstFingerTouching) {
            int firstFingerX = Gdx.input.getX();
            int firstFingerY = Gdx.input.getY();
            client.send(new MouseInputCommand(client.uuid(), firstFingerX, firstFingerY));
        }

        // Mouse
        boolean leftPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if (leftPressed) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            client.send(new MouseInputCommand(client.uuid(), mouseX, mouseY));
        }

        // Keyboard
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            client.send(new KeyboardInputCommand(client.uuid(), Input.Keys.W));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            client.send(new KeyboardInputCommand(client.uuid(), Input.Keys.A));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            client.send(new KeyboardInputCommand(client.uuid(), Input.Keys.S));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            client.send(new KeyboardInputCommand(client.uuid(), Input.Keys.D));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }
}
