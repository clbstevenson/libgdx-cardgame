package com.exovum.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.exovum.tools.IScreenDispatcher;

/**
 * Created by Caleb Stevenson on 1/19/2017.
 *  com.exovum.main: GameScreen
 */
public class GameScreen implements Screen {

    private SpriteBatch batch;
    private IScreenDispatcher screenDispatcher;
    private Game game;

    private BitmapFont font;

    /*
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private BitmapFont white, black;
    */

    public GameScreen(SpriteBatch batch, IScreenDispatcher screenDispatcher, Game game) {
        this.batch = batch;
        this.screenDispatcher = screenDispatcher;
        this.game = game;

        font = new BitmapFont();
        font.setColor(Color.ORANGE);
    }
    //private

    public void GameScreen() {

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "Switched to GameScreen");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Card Game", 200, 200);
        batch.end();
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

    }
}
