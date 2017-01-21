package com.exovum.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.exovum.tools.Assets;
import com.exovum.tools.GameOverScreen;
import com.exovum.tools.IScreenDispatcher;
import com.exovum.tools.SchoolGameScreen;
import com.exovum.tools.ScreenDispatcherWarmup;
import com.exovum.tools.SplashScreenWarmup;

/**
 * Created by Caleb Stevenson on 01/19/2017
 */

public class CardGame extends Game {

    SpriteBatch batch;
    AssetManager am;

   // public static ScreenDispatcherWarmup screenDispatcher;
    private IScreenDispatcher screenDispatcher;

    @Override
    public void create() {
        // Load the assets before hand, and setup AssetManager to access later
        am = Assets.load();
        batch = new SpriteBatch();

        // Setup the screen dispatcher to switch between screens
        // TODO: Create custom ScreenDispatcher for CardGame
        screenDispatcher = new ScreenDispatcherWarmup();

        // Create the screens
        Screen splashScreen = new SplashScreen(batch, screenDispatcher);
        Screen gameScreen = new GameScreen(batch, screenDispatcher, this);

        // Add the screens to the dispatcher

        screenDispatcher.addScreen(splashScreen);
        screenDispatcher.addScreen(gameScreen);

        /*
        screenDispatcher = new ScreenDispatcherWarmup();
        // loading screen to make sure assets are loaded
        Screen splashScreen = new SplashScreenWarmup(batch, screenDispatcher);
        Screen schoolScreen = new SchoolGameScreen(this, batch, screenDispatcher);
        Screen gameoverScreen = new GameOverScreen(this, batch, screenDispatcher);
        screenDispatcher.AddScreen(splashScreen);
        // Old GameScreen
        // screenDispatcher.AddScreen(gameScreen);
        screenDispatcher.AddScreen(schoolScreen);
        screenDispatcher.AddScreen(gameoverScreen);
        setScreen(splashScreen);
        */
    }

    @Override
    public void render () {
        float r = 0/255f;
        float g = 24f/255f;
        float b = 72f/255f;
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(screenDispatcher.getCurrentScreen() == null) {
            Gdx.app.log("CardGame", "No screens in screenDispatcher to switch to. Exiting.");
            Gdx.app.exit();
            //System.exit(1);
        }
        Screen nextScreen = screenDispatcher.getNextScreen();
        if(nextScreen != getScreen()){
            setScreen(nextScreen);
            Gdx.app.log("CardGame", "Switching screens");
        }


        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.log("CardGame", "Disposing of assets");
        am.dispose(); // am.clear();
    }

}
