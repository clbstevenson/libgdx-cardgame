package com.exovum.ld37warmup;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import com.exovum.ld37warmup.components.AnimationComponent;
import com.exovum.ld37warmup.components.BodyComponent;
import com.exovum.ld37warmup.components.BookComponent;
import com.exovum.ld37warmup.components.BookComponent.BookTitle;
import com.exovum.ld37warmup.components.BoundsComponent;
import com.exovum.ld37warmup.components.ChildComponent;
import com.exovum.ld37warmup.components.FontComponent;
import com.exovum.ld37warmup.components.SchoolComponent;
import com.exovum.ld37warmup.components.StateComponent;
import com.exovum.ld37warmup.components.TextureComponent;
import com.exovum.ld37warmup.components.TransformComponent;
import com.exovum.ld37warmup.systems.RenderingSystem;

import java.util.Random;

import static com.exovum.ld37warmup.components.BookComponent.BookTitle.getBookTitleByID;

/**
 * Created by exovu_000 on 12/9/2016.
 */

public class SchoolWorld {


    // PS: Use an abstract World class next time that supplies the base info
    // connecting SchoolWorld and GameWorld together for simplification

    enum State {
        RUNNING, PAUSED, GAMEOVER, GAMEWON, UPGRADE
    }

    public static final short ENT_SCHOOL = 0x0001;  // 0b0000_0000_0000_0001
    public static final short ENT_FRIENDLY_BOOK = 0x0002;  // 0b0000_0000_0000_0010
    public static final short ENT_ENEMY_BOOK = 0x0004;  // 0b0000_0000_0000_0100
    public static final short ENT_FRIENDLY_CHILD = 0x0008;  // 0b0000_0000_0000_1000
    public static final short ENT_ENEMY_CHILD = 0x0010; // 0b0000_0000_0001_0000
    public static final short ENT_BOUNDARY = 0x0020;  // 0b0000_0000_0010_0000

    public static final short GROUP_SCHOOL = 1;
    public static final short GROUP_BOOK = -2;
    public static final short GROUP_CHILD = -1;

    // Cooldown timer for controlling events such as throwing books
    // TODO: array of bookCooldown timers?
    private float bookCooldown, childCooldown;
    private Random random;
    private boolean paused;

    State state;

    World physicsWorld;
    public static final float PIXELS_TO_METERS = 16f; //16f;
    public static Vector2 screenInMeters = RenderingSystem.getScreenSizeInMeters();

    public static final float WORLD_WIDTH = 40f; // TODO: To Be Determined. Check with RenderingSystem
    public static final float WORLD_HEIGHT = 30f; // TODO: TBD. Check with RenderingSystem

    PooledEngine engine;
    Entity school;

    Entity leftText, rightText;
    // Entity used for drawing the current book quote
    Entity quoteEntity;
    // Entity used for tracking how many children have escaped and the maximum
    Entity healthEntity;
    // Entity for tracking and displaying points
    Entity pointsEntity;

    private static final String fontnameHeader = "chawp32.fnt";
    private static final String fontnameSubheader = "chawp18.fnt";


    Music music;

    int points;
    int missedChildren, maxChildren;

    public SchoolWorld(PooledEngine engine, World world) {
        this.engine = engine;
        this.physicsWorld = world;
        random = new Random();

    }

