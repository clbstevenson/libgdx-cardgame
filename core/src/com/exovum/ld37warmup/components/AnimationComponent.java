package com.exovum.ld37warmup.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.ArrayMap;

public class AnimationComponent implements Component {
    public ArrayMap<String, Animation> animations = new ArrayMap<String, Animation>();

}
