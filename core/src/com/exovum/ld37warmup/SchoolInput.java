package com.exovum.ld37warmup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by exovu_000 on 12/10/2016.
 */
public class SchoolInput extends InputAdapter {

    private final OrthographicCamera camera;

    private Vector3 touch;

    private SchoolWorld world;

    public SchoolInput(OrthographicCamera camera, SchoolWorld world) {
        this.camera = camera;
        this.world = world;
        touch = new Vector3();
    }

    @Override
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.ESCAPE) {
            // exit please
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        camera.unproject(touch.set(x, y, 0));

        if(button == Input.Buttons.LEFT) {
            //Vector2 direction = new Vector2(touch.x, touch.y);
            //world.throwBook(direction);
            //world.throwBook(touch.x, touch.y);
            world.throwBook(touch.x, touch.y);
            return true;
        }
        //if(button == Input.Buttons.RIGHT) {
            //world.create();
        //}

        return false;
    }

}
