package entities;

import java.awt.*;

/**
 * Created by sebi on 004 04/11/2016.
 */
public abstract class Entity {
    protected float x = 0, y = 0;
    protected int width, height;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }

    public abstract void draw();

    public abstract void update();
}
