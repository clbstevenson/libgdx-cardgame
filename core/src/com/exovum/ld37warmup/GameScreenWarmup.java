package com.exovum.ld37warmup;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.exovum.ld37warmup.components.*;
import com.exovum.ld37warmup.systems.AnimationSystem;
import com.exovum.ld37warmup.systems.PhysicsDebugSystem;
import com.exovum.ld37warmup.systems.PhysicsSystem;
import com.exovum.ld37warmup.systems.RenderingSystem;
import com.exovum.ld37warmup.systems.UselessStateSwapSystem;

/**
 * Created by exovu_000 on 12/3/2016.
 */

public class GameScreenWarmup extends ScreenAdapter {

    Game game; // Might need this defined as LD37Game Game instead

    private boolean initialized;
    private float elapsedTime = 0f;
    private int secondsToSplash = 10;
    private World world; // **Box2D** World

    private GameWorld gameWorld; // class for creating entities and adding to the world of the game

    private PooledEngine engine; // Ashley ECS engine

    private SpriteBatch batch;
    private IScreenDispatcher dispatcher;

    // TODO add InputListener, InputAdapter etc

    public GameScreenWarmup(Game game, SpriteBatch batch, IScreenDispatcher dispatcher) {
        super();
        this.game = game;
        this.batch = batch;
        this.dispatcher = dispatcher;
    }

    private void init() {
        Gdx.app.log("Game Screen Warmup", "Initializing");
        initialized = true;

        // world has 0 x-acceleration, and -9.8f y-accel [gravity]
        world = new World(new Vector2(0f, -9.8f), true);
        engine = new PooledEngine();
        gameWorld = new GameWorld(engine);

        // create ECS system to process rendering. renders entities via TextureComponents
        RenderingSystem renderingSystem = new RenderingSystem(batch);
        engine.addSystem(renderingSystem);
        engine.addSystem(new AnimationSystem());
        // add ECS system to process physics in the Box2D world
        engine.addSystem(new PhysicsSystem(world));

        engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
        engine.addSystem(new UselessStateSwapSystem());

        //Entity e = buildBall(world);
        //Entity e = buildTree(world);
        //engine.addEntity(e);
        //engine.addEntity(buildTreeEntity(world));
        //buildSmallForest(engine, world);
        //Array<Entity> trees = getTreeLayer(world);
        //for(Entity treeEntity: getTreeLayer(world)) {
        //    engine.addEntity(treeEntity);
        //}
        //addTreeEntityAt(world, 4f, 2f);
        //addTreeEntityAt(world, 10f, 2f);
        //addTreeEntityAt(world, 14f, 2f);
        //addTreeLayer(world);

        //addTreeEntityAt2(world, 14f, 2f);
        //addTreeEntityAt2(world, 20f, 20f); // please work ps it doesnt want to I guess

        gameWorld.create();

        initialized = true;
    }

    private void update(float delta) {
        engine.update(delta);

        elapsedTime += delta;
        //if elapsedTime/1000f > secondsToEvent { then do stuff }
    }

    @Override
    public void render(float delta) {
        if(initialized) {
            update(delta);
        } else {
            init();
        }
    }

    private Entity buildTree(World world) {
        Entity e = engine.createEntity();
        e.add(new TreeComponent());

        AnimationComponent a = new AnimationComponent();
        a.animations.put("DEFAULT", new Animation(1f/16f, Assets.getTreeNormalArray(), Animation.PlayMode.LOOP));
        a.animations.put("RUNNING", new Animation(1f/8f, Assets.getTreeLightsArray(), Animation.PlayMode.LOOP));
        e.add(a);
        StateComponent state = new StateComponent();
        state.set("DEFAULT");
        e.add(state);
        TextureComponent tc = new TextureComponent();
        e.add(tc);

        TransformComponent tfc = new TransformComponent();
        tfc.position.set(10f, 10f, 1f);
        tfc.rotation = 15f;
        tfc.scale.set(0.25f, 0.25f);
        e.add(tfc);

        // setup the physics body
        BodyComponent bc = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(10f, 23f);

        bc.body = world.createBody(bodyDef);
        //bc.body.applyAngularImpulse(50f, true); // apply impulse to move body

        CircleShape circle = new CircleShape();
        circle.setRadius(tc.region.getRegionWidth());

        //Create fixture definition to apply to the shape
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 40f;
        fixtureDef.friction = 0.4f;

        return e;
    }

