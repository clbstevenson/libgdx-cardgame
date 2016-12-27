package com.exovum.ld37warmup.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent implements Component {
    // Box2D Body Component
    public Body body;

    public boolean flaggedForDelete = false;

}
