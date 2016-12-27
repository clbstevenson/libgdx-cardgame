package com.exovum.ld37warmup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by exovu_000 on 12/12/2016.
 */
public class GameOverScreen implements Screen {
    SpriteBatch batch;
    Game game;
    IScreenDispatcher dispatcher;

    public GameOverScreen(Game game, SpriteBatch batch, IScreenDispatcher screenDispatcher) {
        this.batch = batch;
        this.game = game;
        this.dispatcher = screenDispatcher;
    }

    @Override
    public void show() {
        Gdx.app.log("Game Over Screen", "Switched to GameOverScreen");

        Assets.getSoundByName("gameover.wav").play(0.5f);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.SPACE) {
                    Gdx.app.log("Game Over Screen", "Ending game over screen");
                    // reset
                    //game.setScreen(new SchoolGameScreen(game, batch, LD37Game.screenDispatcher));
                    dispatcher.endCurrentScreen();
                    return true;
                }
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
                // reset
                //game.setScreen(new SchoolGameScreen(game, batch, LD37Game.screenDispatcher));
                //dispatcher.endCurrentScreen();
                return true;
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
        });
    }


    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(Assets.gameOver, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
