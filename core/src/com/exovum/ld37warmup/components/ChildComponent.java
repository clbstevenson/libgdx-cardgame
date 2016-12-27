package com.exovum.ld37warmup.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by exovu_000 on 12/11/2016.
 */
public class ChildComponent implements Component {
    public static final String STATE_RUNNING = "FREE"; //"TREE_NORMAL";
    public static final String STATE_READING = "READING"; //"TREE_LIGHTS";
    public static final String STATE_RELEASED = "RELEASED";
    public static final float WIDTH = 4f;
    public static final float HEIGHT = 4f;
    public static final float RADIUS = 4f;
}
