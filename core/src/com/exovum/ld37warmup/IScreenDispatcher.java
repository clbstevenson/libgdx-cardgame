package com.exovum.ld37warmup;

import com.badlogic.gdx.Screen;

public interface IScreenDispatcher {

    void endCurrentScreen();
    Screen getNextScreen();
}
