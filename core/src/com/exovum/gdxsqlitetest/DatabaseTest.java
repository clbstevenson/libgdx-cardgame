package com.exovum.gdxsqlitetest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * Created by exovu on 3/18/2017.
 */

public class DatabaseTest extends Game{

    Database dbHandler;

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "comments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_COMMENTS + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_COMMENT
            + " VARCHAR NOT NULL);";

    private Stage stage;
    private TextButton textButton;
    private Label statusLabel;
    private Skin skin;

    public DatabaseTest() {


    }

    @Override
    public void create() {
        Gdx.app.log("DatabaseTest", "Creation Started");
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        Gdx.app.log("DatabaseTest", "Creation Success");

        //skin = new Skin(Gdx.files.internal("ui/uiskin-2.atlas"));
        //skin = new Skin(Gdx.files.internal("ui/handfont-test.json"));
        skin = new Skin(Gdx.files.internal("ui/handfont-test.json"),
                new TextureAtlas("ui/handfont-test.atlas"));
        //skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(), new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);

//        Table mainTable = new Table();
//        mainTable.center();
//        mainTable.align(Align.center);
//        mainTable.setFillParent(true);
//        stage.addActor(mainTable);

        statusLabel = new Label("", skin);
        statusLabel.setWrap(true);
        statusLabel.setWidth(Gdx.graphics.getWidth() * 0.96f);
        statusLabel.setAlignment(Align.center);
        statusLabel.setPosition(Gdx.graphics.getWidth() * 0.5f - statusLabel.getWidth() * 0.5f, 30f);
        stage.addActor(statusLabel);

        textButton = new TextButton("Insert Data", skin);
        textButton.setPosition(Gdx.graphics.getWidth() * 0.5f - textButton.getWidth() * 0.5f, 60f);
        //textButton.align(Align.center);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                try {
                    dbHandler.execSQL("INSERT INTO comments ('comment') " +
                            "VALUES ('A test comment')");
                } catch (SQLiteGdxException e) {
                    e.printStackTrace();
                }

                DatabaseCursor cursor = null;

                try {
                    cursor = dbHandler.rawQuery("SELECT * FROM comments");
                } catch (SQLiteGdxException e) {
                    e.printStackTrace();
                }

                while(cursor.next()) {
                    statusLabel.setText(String.valueOf(cursor.getInt(0)));
                }

                try {
                    cursor = dbHandler.rawQuery(cursor, "SELECT * FROM comments");
                } catch (SQLiteGdxException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.addActor(textButton);

        /*
        try {
            dbHandler.execSQL("INSERT INTO comments ('comment') " +
                    "VALUES ('A test comment');");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        DatabaseCursor cursor = null;

        try {
            cursor = dbHandler.rawQuery("SELECT * FROM comments");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        while (cursor.next()) {
            Gdx.app.log("FromDB", String.valueOf(cursor.getString(1)));
        }

        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        dbHandler = null;
        Gdx.app.log("DatabaseTest", "Disposing");
        */
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        dbHandler = null;
        Gdx.app.log("DatabaseTest", "Disposing from DatabaseTest");
    }
}
