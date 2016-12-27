package com.exovum.ld37warmup.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.exovum.ld37warmup.LD37Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // default width and height, but setting with config.width in case I want to change it later
        config.width = 640;
        config.height = 480;
		new LwjglApplication(new LD37Game(), config);
	}
}
