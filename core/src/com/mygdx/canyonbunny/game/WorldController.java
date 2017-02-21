package com.mygdx.canyonbunny.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.canyonbunny.util.CameraHelper;


public class WorldController extends InputAdapter{

    public Sprite[] testSprites;
    public int selectedSprite;
    public CameraHelper cameraHelper;

    private static final String TAG = WorldController.class.getName();
    public WorldController(){
        init();

    }

    private void init(){
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        initTestObjects();
    }

    private void initTestObjects() {

        // Create new array for 5 sprites
        testSprites = new Sprite[5];

        // Create empty POT-sized Pixmap with 8 bit RBGA pixel data
        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);

        // create a new texture from pixmap data
        Texture texture = new Texture(pixmap);

        // Create new sprites using the just created pixmap
        for (int i = 0; i < testSprites.length; i++){
            Sprite spr = new Sprite(texture);

            // Define sprite size to be 1m x 1m in game world
            spr.setSize(1,1);

            // Set origin to sprite's center
            spr.setOrigin(spr.getWidth()/2.0f, spr.getHeight()/2.0f);

            // Calculate random position for sprite
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);

            // Put new sprites into array
            testSprites[i] = spr;
        }

        // Set first sprite as selected one
        selectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Fill square with red color at 50% opacity
        pixmap.setColor(1,0,0,0.5f);
        pixmap.fill();

        // Draw a yellow-colored X shape on square
        pixmap.setColor(1,1,0,1);
        pixmap.drawLine(0,0,width, height);
        pixmap.drawLine(width, 0, height, 0);

        // Draw a cyan-colored border around square
        pixmap.setColor(0,1,1,1);
        pixmap.drawRectangle(0,0,width,height);

        return pixmap;
    }


    public void update(float deltaTime){
        handleDebugInput(deltaTime);
        updateTestObjects(deltaTime);
        // is getting movement coords from cameraHelper if there is a target
        cameraHelper.update(deltaTime);
    }

    private void updateTestObjects(float deltaTime){
        // Get current rotation from selected sprite
        float rotation = testSprites[selectedSprite].getRotation();

        // Rotate sprite by 90 degrees per second
        rotation += 90* deltaTime;

        // Wrap around at 360 degrees
        rotation %= 360;

        // Set new rotation value to selected sprite
        testSprites[selectedSprite].setRotation(rotation);
    }

    private void handleDebugInput(float deltaTime){
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        // Selected sprite Controls
        float sprMoveSpeed = 5.0f * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveSelectedSprite(-sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveSelectedSprite(sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveSelectedSprite(0, sprMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveSelectedSprite(0, -sprMoveSpeed);

        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) cameraHelper.setPosition(0,0);

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) cameraHelper.addZoom(-camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) cameraHelper.setZoom(1);
    }

    private void moveCamera (float x, float y){
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x,y);
    }

    private void moveSelectedSprite(float x, float y){
        testSprites[selectedSprite].translate(x,y);
    }

    @Override
    public boolean keyUp(int keycode) {

        // Reset game world
        if (keycode == Input.Keys.R){
            init();
            Gdx.app.debug(TAG, "Game World Reset");
        }

        // Select next sprite
        else if (keycode == Input.Keys.SPACE){
            // Using modulo allows you to wrap around the array testSprites. 0-4 in this instance
            selectedSprite = (selectedSprite + 1) % testSprites.length;

            // Update camera's target to follow the current sprite
            if (cameraHelper.hasTarget()) {
                cameraHelper.setTarget(testSprites[selectedSprite]);
            }
            Gdx.app.debug(TAG, "Selected Sprite: " + selectedSprite);
        }

        else if (keycode == Input.Keys.ENTER){
            // Ternary Operator.  If there is a target, when enter is pressed it will now become null
            // If there is no target, when enter is pressed it will become the currently selected sprite.
            cameraHelper.setTarget(cameraHelper.hasTarget()? null: testSprites[selectedSprite]);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return true;
    }
}
