package com.mygdx.canyonbunny.game;


import com.badlogic.gdx.graphics.g2d.Sprite;

public class WorldController {

    public Sprite[] testSprites;
    public int selectedSprite;

    private static final String TAG = WorldController.class.getName();
    public WorldController(){
        init();

    }

    private void init(){
        initTestObjects();
    }

    private void initTestObjects() {

        // Create new array for 5 sprites
        testSprites = new Sprite[5];

        // Create empty POT-sized Pixmap with 8 bit RBGA pixel data
    }

    public void update(float deltaTime){

    }
}
