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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.exovum.tools.components.ChildComponent;
import com.exovum.tools.components.StateComponent;

public class UselessStateSwapSystem extends IteratingSystem {

    private ComponentMapper<StateComponent> sm;

    private Array<Entity> stateQueue;

    public UselessStateSwapSystem(){
        //super(Family.all(StateComponent.class, PuffinComponent.class).get());
        super(Family.all(StateComponent.class, ChildComponent.class).get());
        //super(Family.all(StateComponent.class).get());
        sm = ComponentMapper.getFor(StateComponent.class);
        stateQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for(Entity entity:stateQueue) {
                StateComponent state = sm.get(entity);
                if (state.get() == "DEFAULT") {
                    state.set("RUNNING");
                } else {
                    state.set("DEFAULT");
                }
            }
        }
        stateQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        stateQueue.add(entity);
    }
}