    public void create() {
        this.state = State.RUNNING;

        // reset points to 0
        points = 0;
        // reset health to 10: 10 children can get away before gameover
        missedChildren = 0;
        // maximum # of children that can be missed before game over
        maxChildren = 10;

        // Reset the timer cooldowns
        bookCooldown = 0f;
        childCooldown = 0f;

        music = Assets.getMusic();
        if (music != null) {
            Gdx.app.log("School World", "Start playing music");
            music.setVolume(0.25f);
            music.setLooping(true);
            music.play();
        }

        // initially create a
        //physicsWorld = new World(new Vector2(0f, 0f), true);

        //physicsWorld.setContactListener(new CollisionSystem(this, physicsWorld));

        school = generateSchool(WORLD_WIDTH / 2, WORLD_HEIGHT - SchoolComponent.HEIGHT * 3 / 4);

        generateBackground();
        //generateEmptyTextAreas("candara20.fnt");
        //leftText.getComponent(FontComponent.class).setText("LEFT AREA IS THE BEST AREA YEAH MAN SO COOL");
        //rightText.getComponent(FontComponent.class).setText("Right Area is much more docile and well-behaved when compared to the left area.");

        //generateBounds(WORLD_WIDTH * 2,5,WORLD_WIDTH + 10, WORLD_HEIGHT * 2);
        //new Vector2(screenInMeters.x - 2, screenInMeters.y / 2);
        //bodyShape.setAsBox(1, screenInMeters.y / 4);//, new Vector2(screenInMeters.x / 2, screenInMeters.y / 2), 0f);
        generateBounds(screenInMeters.x + 10, 1f, 1f, screenInMeters.y);
        generateBounds(-10, 1f, 1f, screenInMeters.y);

        healthEntity = generateTextWithFont("Children: " + missedChildren + " / " + maxChildren,
                WORLD_WIDTH - 6.25f, WORLD_HEIGHT - 2.75f, fontnameSubheader, Color.WHITE);
        //generateBoundsLine(WORLD_WIDTH  / 2 + 5, 0, WORLD_WIDTH / 2 + 5, WORLD_HEIGHT);
        pointsEntity = generateTextWithFont("Points " + points,
                5.5f, WORLD_HEIGHT - 2.75f, fontnameSubheader, Color.WHITE);

        pointsEntity.getComponent(FontComponent.class).glyph.setText(
                pointsEntity.getComponent(FontComponent.class).font, "Points: " + points);

        generateTextWithFont("One Room Schoolhouse", WORLD_WIDTH / 2, WORLD_HEIGHT,
                fontnameHeader, Color.WHITE);
        //generateTextWithFont(BookComponent.getRandomQuote(BookTitle.MOCKING, random),
        //        WORLD_WIDTH / 2, WORLD_HEIGHT / 4, "candara20.fnt", Color.WHITE);//, FontComponent.TYPE.TEMP);
        //generateText("A Simple Text", 10f, 10f);//WORLD_WIDTH / 2, WORLD_HEIGHT - SchoolComponent.HEIGHT);

        //generateTextWithFont("")


        // addChild();
        // removeChild();
        // updateChild();
        // generateUI();
    }

    public void update(float delta) {
        if (!paused) {
            //Gdx.app.log("School World", "Updating school world and cooldowns");
            // '1' bookCooldown = 1 second. so don't set bookCooldown to 1000
            bookCooldown -= delta;
            childCooldown -= delta;

            // create a new child every childCooldown seconds?
            if (childCooldown < 0) {
                createChild();
                childCooldown = 4f;
            }

            sweepDeadBodies();
        }
        //Gdx.app.log("School World", "Updating School World. bookCooldown: " + bookCooldown);
    }

    public void pause(boolean paused) {
        this.paused = paused;
        if (paused)
            music.pause();
        else
            music.play();

    }

    /**
     * Resets the game world. Used if the game is over [lost or won]
     */
    public void reset() {
        // Remove all the Box2D body entities
        for (Entity e : engine.getEntitiesFor(Family.all(BodyComponent.class).get())) {
            physicsWorld.destroyBody(e.getComponent(BodyComponent.class).body);
        }
        // Remove all the entities from the engine
        engine.removeAllEntities();
        engine.clearPools();
        // reset the music
        music.setPosition(0f);
        // reset points
        //missedChildren = 0;
        //points = 0;
        create();
    }

    public boolean isPaused() {
        return paused;
    }

    private void sweepDeadBodies() {
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        addPoints(1);
    }

    public void addPoints(int morePoints) {
        points += morePoints;
        FontComponent pointsFont = pointsEntity.getComponent(FontComponent.class);
        pointsFont.glyph.setText(pointsFont.font, "Points: " + points);
    }

    private void playSoundBookChild() {
        Assets.getSoundByName("hit-sound-1.wav").play(0.5f);
    }

