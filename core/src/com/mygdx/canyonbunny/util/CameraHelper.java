package com.mygdx.canyonbunny.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();

    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 10.0f;

    private Vector2 position;
    private float zoom;
    private Sprite target;

    public CameraHelper(){
        position = new Vector2();
        zoom = 1.0f;
    }

    public void update(float deltaTime){

        if (!hasTarget()) return;

        position.x = target.getX() + target.getOriginX();
        position.y = target.getY() + target.getOriginY();
    }

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

    public Sprite getTarget() {
        return target;
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }

    public boolean hasTarget(){
        // if there is a target object it will not be null, thus it will return true
        return target != null;
    }

    public boolean hasTarget(Sprite target){
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