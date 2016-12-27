package com.exovum.testgame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

/**
 * Created by barry on 12/8/15 @ 10:22 PM.
 */
public class ZComparator implements Comparator<Entity> {
    private ComponentMapper<com.exovum.testgame.components.TransformComponent> transformM;

    public ZComparator(){
        transformM = ComponentMapper.getFor(com.exovum.testgame.components.TransformComponent.class);
    }

    @Override
    public int compare(Entity entityA, Entity entityB) {
        return (int) Math.signum(transformM.get(entityB).position.z -
                transformM.get(entityA).position.z);
    }
}
