package com.danielvandenbrink.corena.client.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.danielvandenbrink.corena.client.Corena;

public class DesktopLauncher {
	public static void main(String[] arg) {
        Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = displayMode.width;
		//config.height = displayMode.height;
        //config.fullscreen = true;
		config.width = 960;
		config.height = 600;

		new LwjglApplication(new Corena(), config);
	}
}
