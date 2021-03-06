package com.danielvandenbrink.corena.client.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.danielvandenbrink.corena.Action;
import com.danielvandenbrink.corena.client.GameObjectManager;
import com.danielvandenbrink.corena.client.Renderer;
import com.danielvandenbrink.corena.client.handlers.GameStateCommandHandler;
import com.danielvandenbrink.corena.commands.*;
import com.danielvandenbrink.corena.communication.Command;
import com.danielvandenbrink.corena.communication.CommandHandlerRegistry;
import com.danielvandenbrink.corena.communication.CommandParser;
import com.danielvandenbrink.corena.server.GameServer;
import com.danielvandenbrink.xudp.Client;
import com.danielvandenbrink.xudp.impl.*;

public class GameScreen implements Screen, InputProcessor {
    public static final String LOOPBACK_ADDRESS = "127.0.0.1";

    private final Game game;

    private CommandParser commandParser;
    private CommandHandlerRegistry commandHandlerRegistry;
    private Client client;
    private GameObjectManager gameObjectManager;
    private Renderer renderer;

    private long uuid = -1;

    public GameScreen(final Game game, final String name, final int port) {
        this.game = game;

        new Thread(new GameServer(port), "Corena Server").start();

        init(name, LOOPBACK_ADDRESS, port);
    }

    public GameScreen(final Game game, final String name, final String ip, final int port) {
        this.game = game;

        init(name, ip, port);
    }

    private void init(final String name, final String ip, final int port) {
        gameObjectManager = new GameObjectManager();

        final Batch batch = new SpriteBatch();
        final Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new Renderer(batch, camera, gameObjectManager);

        commandParser = new CommandParser();
        commandHandlerRegistry = new CommandHandlerRegistry();
        commandHandlerRegistry.register(GameStateCommand.class, new GameStateCommandHandler(gameObjectManager));
        commandHandlerRegistry.register(AuthorizedCommand.class, (command, address) -> uuid = command.uuid());

        client = new Client<>(new UdpSocket(new SelectorFactory(), new DatagramChannelFactory()),
                new UdpPacketHandler(),
                new UdpPacketEncoder(),
                new UdpPacketDecoder(),
                new UdpPacketFactory());
        client.connect(ip, port);
        send(new ConnectCommand(name));

        Gdx.input.setInputProcessor(this);
    }

    private void send(Command command) {
        client.send(command.protocol(), commandParser.encode(command));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput();

        client.read(e -> {
            Command cmd = commandParser.decode(e.packet().data());
            commandHandlerRegistry.dispatch(cmd, e.from());
        });

        renderer.render();

        client.write();
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
        send(new DisconnectCommand(uuid));

        client.write();
        client.close();

        renderer.dispose();
    }

    private void handleInput() {
        // Touch
        boolean firstFingerTouching = Gdx.input.isTouched(0);
        if (firstFingerTouching) {
            int firstFingerX = Gdx.input.getX();
            int firstFingerY = Gdx.input.getY();
            send(new MouseInputCommand(uuid, firstFingerX, firstFingerY));
        }

        // Mouse
        boolean leftPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        if (leftPressed) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            send(new MouseInputCommand(uuid, mouseX, mouseY));
        }

        // Keyboard
        KeyboardInputCommand kic = new KeyboardInputCommand(uuid);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            kic.add(Action.FORWARD);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            kic.add(Action.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            kic.add(Action.BACKWARD);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            kic.add(Action.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            kic.add(Action.SHOOT);
        }
        if (kic.keys().size() > 0) {
            send(kic);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
//        switch (keycode) {
//            case Input.Keys.W:
//                send(new KeyboardInputCommand(uuid, Input.Keys.W));
//                break;
//            case Input.Keys.A:
//                send(new KeyboardInputCommand(uuid, Input.Keys.A));
//                break;
//            case Input.Keys.S:
//                send(new KeyboardInputCommand(uuid, Input.Keys.S));
//                break;
//            case Input.Keys.D:
//                send(new KeyboardInputCommand(uuid, Input.Keys.D));
//                break;
//            case Input.Keys.ESCAPE:
//                game.setScreen(new MainMenuScreen(game));
//                dispose();
//                break;
//        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
