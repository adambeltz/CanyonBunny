/// 158?

package com.mygdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.canyonbunny.game.Assets;
import com.mygdx.canyonbunny.game.WorldController;
import com.mygdx.canyonbunny.game.WorldRenderer;

public class CanyonBunnyMain extends ApplicationAdapter {
	private static final String TAG = CanyonBunnyMain.class.getName();
	private WorldController worldController;
	private WorldRenderer worldRenderer;


	// For Android
	private boolean paused;
	
	@Override
	public void create () {

		// Set LibGDX log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Load assets
		Assets.instance.init(new AssetManager());

		// Initialize Controller and Renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);





	}

	@Override
	public void render () {

		// Do not update the game world if paused
		if (!paused) {
			// Update game world by the time that has passed since last rendered time
			worldController.update(Gdx.graphics.getDeltaTime());
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render game world to screen
		worldRenderer.render();


	}
	
	@Override
	public void dispose () {
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}
}
