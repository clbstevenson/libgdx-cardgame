package com.exovum.tools;

import com.badlogic.gdx.Screen;

public interface IScreenDispatcher {

    void endCurrentScreen();
    Screen getNextScreen();
}
