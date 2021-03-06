package com.obstacleavoid.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {
    private float x;
    private float y;
    private float width = 1;
    private float height = 1;

    private Circle bounds;

    public GameObjectBase(float boundsRadius) {
        bounds = new Circle(x, y, boundsRadius);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.x(bounds.x, bounds.y, 0.1f);
        renderer.circle(bounds.x, bounds.y, bounds.radius);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void updateBounds() {
        // Adding half width and height because we need to move the center of the bounds
        // object to the center of the texture. Things are out of alignment because we have a square
        // sprite which has a 0,0 of the lower left corner and a circle bounds which has a 0,0 of the
        // center of the circle.
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public Circle getBounds() {
        return bounds;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }
}