    private Entity buildTreeEntity(World world) {
        // Call buildTreeEntityAt(world, position) where position is a default Vector3 value
        return buildTreeEntityAt(world, new Vector3(10f, 2.2f, 1));

        /*
        Entity e = engine.createEntity();
        // Add Tree Component to Tree Entity
        e.add(new TreeComponent());

        // Add Animation Component to Tree Entity
        AnimationComponent a = new AnimationComponent();
        // for tag "DEFAULT" put an animation with parameter: duration=1/8f, TextureRegion, PlayMode;
        a.animations.put("DEFAULT", new Animation(1f/8f, Assets.getTreeNormalArray(), Animation.PlayMode.LOOP));
        a.animations.put("RUNNING", new Animation(1f/8f, Assets.getTreeLightsArray(), Animation.PlayMode.LOOP));
        e.add(a);

        // Add State Component to Tree Entity
        StateComponent state = new StateComponent();
        state.set("RUNNING");
        e.add(state);

        // Add Texture Component to Tree Entity
        TextureComponent tc = new TextureComponent();
        e.add(tc);

        // Use a Vector3 so the TransformComponent and the Body are at the same location
        Vector3 position = new Vector3(10f, 2f, 1f);

        // Add TransformComponent to Tree Entity
        TransformComponent tfc = new TransformComponent();
        //tfc.position.set(30f, 60f, 1f);
        tfc.position.set(position);
        tfc.rotation = 40f;
        tfc.scale.set(0.25f, 0.25f);
        e.add(tfc);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // set position of body into the world
        //bodyDef.position.set(new Vector2(6f, 1f));
        //bodyDef.position.set(10f, 23f);
        // Use the X and Y values from the Vector3. Make bodyDef and tfc use same position
        bodyDef.position.set(position.x, position.y);

        BodyComponent bc = new BodyComponent();
        // create body from bodyDef and add to the world
        bc.body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(2f);
        //PolygonShape treeBox = new PolygonShape();
        //treeBox.setAsBox(5f, 1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // makes it bounce slightly
        bc.body.createFixture(fixtureDef);
        //bc.body.createFixture(treeBox, 0.0f);

        // Clean Up
        circle.dispose();
        //treeBox.dispose();

        // Add Body Component to Tree Entity
        e.add(bc);

        // This isn't used anymore?
        Vector2 screenMeters = RenderingSystem.getScreenSizeInMeters();
        Gdx.app.log("Game Screen Warmup", "Screen Meters: " + screenMeters.x + " x " + screenMeters.y);
        Vector2 screenPixels = RenderingSystem.getScreenSizeInPixels();
        Gdx.app.log("Game Screen Warmup", "Screen Pixels:" + screenPixels.x + " x " + screenPixels.y);

        return e;
        */
    }

    private Entity buildTreeEntityAt(World world, Vector3 position) {
        Entity e = new Entity(); //or? engine.createEntity();
        // Add Tree Component to Tree Entity
        e.add(new TreeComponent());

        // Add Animation Component to Tree Entity
        AnimationComponent a = new AnimationComponent();
        // for tag "DEFAULT" put an animation with parameter: duration=1/8f, TextureRegion, PlayMode;
        a.animations.put("DEFAULT", new Animation(1f/8f, Assets.getTreeNormalArray(), Animation.PlayMode.LOOP));
        a.animations.put("RUNNING", new Animation(1f/8f, Assets.getTreeLightsArray(), Animation.PlayMode.LOOP));
        e.add(a);

        // Add State Component to Tree Entity
        StateComponent state = new StateComponent();
        state.set("RUNNING");
        e.add(state);

        // Add Texture Component to Tree Entity
        TextureComponent tc = new TextureComponent();
        e.add(tc);

        // Use a Vector3 so the TransformComponent and the Body are at the same location
        // * Use the parameter instead for position
        //Vector3 position = new Vector3(10f, 2f, 1f);

        // Add TransformComponent to Tree Entity
        TransformComponent tfc = new TransformComponent();
        //tfc.position.set(30f, 60f, 1f);
        tfc.position.set(position);
        tfc.rotation = 40f;
        tfc.scale.set(0.25f, 0.25f);
        e.add(tfc);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // set position of body into the world
        //bodyDef.position.set(new Vector2(6f, 1f));
        //bodyDef.position.set(10f, 23f);
        // Use the X and Y values from the Vector3. Make bodyDef and tfc use same position
        bodyDef.position.set(position.x, position.y);

        BodyComponent bc = new BodyComponent();
        // create body from bodyDef and add to the world
        bc.body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(2f);
        //PolygonShape treeBox = new PolygonShape();
        //treeBox.setAsBox(5f, 1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // makes it bounce slightly
        bc.body.createFixture(fixtureDef);
        //bc.body.createFixture(treeBox, 0.0f);

        // Clean Up
        circle.dispose();
        //treeBox.dispose();

        // Add Body Component to Tree Entity
        e.add(bc);

        // This isn't used anymore?
        Vector2 screenMeters = RenderingSystem.getScreenSizeInMeters();
        Gdx.app.log("Game Screen Warmup", "Screen Meters: " + screenMeters.x + " x " + screenMeters.y);
        Vector2 screenPixels = RenderingSystem.getScreenSizeInPixels();
        Gdx.app.log("Game Screen Warmup", "Screen Pixels:" + screenPixels.x + " x " + screenPixels.y);

        return e;
    }

