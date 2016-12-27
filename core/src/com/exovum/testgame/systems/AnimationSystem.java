package com.exovum.testgame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.exovum.testgame.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    ComponentMapper<TextureComponent> tm;
    ComponentMapper<com.exovum.testgame.components.AnimationComponent> am;
    ComponentMapper<com.exovum.testgame.components.StateComponent> sm;

    public AnimationSystem(){
        super(Family.all(TextureComponent.class,
                com.exovum.testgame.components.AnimationComponent.class,
                com.exovum.testgame.components.StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(com.exovum.testgame.components.AnimationComponent.class);
        sm = ComponentMapper.getFor(com.exovum.testgame.components.StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        com.exovum.testgame.components.AnimationComponent ani = am.get(entity);
        com.exovum.testgame.components.StateComponent state = sm.get(entity);

        if(ani.animations.containsKey(state.get())){
            TextureComponent tex = tm.get(entity);
            tex.region = ani.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
        }

        state.time += deltaTime;
    }
}
