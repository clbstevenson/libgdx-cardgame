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
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.exovum.tools.components.BookComponent;
import com.exovum.tools.components.SchoolComponent;
import com.exovum.tools.components.TextureComponent;
import com.exovum.tools.components.TransformComponent;

import java.util.Comparator;

public class ThrowingSystem extends SortedIteratingSystem {

    static final float PPM = 16.0f;
    static final float FRUSTUM_WIDTH = 40f;//Gdx.graphics.getWidth()/PPM;//37.5f;
    static final float FRUSTUM_HEIGHT = 30f;//Gdx.graphics.getHeight()/PPM;//.0f;

    public static final float PIXELS_TO_METRES = 1.0f / PPM;

    private static Vector2 meterDimensions = new Vector2();
    private static Vector2 pixelDimensions = new Vector2();
    public static Vector2 getScreenSizeInMeters(){
        meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METRES,
                Gdx.graphics.getHeight()*PIXELS_TO_METRES);
        return meterDimensions;
    }

    public static Vector2 getScreenSizeInPixesl(){
        pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return pixelDimensions;
    }

    public static float PixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METRES;
    }

    private Array<Entity> queue;
    private Comparator<Entity> comparator;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<BookComponent> bookM;
    private ComponentMapper<SchoolComponent> schoolM;

    public ThrowingSystem() {
        super(Family.all(TransformComponent.class, TextureComponent.class)
                    .one(BookComponent.class, SchoolComponent.class).get(), new ZComparator());

        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
        bookM = ComponentMapper.getFor(BookComponent.class);


        queue = new Array<>();

        // Add a comparator so the queue.sort doesn't crash *crosses fingers*

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformM.get(entityB).position.z -
                        transformM.get(entityA).position.z);
            }
        };

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        queue.sort(comparator);

        for (Entity entity : queue) {
            TextureComponent tex = textureM.get(entity);
            TransformComponent t = transformM.get(entity);

            if (tex.region == null || t.isHidden) {
                continue;
            }


            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width/2f;
            float originY = height/2f;

        }

        queue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        queue.add(entity);
    }

}
