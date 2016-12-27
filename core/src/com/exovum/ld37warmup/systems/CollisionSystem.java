package com.exovum.ld37warmup.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.exovum.ld37warmup.EntityData;
import com.exovum.ld37warmup.SchoolWorld;
import com.exovum.ld37warmup.components.BodyComponent;
import com.exovum.ld37warmup.components.BookComponent;
import com.exovum.ld37warmup.components.BoundsComponent;
import com.exovum.ld37warmup.components.ChildComponent;
import com.exovum.ld37warmup.components.SchoolComponent;
import com.exovum.ld37warmup.components.StateComponent;
import com.exovum.ld37warmup.components.TransformComponent;

import java.util.Random;

/**
 * Created by exovu_000 on 12/11/2016.
 */

// Processes and examines collisions between Children, Books, and possibly schools
public class CollisionSystem extends EntitySystem implements ContactListener {

    private ComponentMapper<TransformComponent> posM;
    private ComponentMapper<StateComponent> stateM;
    // like bounds and movement
    private ComponentMapper<BodyComponent> bodyM;

    public static interface CollisionListener {

    }

    private Engine engine;
    private SchoolWorld gameWorld;
    private World world;
    //private CollisionListener listener;
    private Random random;

    private ImmutableArray<Entity> books;
    private ImmutableArray<Entity> children;
    private ImmutableArray<Entity> school;
    private ImmutableArray<Entity> bounds;

    private Array<Entity> removeEntities;
    private Array<Entity> removeBodyPhysics;

