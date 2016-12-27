package com.exovum.ld37warmup;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by exovu_000 on 12/11/2016.
 */

public interface ICollisionListener {
    void onBeginContact(Body a, Body b);
    void endContact(Body a, Body b);
}
