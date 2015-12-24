package com.danielvandenbrink.corena.client;

import com.badlogic.gdx.Game;
import com.danielvandenbrink.corena.client.screens.MainMenuScreen;

public class Corena extends Game {
	@Override
	public void create() {
        setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
        super.render();
	}

	@Override
	public void dispose() {
		getScreen().dispose();
	}
}
