package com.exovum.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextureAtlas atlas;
    protected Skin skin;

    private Stage stage;


    /*
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

        camera = new OrthographicCamera();
        viewport = new FitViewport(200, 200, camera);
        viewport.apply(false);

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        atlas = new TextureAtlas("ui/ui.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);

        stage = new Stage(viewport, batch);
        // Add the stage as an InputProcessor
        Gdx.input.setInputProcessor(stage);
    }
    //private

    public void GameScreen() {

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "Switched to GameScreen");

        Table mainTable = new Table();
        // Set table to fill the stage
        mainTable.setFillParent(true);
        // Set alignment of contents in the table
        mainTable.top();

        // Create the buttons
        TextButton playButton = new TextButton("Play", skin, "container_gold");
        TextButton optionsButton = new TextButton("Options", skin, "container_gold_small");
        TextButton exitButton= new TextButton("Exit", skin, "container_gold");

        // Add Input Listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("GameScreen", "Pressed PLAY");
                //((CardGame)Gdx.app.getApplicationListener()).setScreen(new  NextScreen());
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("GameScren", "Pressed EXIT");
                Gdx.app.exit();
            }
        });

        // Add buttons to table
        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.row();
        mainTable.add(exitButton);

        // Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {

        camera.update();
        //Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        /*
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Card Game", 0, 0);
        batch.end();
        */
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();
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
        font.dispose();
    }
}
