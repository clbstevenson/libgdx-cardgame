package com.exovum.ld37warmup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by exovu_000 on 12/3/2016.
 *
 * Ludum Dare 37 - Jam
 * Theme: One Room
 */

public class LD37Game extends Game {

    SpriteBatch batch;
    AssetManager am;

    public static ScreenDispatcherWarmup screenDispatcher;

    @Override
    public void create() {
        // Load the assets before hand, and setup AssetManager to access later
        am = Assets.load();
        batch = new SpriteBatch();
        screenDispatcher = new ScreenDispatcherWarmup();
        // loading screen to make sure assets are loaded
        Screen splashScreen = new SplashScreenWarmup(batch, screenDispatcher);
        Screen gameScreen = new GameScreenWarmup(this, batch, screenDispatcher);
        Screen schoolScreen = new SchoolGameScreen(this, batch, screenDispatcher);
        Screen gameoverScreen = new GameOverScreen(this, batch, screenDispatcher);
        screenDispatcher.AddScreen(splashScreen);
        // Old GameScreen
        // screenDispatcher.AddScreen(gameScreen);
        screenDispatcher.AddScreen(schoolScreen);
        screenDispatcher.AddScreen(gameoverScreen);
        setScreen(splashScreen);
    }

    @Override
    public void render () {
        float r = 0/255f;
        float g = 24f/255f;
        float b = 72f/255f;
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Screen nextScreen = screenDispatcher.getNextScreen();
        if(nextScreen != getScreen()){
            setScreen(nextScreen);
            Gdx.app.log("LD37 Warmup", "Switching screens");
        }

        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.log("LD37 Warmup", "Disposing of assets");
        am.dispose(); // am.clear();
    }

}
