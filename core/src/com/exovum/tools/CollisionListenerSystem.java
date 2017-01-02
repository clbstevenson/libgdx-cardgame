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

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by exovu_000 on 12/11/2016.
 */

public class CollisionListenerSystem implements ContactListener {

    private final Array<ICollisionListener> collisionListeners;

    public CollisionListenerSystem(World world, Array<ICollisionListener> collisionListeners) {
        world.setContactListener(this);
        this.collisionListeners = collisionListeners;
    }

    @Override
    public void beginContact(Contact contact) {
        for(ICollisionListener cl: collisionListeners) {
            cl.onBeginContact(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {
        for(ICollisionListener cl: collisionListeners) {
            cl.endContact(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
