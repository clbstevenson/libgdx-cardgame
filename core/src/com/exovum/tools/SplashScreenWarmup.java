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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SplashScreenWarmup extends ScreenAdapter {

    OrthographicCamera cam;
    SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private IScreenDispatcher dispatcher;

    public SplashScreenWarmup(SpriteBatch batch, IScreenDispatcher dispatcher) { //IScreenDispatcher dispatcher){
        this.batch = batch;
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0f);
        this.dispatcher = dispatcher;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(Assets.am.update()){
            Gdx.app.log("Splash Screen Warmup", "Assets are Loaded!");
            //Gdx.app.log("Splash Screen", "Assets.am.getProgress() = " + Assets.am.getProgress());
            dispatcher.endCurrentScreen();
        }else {

            cam.update();
            batch.setProjectionMatrix(cam.combined);
            batch.enableBlending();
            batch.begin();
            batch.draw(Assets.splashScreen, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();

            Gdx.gl20.glLineWidth(1f);
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(0, 0, cam.viewportWidth * Assets.am.getProgress(), cam.viewportHeight / 5f);
            //shapeRenderer.rect(0, 0, Assets.am.getProgress(), cam.viewportHeight / 5f);
            //Gdx.app.log("Splash Screen", "Assets.am.getProgress() = " + Assets.am.getProgress());
            //Gdx.app.log("Splash Screen", "cam.viewportWidth * Assets.am.getProgress() = " + cam.viewportWidth * Assets.am.getProgress());
            //shapeRenderer.rect(0, 0, cam.viewportWidth / 5f, cam.viewportHeight / 5f);
            shapeRenderer.end();
        }
    }

    @Override
    public void show() {
        Gdx.app.log("Splash Screen", "Switched to SplashScreen");
    }

}

