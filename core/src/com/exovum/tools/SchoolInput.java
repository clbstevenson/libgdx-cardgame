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

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by exovu_000 on 12/10/2016.
 */
public class SchoolInput extends InputAdapter {

    private final OrthographicCamera camera;

    private Vector3 touch;

    private SchoolWorld world;

    public SchoolInput(OrthographicCamera camera, SchoolWorld world) {
        this.camera = camera;
        this.world = world;
        touch = new Vector3();
    }

    @Override
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.ESCAPE) {
            // exit please
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        camera.unproject(touch.set(x, y, 0));

        if(button == Input.Buttons.LEFT) {
            //Vector2 direction = new Vector2(touch.x, touch.y);
            //world.throwBook(direction);
            //world.throwBook(touch.x, touch.y);
            world.throwBook(touch.x, touch.y);
            return true;
        }
        //if(button == Input.Buttons.RIGHT) {
            //world.create();
        //}

        return false;
    }

}