    public void processBookChildHit(Object data) {
        EntityData entityData = ((EntityData)data);
        // entityData.getData(0) would be the ChildEntity. data(1) is BookEntity
        BodyComponent childBody = entityData.getData(0).getComponent(BodyComponent.class);
        // add points based on velocity of child: (child.vel.x + child.vel.y)
        addPoints((int)childBody.body.getLinearVelocity().x + (int)childBody.body.getLinearVelocity().y);
        //addPoints(1);

        playSoundBookChild();
        Gdx.app.log("School World", "Book-Child Hit. Points: " + points);
    }

    public void processChildSchoolHit(Object data) {
        EntityData entityData = ((EntityData)data);
        addPoints(2);
        Assets.getSoundByName("points.wav").play(0.5f);
        Gdx.app.log("School World", "Child-School Hit. Points: " + points);
        if(quoteEntity != null)
            quoteEntity.removeAll();
        quoteEntity = generateTextWithFont(getTextFromEntityData(entityData),
                WORLD_WIDTH / 2 - 1 , 5, "candara16b.fnt", Color.WHITE);

    }

    public void processChildBoundaryHit(Object data) {
        EntityData entityData = ((EntityData)data);
        if(entityData.getFirst().getComponent(BookComponent.class) != null) {
            // do nothing with health/missedChildren if the entity is a book
            return;
        }
        Gdx.app.log("School World", "Child-Boundary Hit. Lose points/health/something");

        missedChildren++;
        if(missedChildren >= maxChildren) {
            // switch to gameover
            this.state = State.GAMEOVER;
            Gdx.app.log("School World", "Miissed too many children. State = GAMEOVER");
        } else {
            if(healthEntity != null)
                healthEntity.removeAll();
            healthEntity = generateTextWithFont("Children: " + missedChildren + " / " + maxChildren,
                    WORLD_WIDTH - 6.25f, WORLD_HEIGHT - 2.75f, fontnameSubheader, Color.WHITE);
        }
    }



    private void generateEmptyTextAreas(String fontname) {
        Gdx.app.log("School World", "Setting up the text areas to display the BookCP.quote");
        Entity e1 = engine.createEntity();
        Entity e2 = engine.createEntity();

        FontComponent font1 = engine.createComponent(FontComponent.class);
        TransformComponent position1 = engine.createComponent(TransformComponent.class);

        FontComponent font2 = engine.createComponent(FontComponent.class);
        TransformComponent position2 = engine.createComponent(TransformComponent.class);

        font1.font = Assets.getFont(fontname);
        font1.glyph = new GlyphLayout();
        font1.targetWidth = 15f;
        font2.font = Assets.getFont(fontname);
        font2.glyph = new GlyphLayout();
        font2.targetWidth = 5f;
        font2.color = Color.RED;

        position1.position.set(WORLD_WIDTH / 2 - WORLD_WIDTH / 4,
                WORLD_HEIGHT / 2, 4f);//- SchoolComponent.HEIGHT* 3 / 4, 4f);
        position2.position.set(WORLD_WIDTH - 2f, WORLD_HEIGHT / 2, 4f);

        e1.add(font1);
        e1.add(position1);

        e2.add(font2);
        e2.add(position2);

        leftText = e1;
        rightText = e2;
    }

