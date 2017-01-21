package com.exovum.tools;

import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ScreenDispatcherWarmup implements IScreenDispatcher {

    public ArrayList<Screen> screens;
    private boolean isCurrenScreenEnded = false;
    private int currentIndex = 0;

    public ScreenDispatcherWarmup(){
        screens = new ArrayList<>();
    }

    public void AddScreen(Screen screen){
        screens.add(screen);
    }

    @Override
    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    @Override
    public void endCurrentScreen() {
        isCurrenScreenEnded = true;
    }

    @Override
    public Screen getCurrentScreen() {
        if(screens.size() <= currentIndex)
            return null;
        return screens.get(currentIndex);
    }

    @Override
    public Screen getNextScreen() {
        if(isCurrenScreenEnded){
            isCurrenScreenEnded = false;
            //Do logic to pick the next screen
            currentIndex++;

            if(currentIndex > screens.size()) {
                currentIndex = 0;
            }
        }

        if(screens.size() >= currentIndex){
            return screens.get(currentIndex);
        }else{
            return screens.get(0);
        }
    }
}
