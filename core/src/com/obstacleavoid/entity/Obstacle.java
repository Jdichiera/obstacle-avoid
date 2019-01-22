package com.obstacleavoid.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Obstacle extends GameObjectBase{

    private static final float BOUNDS_RADIUS = 0.3f; // in world units
    private static final float SIZE = 2 * BOUNDS_RADIUS;

    private float ySpeed = 0.1f;

    public Obstacle() {
        super(BOUNDS_RADIUS);
    }

    public void update() {
        setY(getY() - ySpeed);
    }

    public float getWidth() {
        return SIZE;
    }
}
