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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exovum.tools.IScreenDispatcher;

/**
 * Created by Caleb Stevenson on 1/19/2017.
 *  com.exovum.main: GameScreen
 */
class GameScreen implements Screen {

    private SpriteBatch batch;
    private IScreenDispatcher screenDispatcher;
    private Game game;

    private BitmapFont font;

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextureAtlas atlas;
    private Skin skin;
    // Note: When adding skin to Actors, rememeber to specify which skin font to use. For example:
    //      TextButton specialFontButton = new TextButton("I'm Special!", skin, "special-font");
    // Otherwise, the font for the button is garbledygook hieroglyphics.

    private Stage stage;

    private TestDatabase db;
    private ActionResolver resolver;

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
        viewport = new FitViewport(800, 480, camera);
        viewport.apply(true);

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        /*
        atlas = new TextureAtlas("ui/uiskin-1.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin-1.json"), atlas);
        */
        atlas = new TextureAtlas("ui/uiskin-2.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin-2.json"), atlas);

        stage = new Stage(viewport, batch);
        // Add the stage as an InputProcessor
        Gdx.input.setInputProcessor(stage);
        // TODO: Create a InputMultiplexer to process multiple input sources (stage, keyboard, etc)
    }

    public GameScreen(SpriteBatch batch, IScreenDispatcher screenDispatcher, Game game,
                      TestDatabase db, ActionResolver resolver) {
        this.batch = batch;
        this.screenDispatcher = screenDispatcher;
        this.game = game;
        this.db = db;
        this.resolver = resolver;

        font = new BitmapFont();
        font.setColor(Color.ORANGE);

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        viewport.apply(true);

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        /*
        atlas = new TextureAtlas("ui/uiskin-1.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin-1.json"), atlas);
        */
        atlas = new TextureAtlas("ui/uiskin-2.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin-2.json"), atlas);

        stage = new Stage(viewport, batch);
        // Add the stage as an InputProcessor
        Gdx.input.setInputProcessor(stage);
        // TODO: Create a InputMultiplexer to process multiple input sources (stage, keyboard, etc)
    }

    //private

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "Switched to GameScreen");

        //generateMenuScreen(stage);
       /* generateFrame1(stage);
        generateFrame1(stage);
        generateFrame1(stage);
        */

        generateTestButtons(stage);

    }

    private void generateFrame1(Stage stage) {

        boolean debugTables = true;

        Table mainTable = new Table();
        //mainTable.setFillParent(true);
        // Set alignment of contents
        //mainTable.align(Align.right);
        mainTable.left();
        mainTable.debugAll();
//        if (debugTables)
//            mainTable.debug();

        // Opponent Section
        // Create top section of Frame 1: Opponent's Deck and Grave
        Table oppTable = new Table();
        oppTable.setFillParent(false);
        oppTable.center();
//        if (debugTables)
//            oppTable.debug();


        //Create the Deck piles
        Table oppDeckGrave = new Table();
        //oppDeckGrave.setFillParent(false);
//        if (debugTables)
//            oppDeckGrave.debug();

        // Deck and Grave testing buttons for opponent
        final TextButton oppDeck = new TextButton("Opponent's\nDeck", skin, "small-font");
        final TextButton oppGrave = new TextButton("Opponent's\nGrave", skin, "small-font");
        oppDeck.setHeight(10);
        oppGrave.setHeight(10);

        // Add opponent's buttons to the oppDeckGrave table
        oppDeckGrave.add(oppDeck);
        oppDeckGrave.add(oppGrave);
        addButtonListenerTo(oppDeck);
        addButtonListenerTo(oppGrave);

        // Add oppDeckGrave to the opponent's main table
        oppTable.add(oppDeckGrave);


        // Player Section
        // Create bottom section of Frame 1: Player's Deck and Grave
        Table playerTable = new Table();
        playerTable.setFillParent(false);
        playerTable.center();
//        if (debugTables)
//            playerTable.debug();

        // Create the Deck pile
        // TEMPORARY: use a button as a test
        Table playerDeckGrave = new Table();
        //playerDeckGrave.setFillParent(false);
        //playerDeckGrave.center();
//        if (debugTables)
//            playerDeckGrave.debug();

        final TextButton playerDeck = new TextButton("Player's\nDeck", skin, "small-font");
        final TextButton playerGrave = new TextButton("Player's\nGrave", skin, "small-font");
        //playerDeck.setFillParent(false);

        addButtonListenerTo(playerDeck);
        addButtonListenerTo(playerGrave);

        /*
        // Input Listener for testing the UI buttons
        ClickListener buttonListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getRelatedActor() == playerDeck) {
                    Gdx.app.log("GameScreen UI", "Pressed Player's Deck");
                } else if(event.getRelatedActor() == playerGrave) {
                    Gdx.app.log("GameScreen UI", "Pressed Player's Grave");
                } else {
                    Gdx.app.log("GameScreen UI", "Nothing was pressed?");
                }

                //((CardGame)Gdx.app.getApplicationListener()).setScreen(new  NextScreen());
            }
        };

        playerDeck.addListener(buttonListener);
        playerGrave.addListener(buttonListener);
        */

        // Add the buttons to the playerDeckGrave table
        playerDeckGrave.add(playerDeck);
        playerDeckGrave.add(playerGrave);
        //playerDeckGrave.row();

        // Add the playerDeckGrave table to be the top of the playerTable
        playerTable.add(playerDeckGrave);

        // Add the oppTable first, so it should be above the playerTable
        mainTable.add(oppTable);
        mainTable.row();
        // Add the playerTable to be at the bottom of the Frame 1 table
        mainTable.add(playerTable);

        // Add the Frame 1 table to the stage
        stage.addActor(mainTable);
    }

    private void addButtonListenerTo(final TextButton button) {
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("GameScreen UI", "Pressed Button:" + button.getLabel().getText());
            }
        });
    }

    private void generateMenuScreen(Stage stage) {
        Table mainTable = new Table();
        // Set table to fill the stage
        mainTable.setFillParent(true);
        // Set alignment of contents in the table
        mainTable.top();

        // Create the buttons
        TextButton playButton = new TextButton("Play the long-name game", skin, "small-font");
        TextButton optionsButton = new TextButton("Options", skin, "small-font");
        TextButton exitButton= new TextButton("Exit", skin, "small-font");

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

    private void generateTestButtons(Stage stage) {


        String entryLabel = "main table. debugging {main, input, entries}";



        Table mainTable = new Table();
        mainTable.debug();
        mainTable.setFillParent(true);
        //mainTable.center();

        //mainTable.set

        /*
            Table for containing input: items to add/delete and buttons
        */
        Table inputTable = new Table();
        inputTable.center();
        inputTable.debug();

        TextField inputDBEntry =  new TextField(entryLabel, skin, "small-font");
        inputDBEntry.setAlignment(Align.center);
        //inputDBEntry.set
        inputTable.add(inputDBEntry).pad(10);//.fillY().align(Align.top);

        inputTable.row();

        TextButton addDBEntry = new TextButton("Add Item", skin, "small-font");
        TextButton deleteDBEntry = new TextButton("Delete Item", skin, "small-font");

        inputTable.add(addDBEntry);
        inputTable.row();
        inputTable.add(deleteDBEntry);


        /*
            Tables for list entries added to the DB
        */
        Table scrollEntriesTable = new Table();
        scrollEntriesTable.debug();
        scrollEntriesTable.center();

        Table entriesTable = new Table();
        //entriesTable.debug();
        //entriesTable.left();

        final ScrollPane scroll = new ScrollPane(entriesTable, skin);
        //scroll.setScrollingDisabled(true, false);
        //scroll.setWidth(10);
        //scroll.setFadeScrollBars(true);


        InputListener stopTouchDown = new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return false;
            }
        };

        entriesTable.pad(10).defaults().expandX().space(4);
        TestDatabase.Result q = db.query("SELECT * FROM 'products'");
        int i = 0;
        while (!q.isEmpty()) {
            q.moveToNext();
            System.out.println("Product: " + q.getString(q.getColumnIndex("name")));
            entriesTable.row();

            entriesTable.add(new Label("Product: " + i, skin, "small-font")).expandX().fillX();
            entriesTable.add(new Label(q.getString(q.getColumnIndex("name")), skin, "small-font")).expandX().fillX();

            TextButton button = new TextButton(i + "Remove", skin, "small-font");
            entriesTable.add(button);
            button.addListener(new ClickListener() {
                public void clicked (InputEvent event, float x, float y) {
                    System.out.println("click " + x + ", " + y);
                    // TODO: Remove item
                }
            });
        }
        /*for (int i = 0; i < 100; i++) {
            entriesTable.row();
            entriesTable.add(new Label(i + "uno", skin, "small-font")).expandX().fillX();

            //entriesTable.findActor()

            TextButton button = new TextButton(i + "Remove", skin, "small-font");
            entriesTable.add(button);
            button.addListener(new ClickListener() {
                public void clicked (InputEvent event, float x, float y) {
                    System.out.println("click " + x + ", " + y);
                    // TODO: Remove item
                }
            });

            //Slider slider = new Slider(0, 100, 1, false, skin);
            //slider.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
            //entriesTable.add(slider);

            entriesTable.add(new Label(i + "tres long0 long1 long2 long3", skin, "small-font"));
        }
        */

        //entriesTable.right();
        //entriesTable.setFillParent(true);

        //Group groupEntries = new Group();

        //ScrollPane scrollEntries = new ScrollPane(groupEntries, skin);

        /*
        groupEntries.addActor(new Label("Apples", skin, "small-font"));
        groupEntries.addActor(new Label("Popcorn", skin));
        groupEntries.addActor(new Label("Mashed Potatoes", skin, "small-font"));
        */

        //entriesTable.add(groupEntries);
        //        entriesTable.addActor(new Label("Apples", skin, "small-font"));
//        entriesTable.add(new Label("Apples", skin, "small-font")).row();
//        entriesTable.add(new Label("Popcorn", skin, "small-font")).row();
//        entriesTable.add(new Label("Mashed Potatoes", skin, "small-font")).row();
//        entriesTable.pad(10);

        scrollEntriesTable.add(scroll).expand().fill().colspan(2);
        scrollEntriesTable.row().space(10).padBottom(10);

        // Combine all the tables to the main table
        mainTable.add(inputTable).align(Align.left);
        mainTable.add(scrollEntriesTable);//.align(Align.right);
        //mainTable.add(entriesTable).align(Align.right);

        //stage.addActor(entriesTable);
        stage.addActor(mainTable);



    }

    private void removeEntry() {

    }

    @Override
    public void render(float delta) {

        camera.update();
        //Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage.getBatch().setProjectionMatrix(camera.combined);
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
        //camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        //camera.update();
        camera.update();
        //stage.getCamera().position.set(stage.getCamera().viewportWidth/2, stage.getCamera().viewportHeight/2, 0);
        //stage.getCamera().update();
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
