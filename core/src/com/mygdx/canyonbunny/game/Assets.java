package com.mygdx.canyonbunny.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.canyonbunny.util.Constants;

public class Assets implements Disposable, AssetErrorListener{

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    // Singleton: prevent instantiation from other classes
    private Assets() {}

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;

        // Set asset manager error handler
        assetManager.setErrorListener(this);

        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);

        // Start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);

        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset :" + asset.fileName, (Exception)throwable);

    }

    //@Override
    public void error (String filename, Class type, Throwable throwable){
        Gdx.app.error(TAG, "Couldn't load asset '"
                + filename + "'", (Exception)throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}