    public CollisionSystem(SchoolWorld gameWorld, World world) {
        this.gameWorld = gameWorld;
        this.world = world;
        //this.listener = listener;

        random = new Random();

        bodyM = ComponentMapper.getFor(BodyComponent.class);
        posM = ComponentMapper.getFor(TransformComponent.class);
        stateM = ComponentMapper.getFor(StateComponent.class);

        removeEntities = new Array<>();
        removeBodyPhysics = new Array<>();
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        books = engine.getEntitiesFor(Family.all(BookComponent.class, BodyComponent.class,
                TransformComponent.class, StateComponent.class).get());
        children = engine.getEntitiesFor(Family.all(ChildComponent.class, BodyComponent.class,
                TransformComponent.class, StateComponent.class).get());
        school = engine.getEntitiesFor(Family.all(SchoolComponent.class, BodyComponent.class,
                TransformComponent.class, StateComponent.class).get());
        bounds = engine.getEntitiesFor(Family.all(BoundsComponent.class, BodyComponent.class,
                TransformComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {


        for(int i = 0; i < books.size(); ++i) {
            Entity book = books.get(i);
            StateComponent bookState = stateM.get(book);
            BodyComponent bookBody = bodyM.get(book);

            // If the book has a negative y-velocity [moving down], then check for collisions?
            if(bookBody.body.getLinearVelocity().y < 0) {
                TransformComponent bookPos = posM.get(book);

                for(int j = 0; j < children.size(); ++j) {
                    Entity child = children.get(j);
                    TransformComponent childPos = posM.get(child);


                }
            }
        }

        // Remove everything from the entity and then kill the entity too
        for(Entity e: removeEntities) {
            BodyComponent deadBody = bodyM.get(e);
            if(deadBody != null) {
                engine.removeEntity(e);
                world.destroyBody(deadBody.body);
                deadBody.body.setUserData(null);
                deadBody.body = null;
            }
        }
        removeEntities.clear();

        for(Entity f: removeBodyPhysics) {
            BodyComponent deadBody = bodyM.get(f);
            if(deadBody != null) {
                world.destroyBody(deadBody.body);
                deadBody.body.setUserData(null);
                deadBody.body = null;
            }
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        Body bodyA = a.getBody();
        Body bodyB = b.getBody();
        EntityData dataA = ((EntityData)bodyA.getUserData());
        EntityData dataB = ((EntityData)bodyB.getUserData());
        Entity entityA = ((Entity)dataA.getFirst());
        Entity entityB = ((Entity)dataB.getFirst());

        // TODO check if == comparison or .equals comparision should be used
        // Currently set to: true == comparison
        if(books.contains(entityA, true) && children.contains(entityB, true)) {
            // EntityA is BOOK, EntityB is CHILD
            StateComponent bookState = stateM.get(entityA);
            StateComponent childState = stateM.get(entityB);
            if(childState.get().equals(ChildComponent.STATE_RUNNING)) {
                BodyComponent bookBody = bodyM.get(entityA);
                BodyComponent childBody = bodyM.get(entityB);
                TransformComponent bookPos = posM.get(entityA);
                TransformComponent childPos= posM.get(entityB);

                bookState.set(BookComponent.STATE_CAUGHT);
                childState.set(ChildComponent.STATE_READING);

                // Store the BookComponent data as the UserData in the child
                dataB.addData(dataA.getFirst());
                //childBody.body.setUserData(bookBody.body.getUserData());

                bookBody.body.setLinearVelocity(0f, 0f);
                childBody.body.setLinearVelocity(0f, 0f);

                bookBody.body.setLinearVelocity(0f, 0f);
                childBody.body.setLinearVelocity(0f, 0f);
                // Calculate velocity values to go from current position back to the school
                Vector2 direction = gameWorld.getVelocityTowardsSchool(5, childPos.position.x, childPos.position.y);
                // update the linear velocities of BOTH child and book so they *should* move together
                childBody.body.setLinearVelocity(direction);
                bookBody.body.setLinearVelocity(direction);
                bookBody.body.setAngularVelocity(0f);

                Filter childFilter = new Filter();
                childFilter.categoryBits = SchoolWorld.ENT_FRIENDLY_CHILD;
                childFilter.maskBits = SchoolWorld.ENT_SCHOOL;
                childBody.body.getFixtureList().first().setFilterData(childFilter);

                Filter bookFilter = new Filter();
                bookFilter.categoryBits = SchoolWorld.ENT_FRIENDLY_BOOK;
                bookFilter.maskBits = 0;
                bookBody.body.getFixtureList().first().setFilterData(bookFilter);

                // remove the book entity so it doesn't collide with more stuff
                //bookBody.body.setActive(false);
                //engine.removeEntity(entityA);
                //entityA.remove(BodyComponent.class);
                //world.destroyBody(bookBody.body);
                //bookBody.flaggedForDelete = true;
                // Add the Book Entity so it can be removed later
                removeEntities.add(entityA);

                // Play collision sound
                //gameWorld.playSoundBookChild();
                gameWorld.processBookChildHit(childBody.body.getUserData());

            }

        } else if(books.contains(entityB, true) && children.contains(entityA, true)) {
            // EntityB is BOOK, EntityA is CHILD
            StateComponent bookState = stateM.get(entityB);
            StateComponent childState = stateM.get(entityA);
            BodyComponent bookBody = bodyM.get(entityB);
            BodyComponent childBody = bodyM.get(entityA);
            TransformComponent bookPos = posM.get(entityB);
            TransformComponent childPos = posM.get(entityA);

            if (childState.get().equals(ChildComponent.STATE_RUNNING)) {
                bookState.set(BookComponent.STATE_CAUGHT);
                childState.set(ChildComponent.STATE_READING);

                // Store the BookComponent data as the UserData in the child
                dataA.addData(dataB.getFirst());
                //childBody.body.setUserData(bookBody.body.getUserData());

                bookBody.body.setLinearVelocity(0f, 0f);
                childBody.body.setLinearVelocity(0f, 0f);
                // Calculate velocity values to go from current position back to the school
                Vector2 direction = gameWorld.getVelocityTowardsSchool(5, childPos.position.x, childPos.position.y);
                // update the linear velocities of BOTH child and book so they *should* move together
                childBody.body.setLinearVelocity(direction);
                bookBody.body.setLinearVelocity(direction);
                bookBody.body.setAngularVelocity(0f);

                Filter childFilter = new Filter();
                childFilter.categoryBits = SchoolWorld.ENT_FRIENDLY_CHILD;
                childFilter.maskBits = SchoolWorld.ENT_SCHOOL;
                childBody.body.getFixtureList().first().setFilterData(childFilter);

                Filter bookFilter = new Filter();
                bookFilter.categoryBits = SchoolWorld.ENT_FRIENDLY_BOOK;
                bookFilter.maskBits = 0;
                bookBody.body.getFixtureList().first().setFilterData(bookFilter);

                // remove the book entity so it doesn't collide with more stuff
                //bookBody.body.setActive(false);
                //engine.removeEntity(entityB);
                // Add the Book Entity so it can be removed later
                removeEntities.add(entityB);

                gameWorld.processBookChildHit(childBody.body.getUserData());

            }
        }
        // If the children collide with the school, remove them? or pause them, then move after a timer or something idk
        else if(school.contains(entityA, true) && children.contains(entityB, true)) {
            //removeEntities.add(entityB);
            StateComponent childState = stateM.get(entityB);
            BodyComponent childBody = bodyM.get(entityB);
            TransformComponent childPos = posM.get(entityB);
            TransformComponent schoolPos = posM.get(entityA);

            if(childState.get().equals(ChildComponent.STATE_READING)) {
                childState.set(ChildComponent.STATE_RELEASED);

                // update the text to the random quote stored from the book that hit the child
                //gameWorld.updateTextField(childBody.body.getUserData());
                //gameWorld.updateTextField(dataB);

                if(schoolPos.position.x > childPos.position.x) {
                    childBody.body.setLinearVelocity(-15f, 0f);
                } else {
                    childBody.body.setLinearVelocity(15f, 0f);
                }

                gameWorld.processChildSchoolHit(childBody.body.getUserData());
                // Remove the Physics body from the child, but keep the rest of the components
                //removeBodyPhysics.add(entityB);
            }
        } else if(school.contains(entityB, true) && children.contains(entityA, true)) {
            //removeEntities.add(entityA);
            StateComponent childState = stateM.get(entityA);
            BodyComponent childBody = bodyM.get(entityA);
            TransformComponent childPos = posM.get(entityA);
            TransformComponent schoolPos = posM.get(entityB);

            if (childState.get().equals(ChildComponent.STATE_READING)) {
                childState.set(ChildComponent.STATE_RELEASED);

                // update the text to the random quote stored from the book that hit the child
                //gameWorld.updateTextField(childBody.body.getUserData());
                //gameWorld.updateTextField(dataA);

                if (schoolPos.position.x > childPos.position.x) {
                    childBody.body.setLinearVelocity(-15f, 0f);
                } else {
                    childBody.body.setLinearVelocity(15f, 0f);
                }
                gameWorld.processChildSchoolHit(childBody.body.getUserData());
                // Remove the Physics body from the child, but keep the rest of the components
                //removeBodyPhysics.add(entityA);
            }
        } else if(bounds.contains(entityA, true) && (children.contains(entityB, true) || books.contains(entityB, true))) {
            Gdx.app.log("Collision System", "1 Removing child/book after collision with a boundary.");
            gameWorld.processChildBoundaryHit(entityB.getComponent(BodyComponent.class).body.getUserData());
            removeEntities.add(entityB);
        } else if(bounds.contains(entityB, true) && (children.contains(entityA, true) || books.contains(entityA, true))) {
            Gdx.app.log("Collision System", "2 Removing child/book after collision with a boundary.");
            gameWorld.processChildBoundaryHit(entityB.getComponent(BodyComponent.class).body.getUserData());
            removeEntities.add(entityA);
        } else {
            // do nothing
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
