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

package com.exovum.tools;

import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ScreenDispatcherWarmup implements IScreenDispatcher {

    public ArrayList<Screen> screens;
    private boolean isCurrenScreenEnded = false;
    private int currentIndex = 0;

    ScreenDispatcherWarmup(){
        screens = new ArrayList<>();
    }

    public void AddScreen(Screen screen){
        screens.add(screen);
    }


    @Override
    public void endCurrentScreen() {
        isCurrenScreenEnded = true;
    }

    @Override
    public Screen getNextScreen() {
        if(isCurrenScreenEnded){
            isCurrenScreenEnded = false;
            //Do logic to pick the next screen
            currentIndex++;

            if(currentIndex > screens.size()) {
                currentIndex = 0;
            }
        }

        if(screens.size() > currentIndex){
            return screens.get(currentIndex);
        }else{
            return screens.get(0);
        }
    }
}
