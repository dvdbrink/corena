package com.danielvandenbrink.corena.client.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.danielvandenbrink.corena.client.Corena;

public class DesktopLauncher {
	public static void main(String[] arg) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 512;
		settings.maxHeight = 512;
		TexturePacker.process(settings, "gfx", "packed", "corena");

        Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = displayMode.width / 2;
		config.height = displayMode.height / 2;
        //config.fullscreen = true;

		config.backgroundFPS = 120;
		config.foregroundFPS = 120;
        config.vSyncEnabled = false;

		new LwjglApplication(new Corena(), config);
	}
}
