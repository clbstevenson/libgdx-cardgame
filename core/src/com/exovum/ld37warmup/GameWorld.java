package com.exovum.ld37warmup;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.exovum.ld37warmup.components.AnimationComponent;
import com.exovum.ld37warmup.components.StateComponent;
import com.exovum.ld37warmup.components.TextureComponent;
import com.exovum.ld37warmup.components.TransformComponent;
import com.exovum.ld37warmup.components.TreeComponent;

/**
 * Created by exovu_000 on 12/7/2016.
 */

public class GameWorld {

    enum State {
        RUNNING, PAUSED, GAMEOVER
    }
    State state;

    public static final float WORLD_WIDTH = 40f; // TODO: To Be Determined. Check with RenderingSystem
    public static final float WORLD_HEIGHT = 30f; // TODO: TBD. Check with RenderingSystem

    PooledEngine engine;

    public GameWorld (PooledEngine engine) {
        this.engine = engine;
    }

    public void create() {
        generateLevel();

        this.state = State.RUNNING;
    }

    private void generateLevel() {
        float x = TreeComponent.WIDTH;
        float y = 5f;
        while(x < WORLD_WIDTH / 2) {
            createTreeEntity(x, y);
            x += TreeComponent.WIDTH * 4f;
        }
    }

    private void createTreeEntity(float x, float y) {
        Entity e = new Entity(); // OR engine.creatEntity(); not sure which is better

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

        // TODO use TreeComponent.width and TreeComponent.height for Bounds
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
}
