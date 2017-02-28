package com.mygdx.canyonbunny.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.canyonbunny.game.objects.AbstractGameObject;


public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();

    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 10.0f;

    private Vector2 position;
    private float zoom;
    private AbstractGameObject target;

    public CameraHelper(){
        position = new Vector2();
        zoom = 1.0f;
    }

    public void update(float deltaTime){

        if (!hasTarget()) return;

        position.x = target.position.x + target.origin.x;
        position.y = target.position.y + target.origin.y;
    }


    // setPosition is called if there is no target.
    // if there is a target the coords are adjusted in cameraHelper.update
    public void setPosition(float x, float y){
        this.position.set(x,y);
    }

    public Vector2 getPosition(){
        return position;
    }

    public void addZoom(float amount){
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom){
    this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    public AbstractGameObject getTarget() {
        return target;
    }

    public void setTarget(AbstractGameObject target) {
        this.target = target;
    }

    public boolean hasTarget(){
        // if there is a target object it will not be null, thus it will return true
        // the target can be set to null so make the camera stop following
        return target != null;
    }

    public boolean hasTarget(AbstractGameObject target){
        // if there is a target, but it does not equal the query target, it will return false
        return hasTarget() && this.target.equals(target);
    }

    public void applyTo (OrthographicCamera camera){
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }


}