    private void generateText(String text, float x, float y) {
        Gdx.app.log("School World", "Generating font text entity");
        Entity e = engine.createEntity();

        FontComponent font = engine.createComponent(FontComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        //TODO: fix font rendering scaling: its too big!
        // font.font = Assets.getMediumFont();
        font.font = Assets.getFont("candara12.fnt");
        font.glyph = new GlyphLayout();
        font.glyph.setText(font.font, text);

        position.position.set(x, y, 2.0f); //TODO compare z-value with School's z-value

        //Remembering to add the components to the entity is a good idea
        e.add(font);
        e.add(position);

        engine.addEntity(e);
    }

    private Entity generateTextWithFont(String text, float x, float y, String fontname, Color color) {
        Gdx.app.log("School World", "Generating font text entity");
        return (generateTextWithFontType(text, x, y, fontname, color));

        /*
        Entity e = engine.createEntity();

        FontComponent font = engine.createComponent(FontComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        //TODO: fix font rendering scaling: its too big!
        // font.font = Assets.getMediumFont();
        font.font = Assets.getFont(fontname);
        font.glyph = new GlyphLayout();
        font.glyph.setText(font.font, text);
        //font.type = FontComponent.TYPE.PERM;

        position.position.set(x, y, 2.0f); //TODO compare z-value with School's z-value

        //Remembering to add the components to the entity is a good idea
        e.add(font);
        e.add(position);

        engine.addEntity(e);
        */

    }

    private Entity generateTextWithFontType(String text, float x, float y, String fontname, Color color) {
        Gdx.app.log("School World", "Generating font text entity with type");

        Entity e = engine.createEntity();

        FontComponent font = engine.createComponent(FontComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        //TODO: fix font rendering scaling: its too big!
        // font.font = Assets.getMediumFont();
        font.font = Assets.getFont(fontname);
        font.glyph = new GlyphLayout();
    // **font.glyph.setText(font.font, text);
        font.glyph.setText(font.font, text);
        float targetWidth = Math.min(font.glyph.width, RenderingSystem.getScreenSizeInPixels().x * 0.8f);
        font.glyph.setText(font.font, text, color, targetWidth, Align.center, true);
        //font.type = FontComponent.TYPE.PERM;

        position.position.set(x, y, 2.0f); //TODO compare z-value with School's z-value

        //Remembering to add the components to the entity is a good idea
        e.add(font);
        e.add(position);

        engine.addEntity(e);

        return e;

        /*
        Entity e = engine.createEntity();

        FontComponent font = engine.createComponent(FontComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        //TODO: fix font rendering scaling: its too big!
        // font.font = Assets.getMediumFont();
        font.font = Assets.getFont(fontname);
        font.glyph = new GlyphLayout();
        font.glyph.setText(font.font, text);
        //font.type = fontType;
        if(fontType != FontComponent.TYPE.PERM)
            font.displayTime = 2.5f;

        position.position.set(x, y, 2.0f); //TODO compare z-value with School's z-value

        //Remembering to add the components to the entity is a good idea
        e.add(font);
        e.add(position);

        engine.addEntity(e);
        */
    }

    /**
     * Generates a bounding Body component starting at (x,y) with specified width and height
     * @param x starting point
     * @param y starting point
     * @param width width of the boundary
     * @param height height of the boundary
     */
    private void generateBounds(float x, float y, float width, float height) {
        Entity e = engine.createEntity();

        TransformComponent position = engine.createComponent(TransformComponent.class);
        BodyComponent body = engine.createComponent(BodyComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);

        position.position.set(x, y, 0f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        body.body = physicsWorld.createBody(bodyDef);
        PolygonShape bodyShape = new PolygonShape();
        //bodyShape.setAsBox(2, screenInMeters.y / 4);//, new Vector2(screenInMeters.x / 2, screenInMeters.y / 2), 0f);
        bodyShape.setAsBox(width, height);
        ////width / 8, height / 8);//width/PIXELS_TO_METERS, height/PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 2f;
        fixtureDef.filter.categoryBits = ENT_BOUNDARY;
        // Schools collide with Enemy Child and Friendly Child
        fixtureDef.filter.maskBits = ENT_ENEMY_CHILD | ENT_FRIENDLY_CHILD | ENT_FRIENDLY_BOOK;

        //fixtureDef.filter.groupIndex = GROUP_BOOK;

        body.body.createFixture(fixtureDef);
        bodyShape.dispose();

        e.add(position);
        e.add(body);
        e.add(bounds);

        engine.addEntity(e);
        body.body.setUserData(new EntityData(e));
        //body.body.setUserData(e);
    }

    /**
     * Generates a bounding Body component starting at (x,y) with specified width and height
     * @param x1 starting point x-value
     * @param y1 starting point y-value
     * @param x2 ending point x-value
     * @param y2 ending point y-value
     */
    private void generateBoundsLine(float x1, float y1, float x2, float y2) {
        Entity e = engine.createEntity();

        TransformComponent position = engine.createComponent(TransformComponent.class);
        BodyComponent body = engine.createComponent(BodyComponent.class);

        position.position.set(x1, y1, 0f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x1, y1);

        body.body = physicsWorld.createBody(bodyDef);
        EdgeShape bodyShape = new EdgeShape();
        bodyShape.set(x1/PIXELS_TO_METERS, y1/PIXELS_TO_METERS, x2/PIXELS_TO_METERS, y2/PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 2f;
        fixtureDef.filter.categoryBits = ENT_BOUNDARY;
        // Schools collide with Enemy Child and Friendly Child
        fixtureDef.filter.maskBits = ENT_ENEMY_CHILD | ENT_FRIENDLY_CHILD | ENT_FRIENDLY_BOOK;

        //fixtureDef.filter.groupIndex = GROUP_BOOK;

        body.body.createFixture(fixtureDef);
        bodyShape.dispose();

        e.add(position);
        e.add(body);

        engine.addEntity(e);

        EntityData data = new EntityData(e);
        body.body.setUserData(data);
    }

    private void generateBackground() {
        Entity e = engine.createEntity();

        // make it a school entity
        //SchoolComponent school = engine.createComponent(SchoolComponent.class);
        // Use Texture instead of AnimationComponent because the School is just a sprite
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        //StateComponent state = engine.createComponent(StateComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        texture.region = Assets.getBackgroundSprite();

        // TODO scale the texture to fit SchoolComponent width and height
        float scaleX = WORLD_WIDTH/ (RenderingSystem.PixelsToMeters(texture.region.getRegionWidth()));
        float scaleY = WORLD_HEIGHT / (RenderingSystem.PixelsToMeters(texture.region.getRegionHeight()));

        //position.position.set(x - SchoolComponent.WIDTH, y, 1.0f);
        position.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 7.0f); // does a lower z-value mean closer or farther?
        // I believe low z-values means closer to the "top"
        // so high-z values will appear UNDER low-z values
        position.scale.set(scaleX, scaleY);  //0.75f, 0.5f);
        //position.scale.set(0.5f, 0.5f);

        //state.set(SchoolComponent.STATE_NORMAL);

        e.add(texture);
        e.add(position);
        //e.add(state);

        engine.addEntity(e);
    }

    /**
     * Creates a new School entity centered around @param x and @param y
     *
     * @param x horizontal value of center point for the school
     * @param y vertical value of center point for the school
     */
    private Entity generateSchool(float x, float y) {
        Entity e = engine.createEntity();

        // make it a school entity
        SchoolComponent school = engine.createComponent(SchoolComponent.class);
        // Use Texture instead of AnimationComponent because the School is just a sprite
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        // use BodyComponent to check when a child reaches the school
        BodyComponent body = engine.createComponent(BodyComponent.class);
        // TODO add BodyComponent so it can be processed by the Box2D world?

        texture.region = Assets.getSchoolSprite();

        // TODO scale the texture to fit SchoolComponent width and height
        float scaleX = SchoolComponent.WIDTH / (RenderingSystem.PixelsToMeters(texture.region.getRegionWidth()));
        float scaleY = SchoolComponent.HEIGHT / (RenderingSystem.PixelsToMeters(texture.region.getRegionHeight()));

        //position.position.set(x - SchoolComponent.WIDTH, y, 1.0f);
        position.position.set(x, y, 5.0f); // does a lower z-value mean closer or farther?
                                            // I believe low z-values means closer to the "top"
                                            // so high-z values will appear UNDER low-z values
        position.scale.set(scaleX, scaleY);  //0.75f, 0.5f);
        //position.scale.set(0.5f, 0.5f);

        state.set(SchoolComponent.STATE_NORMAL);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        //bodyDef.position.set((fromX + texture.region.getRegionWidth()/2) / PIXELS_TO_METERS,
        //        (fromY + texture.region.getRegionHeight()/2) / PIXELS_TO_METERS);

        body.body = physicsWorld.createBody(bodyDef);
        //apply impulse

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox((SchoolComponent.WIDTH / 8), (SchoolComponent.HEIGHT / 6));
        //bodyShape.setAsBox(SchoolComponent.WIDTH * 4/ PIXELS_TO_METERS / scaleX,
        //        SchoolComponent.HEIGHT * 4/ PIXELS_TO_METERS / scaleY); //WIDTH * 0.25f, HEIGHT* 0.3f);
        //bodyShape.setAsBox(128 / PIXELS_TO_METERS, 128 / PIXELS_TO_METERS);
        //bodyShape.setAsBox(BookComponent.WIDTH * 2 / PIXELS_TO_METERS,
        //        BookComponent.HEIGHT * 2 / PIXELS_TO_METERS);
        //bodyShape.setRadius(BookComponent.WIDTH / 2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = -0.5f;
        fixtureDef.filter.categoryBits = ENT_SCHOOL;
        // Schools collide with Enemy Child and Friendly Child
        fixtureDef.filter.maskBits = ENT_ENEMY_CHILD | ENT_FRIENDLY_CHILD;

        //fixtureDef.filter.groupIndex = GROUP_BOOK;

        body.body.createFixture(fixtureDef);
        bodyShape.dispose();

        e.add(school);
        e.add(texture);
        e.add(position);
        e.add(state);
        e.add(body);

        engine.addEntity(e);
        EntityData data = new EntityData(e);
        body.body.setUserData(data);
        return e;
    }

    /**
     * Create a Book entity aimed at (x,y)
     *
     * @param x x-value of target position
     * @param y y-value of target position
     */
    public void throwBook(float x, float y) {
        if (bookCooldown < 0) {
            Gdx.app.log("School World", "Starting to throw book");

            // get a value from [0, number of booktitles ) excluding end point

            //TODO
            //Entity e = engine.getEntitiesFor(Family.all(SchoolComponent.class).get()).first();

            //if(e == null || e.getComponents().
            //createBook(random.nextInt(BookTitle.values().length), engine.getEntitiesFor(Family.all(SchoolComponent.)), y);
            //TODO: change the x and y values. they spawn where they are clicked.
            // they should start at the schoolhouse and move towards point (x,y) instead.
            //createBook(random.nextInt(BookTitle.values().length), x, y);
            //                                                  fromX, fromY, toX, toY);
            // start throwing the book from the position of the SchoolComponent
            createBook(random.nextInt(BookTitle.values().length),
                    school.getComponent(TransformComponent.class).position.x,
                    school.getComponent(TransformComponent.class).position.y,
                    x, y);
                    //x / PIXELS_TO_METERS, WORLD_HEIGHT - (y / PIXELS_TO_METERS));
            //reset the bookCooldown timer
            bookCooldown = 1;
        }
    }

    /**
     * Creates a new Book entity from (fromX, fromY) aiming for (toX, toY)
     *
     * @param bookid  enum BookTitle for the new book
     * @param fromX  x-value of the starting location for the book
     * @param fromY  y-value of the starting location for the book
     * @param toX x-value for the position the book is aimed
     * @param toY y-value for the position the book is aimed
     */
    private void createBook(int bookid, float fromX, float fromY, float toX, float toY) {
        Entity e = new Entity(); // OR engine.creatEntity(); not sure which is better

        BookComponent book = engine.createComponent(BookComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        //TODO: add BodyComponent for collisions
        BodyComponent body = new BodyComponent();

        BookTitle title = getBookTitleByID(bookid);
        book.title = title;

        state.set(BookComponent.STATE_THROWN);

        animation.animations.put(BookComponent.STATE_THROWN, Assets.getBookByName(title.getAssetName()));
        animation.animations.put(BookComponent.STATE_CAUGHT, Assets.getHeldBookByName(title.getAssetName()));
        //texture.region = animation.animations.get(state.get()).getKeyFrame(0f);
        if(toX < fromX) {
            //toX
        }
        //animation.animations.put(TreeComponent.STATE_THROWN, Assets.treeNormal);
        //animation.animations.put(TreeComponent.STATE_CAUGHT, Assets.treeLights);

        // TODO use BookComponent.width and BookComponent.height for Bounds -> and BodyComponent
        position.position.set(fromX, fromY, 3.0f);
        position.scale.set(0.5f, 0.5f); // TODO: check if scaling is OK

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(fromX, fromY);
        //bodyDef.position.set((fromX + texture.region.getRegionWidth()/2) / PIXELS_TO_METERS,
        //        (fromY + texture.region.getRegionHeight()/2) / PIXELS_TO_METERS);

        body.body = physicsWorld.createBody(bodyDef);
        //apply impulse

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(BookComponent.WIDTH * 0.25f, BookComponent.HEIGHT * 0.3f);
        //bodyShape.setAsBox(128 / PIXELS_TO_METERS, 128 / PIXELS_TO_METERS);
        //bodyShape.setAsBox(BookComponent.WIDTH * 2 / PIXELS_TO_METERS,
        //        BookComponent.HEIGHT * 2 / PIXELS_TO_METERS);
        //bodyShape.setRadius(BookComponent.WIDTH / 2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = -0.5f;
        //fixtureDef.filter.groupIndex = GROUP_BOOK;
        fixtureDef.filter.categoryBits = ENT_FRIENDLY_BOOK;
        // Books collide with Enemy Child, but NOT Friendly Child
        fixtureDef.filter.maskBits = ENT_ENEMY_CHILD | ENT_BOUNDARY;


        // calculate force needed to push object from (fromX, fromY) to (toX, toY);
        double distance = Math.sqrt( (Math.pow(toX - fromX, 2)) + (Math.pow(toY - fromY, 2)) );
        // distance from (fromX, fromY) to (toX, toY)
        float distX = toX - fromX;
        float distY = toY - fromY;
        // time for the book to travel from point to point
        float time = 1f; // 1 second?
        // Final velocity values
        float velXf = 5f;
        float velYf = 5f;
        // solve for initial velocities: Vi = (d/t * 2) - Vf
        float velXi =  (distX / time) - velXf;
        float velYi = (distY / time) - velYf;
        float mass2 = (BookComponent.WIDTH / 2 /PIXELS_TO_METERS) *
                (BookComponent.HEIGHT /2 /PIXELS_TO_METERS)* fixtureDef.density;
        float mass = 0f;//5f;
        // impulse = mass * velocity [x and y accordingly]
        // Apply the calculated Impulses
        //Gdx.app.log("School World", "Applying impulse to new book. From (" + fromX + ", " + fromY +
        //        ") To (" + toX + ", " + toY + ")");
        //body.body.applyLinearImpulse(mass2 * velXi, mass2 * velYi, fromX, fromY, true);
        // Setting linear veolicity based on velXi and velYi is not centering the book correctly. WIP
        //body.body.setLinearVelocity(velXi,velYi);
        //body.body.setLinearVelocity(velXi, velYi);
        //body.body.applyForceToCenter(0f, -10f, true);

        Vector2 direction = new Vector2(toX, toY);
        direction.sub(body.body.getPosition());
        direction.nor();

        float speed = 10;
        body.body.setLinearVelocity(direction.scl(speed));
        //body.body.applyForce(10f, 10f, fromX + 10, fromY + 10, true);//applyTorque(0.4f, true);

        if(distX > 0) {
            body.body.setAngularVelocity(-5f);
        } else {
            body.body.setAngularVelocity(5f);
        }


        body.body.createFixture(fixtureDef);
        bodyShape.dispose();

        e.add(book);
        e.add(animation);
        e.add(state);
        e.add(texture);
        e.add(position);
        e.add(body);


        engine.addEntity(e);
        // add the entity as user data to the body - used for collisions
        EntityData data = new EntityData(e);
        body.body.setUserData(data);
    }

    /**
     * Creates a new Child entity. It can spawn either from the left or right
     */
    private void createChild() {
        Entity e = new Entity(); // OR engine.creatEntity(); not sure which is better

        ChildComponent child = engine.createComponent(ChildComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        //TODO: add BodyComponent for collisions
        BodyComponent body = new BodyComponent();

        //TODO: name the children?
        //BookTitle title = getBookTitleByID(bookid);

        state.set(ChildComponent.STATE_RUNNING);

        animation.animations.put(ChildComponent.STATE_RUNNING, Assets.getChildAnimationByName("blue-b"));//title.getAssetName()));
        animation.animations.put(ChildComponent.STATE_READING, Assets.getChildAnimationByName("blue-reading"));
        animation.animations.put(ChildComponent.STATE_RELEASED, Assets.getChildAnimationByName("blue-released"));

        // TODONE  use BookComponent.width and BookComponent.height for Bounds -> and BodyComponent
        // TODO: randomly set y-value and flip x-values so they spawn on both sides of screen
        position.position.set(-2f, 10f, 6.0f);
        position.scale.set(0.5f, 0.5f); // TODO: check if scaling is OK

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-2f, 10f);
        //bodyDef.position.set((fromX + texture.region.getRegionWidth()/2) / PIXELS_TO_METERS,
        //        (fromY + texture.region.getRegionHeight()/2) / PIXELS_TO_METERS);

        body.body = physicsWorld.createBody(bodyDef);
        //apply impulse

        CircleShape bodyShape = new CircleShape();
        bodyShape.setRadius(ChildComponent.RADIUS * 5 / PIXELS_TO_METERS);
        //bodyShape.setAsBox(BookComponent.WIDTH * 0.25f, BookComponent.HEIGHT * 0.3f);
        //bodyShape.setAsBox(128 / PIXELS_TO_METERS, 128 / PIXELS_TO_METERS);
        //bodyShape.setAsBox(BookComponent.WIDTH * 2 / PIXELS_TO_METERS,
        //        BookComponent.HEIGHT * 2 / PIXELS_TO_METERS);
        //bodyShape.setRadius(BookComponent.WIDTH / 2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = -0.5f;
        //fixtureDef.filter.groupIndex = GROUP_CHILD;
        fixtureDef.filter.categoryBits = ENT_ENEMY_CHILD;
        // Children collide with School, Friendy Book, and Enemy Book
        fixtureDef.filter.maskBits = ENT_SCHOOL | ENT_FRIENDLY_BOOK | ENT_ENEMY_BOOK | ENT_BOUNDARY;

        // TODO consider moving children at diagonals with direction.y
        Vector2 direction = new Vector2((position.position.x < WORLD_WIDTH / 2) ? 5f : -5f,
                0f);
        //direction.sub(body.body.getPosition());
        //direction.nor();

        float speed = 6;
        body.body.setLinearVelocity(direction);
        //body.body.applyForce(10f, 10f, fromX + 10, fromY + 10, true);//applyTorque(0.4f, true);

        body.body.createFixture(fixtureDef);

        bodyShape.dispose();

        e.add(child);
        e.add(animation);
        e.add(state);
        e.add(texture);
        e.add(position);
        e.add(body);

        engine.addEntity(e);
        // add the entity as user data to the body - used for collisions
        EntityData data = new EntityData(e);
        body.body.setUserData(data);
    }

    // Veloctiy/Direction helper methods
    public Vector2 getVelocityTowardsSchool(float speed, float x, float y) {
        return getVelocityTo(speed, x, y, school.getComponent(TransformComponent.class).position.x,
                school.getComponent(TransformComponent.class).position.y);
    }

    private Vector2 getVelocityTo(float speed, float fromX, float fromY, float toX, float toY) {
        Vector2 direction = new Vector2(toX, toY);
        direction.sub(fromX, fromY);
        direction.nor();

        //body.body.setLinearVelocity(direction.scl(speed));
        return direction.scl(speed);
    }

    private String getTextFromEntityData(EntityData data) {
        Entity e = data.getData(1);
        BookComponent book = e.getComponent(BookComponent.class);
        if(book == null)
            return "NO BOOK DATA";
        return BookComponent.getRandomQuote(book.title, random);
    }

    public void updateTextField(Object userData) {
        Gdx.app.log("School World", "Updating text field based on user data");
        //BookComponent book = ((BookComponent)userData);
        EntityData data = ((EntityData)userData);
        Entity e = data.getData(1);
        BookComponent book = e.getComponent(BookComponent.class);
        if(book == null) // do nothing
            return;
        //TODO: update text field with a random quote from book
        generateTextWithFontType(BookComponent.getRandomQuote(book.title,random),WORLD_WIDTH/2,
                WORLD_HEIGHT, "candara20.fnt", Color.PURPLE);//, FontComponent.TYPE.TEMP);
    }
}