    private void addTreeEntityAt(World world, float x, float y) {
        Entity e = engine.createEntity(); //new Entity(); //or? engine.createEntity();
        // Add Tree Component to Tree Entity
        e.add(new TreeComponent());

        // Add Animation Component to Tree Entity
        AnimationComponent a = new AnimationComponent();
        // for tag "DEFAULT" put an animation with parameter: duration=1/8f, TextureRegion, PlayMode;
        a.animations.put("DEFAULT", new Animation(1f/8f, Assets.getTreeNormalArray(), Animation.PlayMode.LOOP));
        a.animations.put("RUNNING", new Animation(1f/8f, Assets.getTreeLightsArray(), Animation.PlayMode.LOOP));
        e.add(a);

        // Add State Component to Tree Entity
        StateComponent state = new StateComponent();
        state.set("RUNNING");
        e.add(state);

        // Add Texture Component to Tree Entity
        TextureComponent tc = new TextureComponent();
        e.add(tc);

        // Use a Vector3 so the TransformComponent and the Body are at the same location
        // * Use the parameter instead for position
        //Vector3 position = new Vector3(10f, 2f, 1f);

        // Add TransformComponent to Tree Entity
        TransformComponent tfc = new TransformComponent();
        //tfc.position.set(30f, 60f, 1f);
        //tfc.position.set(position);
        tfc.position.set(x, y, 1);
        tfc.rotation = 40f;
        tfc.scale.set(0.25f, 0.25f);
        e.add(tfc);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // set position of body into the world
        //bodyDef.position.set(new Vector2(6f, 1f));
        //bodyDef.position.set(10f, 23f);
        // Use the X and Y values from the Vector3. Make bodyDef and tfc use same position
        //bodyDef.position.set(position.x, position.y);
        bodyDef.position.set(x, y);

        BodyComponent bc = new BodyComponent();
        // create body from bodyDef and add to the world
        bc.body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(2f);
        //PolygonShape treeBox = new PolygonShape();
        //treeBox.setAsBox(5f, 1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 20f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // makes it bounce slightly
        bc.body.createFixture(fixtureDef);
        //bc.body.createFixture(treeBox, 0.0f);

        // Clean Up
        circle.dispose();
        //treeBox.dispose();

        // Add Body Component to Tree Entity
        e.add(bc);

        // This isn't used anymore?
        Vector2 screenMeters = RenderingSystem.getScreenSizeInMeters();
        Gdx.app.log("Game Screen Warmup", "Screen Meters: " + screenMeters.x + " x " + screenMeters.y);
        Vector2 screenPixels = RenderingSystem.getScreenSizeInPixels();
        Gdx.app.log("Game Screen Warmup", "Screen Pixels:" + screenPixels.x + " x " + screenPixels.y);

        engine.addEntity(e);
        //return e;
    }

    private void addTreeEntityAt2(World world, float x, float y) {
        Entity e = engine.createEntity(); //new Entity(); //or? engine.createEntity();

        TreeComponent tree = engine.createComponent(TreeComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        //TODO: add BodyComponent

        animation.animations.put(TreeComponent.STATE_NORMAL, Assets.getTreeNormalAnimation());
        animation.animations.put(TreeComponent.STATE_LIGHTS, Assets.getTreeLightsAnimation());
        //animation.animations.put(TreeComponent.STATE_THROWN, Assets.treeNormal);
        //animation.animations.put(TreeComponent.STATE_CAUGHT, Assets.treeLights);

        position.position.set(x, y, 3.0f);
        position.scale.set(0.25f, 0.25f);

        state.set(TreeComponent.STATE_NORMAL);

        e.add(tree);
        e.add(animation);
        e.add(state);
        e.add(texture);
        e.add(position);

        engine.addEntity(e);
    }


    private Array<Entity> getTreeLayer(World world) {
        Array<Entity> trees = new Array<>();

        Vector3 currPos = new Vector3(4f, 2.0f, 1f);
        trees.add(buildTreeEntityAt(world, new Vector3(10f, 2.2f, 1f)));
        trees.add(buildTreeEntityAt(world, new Vector3(20f, 2.2f, 1f)));
        trees.add(buildTreeEntityAt(world, new Vector3(30f, 2.2f, 1f)));

        /*
        while(currPos.x < 32f) {
            trees.add(buildTreeEntityAt(world, new Vector3(currPos.x, currPos.y, currPos.z)));
            currPos.x += 8f;
        }
        */

        return trees;
    }

    private void addTreeLayer(World world) {
        Vector3 currPos = new Vector3(10f, 2.0f, 1f);
        for(int x = 10; x < 40; x+= 10) {
            addTreeEntityAt(world, x, 5);
        }
    }

    private Entity buildBall(World world) {
        // TODO buildBall
        Entity e = engine.createEntity();
        e.add(new com.exovum.ld37warmup.components.BallComponent());

        AnimationComponent a = new AnimationComponent();
        a.animations.put("DEFAULT", new Animation(1f/16f, Assets.getBallArray(), Animation.PlayMode.LOOP));
        a.animations.put("MOVING", new Animation(1f/16f, Assets.getBallMoveArray(), Animation.PlayMode.LOOP));
        e.add(a);

        return e;
    }

}
