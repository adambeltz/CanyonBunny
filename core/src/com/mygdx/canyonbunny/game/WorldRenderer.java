package com.mygdx.canyonbunny.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.canyonbunny.util.Constants;



public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    TextureAtlas textureAtlas;
    Sprite coin;

    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();

    }

    private void init(){
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas("assets/canyonbunny.txt");
        coin = textureAtlas.createSprite("item_gold_coin");
        coin.setPosition(0,0);
        coin.setSize(2,2);
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
    }

    public void render(){
        renderTestObjects();
    }

    private void renderTestObjects(){
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        coin.draw(batch);
        for (Sprite sprite : worldController.testSprites){
            sprite.draw(batch);
        }
        batch.end();
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height * width);
        camera.update();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
