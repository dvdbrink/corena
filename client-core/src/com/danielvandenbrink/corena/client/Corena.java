package com.danielvandenbrink.corena.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.danielvandenbrink.corena.client.screens.MainMenuScreen;

public class Corena extends Game {
	public static final String BACKGROUND_SFX = "sfx/day 42.mp3";

	private Music backgroundMusic;

	@Override
	public void create() {
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_SFX));
		//backgroundMusic.play();
		//backgroundMusic.setLooping(true);

        setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
        super.render();
	}

	@Override
	public void dispose() {
		getScreen().dispose();
		backgroundMusic.dispose();
	}
}
