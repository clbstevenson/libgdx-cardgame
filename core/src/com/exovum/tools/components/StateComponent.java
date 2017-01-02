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

package com.exovum.tools.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
    private String state = "DEFAULT";
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(String newState){
        state = newState;
        time = 0.0f;
    }

    public String get(){
        return state;
    }
}