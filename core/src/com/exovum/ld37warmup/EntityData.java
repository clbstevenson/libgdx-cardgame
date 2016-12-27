package com.exovum.ld37warmup;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

/**
 * Created by exovu_000 on 12/12/2016.
 */

public class EntityData {
    Array<Entity> entities;

    public EntityData() {
        entities = new Array<>();
    }

    public EntityData(Entity first) {
        entities = new Array<>();
        entities.add(first);
    }

    public void addData(Entity data) {
        entities.add(data);
    }

    public Array<Entity> getData() {
        return entities;
    }

    public Entity getFirst() {
        return entities.first();
    }

    public Entity getData(int index) {
        return entities.get(index);
    }

}
