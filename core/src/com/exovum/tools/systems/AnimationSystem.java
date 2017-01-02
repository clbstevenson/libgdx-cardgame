/*******************************************************************************
 * Copyright 2016 See AUTHORS files
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.exovum.tools.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.exovum.tools.components.AnimationComponent;
import com.exovum.tools.components.StateComponent;
import com.exovum.tools.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    ComponentMapper<TextureComponent> tm;
    ComponentMapper<AnimationComponent> am;
    ComponentMapper<StateComponent> sm;

    public AnimationSystem(){
        super(Family.all(TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ani = am.get(entity);
        StateComponent state = sm.get(entity);

        if(ani.animations.containsKey(state.get())){
            //Gdx.app.log("Animation System", "Updating Animation/Texture frame");
            TextureComponent tex = tm.get(entity);
            tex.region = ani.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
        }

        state.time += deltaTime;
    }
}
