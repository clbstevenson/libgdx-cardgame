package com.exovum.dbsample;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.exovum.main.ActionResolver;

/**
 * Created by exovu on 3/18/2017.
 */

public class DynamoDBSample extends Game {

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

    private ActionResolver actionResolver;

    public DynamoDBSample(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }

    @Override
    public void create() {
        Gdx.app.log("DynamoDBSample", "Creation Started");

        Gdx.app.log("DynamoDBSample", "Setting up AWS Cognito Credentials");
        actionResolver.setupAWSCredentials();

        Gdx.app.log("DynamoDBSample", "Setting up DynamoDB connections");
        actionResolver.setupDynamoDB();

        /*dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }*/

        Gdx.app.log("DynamoDBSample", "Creation Success");

        //skin = new Skin(Gdx.files.internal("ui/uiskin-2.atlas"));
        //skin = new Skin(Gdx.files.internal("ui/handfont-test.json"));
        skin = new Skin(Gdx.files.internal("ui/handfont-test.json"),
                new TextureAtlas("ui/handfont-test.atlas"));
        //skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new ScalingViewport(Scaling.stretch, 800, 480, new OrthographicCamera())); //Gdx.graphics.getWidth(),
                //Gdx.graphics.getHeight(), new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);

//        Table mainTable = new Table();
//        mainTable.center();
//        mainTable.align(Align.center);
//        mainTable.setFillParent(true);
//        stage.addActor(mainTable);

        // Create object to store DynamoDB - Book data
        Book book = new Book();
        book.setTitle("Great Expectation");
        book.setAuthor("Charles Dickens");
        book.setPrice(1299);
        book.setIsbn("123456789");
        book.setHardCover(false);

        // Store the book to the DynamoDBMapper
        //actionResolver.saveObjectToMapper(book);
        actionResolver.saveBookToMapper(book);

        // ...

        // Retrieve a Book object from DynamoDBMapper
        //Book selectedBook = actionResolver.getBookFromMapper("123456789");


        statusLabel = new Label("", skin);
        statusLabel.setWrap(true);
        statusLabel.setWidth(Gdx.graphics.getWidth() * 0.96f);
        statusLabel.setAlignment(Align.center);
        statusLabel.setPosition(Gdx.graphics.getWidth() * 0.5f - statusLabel.getWidth() * 0.5f, 30f);
        stage.addActor(statusLabel);

        textButton = new TextButton("Find Book", skin);
        textButton.setPosition(Gdx.graphics.getWidth() * 0.5f - textButton.getWidth() * 0.5f, 60f);
        //textButton.align(Align.center);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //Gdx.app.log("DynamoDB")
                //statusLabel.setText("Hey! Hands off!");
                // Retrieve a Book object from DynamoDBMapper
                Book selectedBook = actionResolver.getBookFromMapper("123456789");
                if (selectedBook == null) {
                    statusLabel.setText("This is not the book you are looking for..");
                } else {
                    statusLabel.setText("'" + selectedBook.getTitle() + "' by " +
                            selectedBook.getAuthor() + " is available for " +
                            selectedBook.getPrice() + ". Hardcover? That's " +
                            selectedBook.getHardCover());
                }


                /*try {
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
                }*/
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
        //Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(.8f, .82f, .86f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        /*try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        dbHandler = null;*/
        Gdx.app.log("DynamoDBSample", "Disposing from DynamoDBSample");
    }
}
