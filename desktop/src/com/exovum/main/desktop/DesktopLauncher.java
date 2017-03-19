package com.exovum.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.exovum.gdxsqlitetest.DatabaseTest;
import com.exovum.main.CardGame;
import com.exovum.main.CardGameDBTest;
//import com.exovum.tools.LD37Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // default width and height, but setting with config.width in case I want to change it later
        config.width = 800;
        config.height = 480;
        config.title = "DatabaseTest";
		//new LwjglApplication(new CardGame(), config);
        //new LwjglApplication(new CardGameDBTest(new TestDatabaseDesktop(), new DesktopActionResolver()), config);
        new LwjglApplication(new DatabaseTest(), config);
	}
}
