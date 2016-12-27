package com.exovum.ld37warmup.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
    private String state = "DEFAULT";
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(String newState){
        state = newState;
        time = 0.0f;
    }

    public String get(){
        return state;
    }
}