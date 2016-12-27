package com.exovum.ld37warmup;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.exovum.ld37warmup.systems.CollisionSystem;

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
