package ui;

import helper.Artist;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class Button implements UiElement {
    private float x, y;
    private float width, height;
    private Texture texture;
    private String name;
    private Text text;

    public Button(float x, float y, float width, float height, Texture texture, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.name = name;
    }

    public Button(float x, float y, float width, float height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public Button(float x, float y, Texture texture, String name) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = texture.getImageWidth();
        this.height = texture.getImageHeight();
        this.name = name;
    }

    public Button(Texture texture, String name) {
        this.texture = texture;
        this.name = name;
        this.width = texture.getImageWidth();
        this.height = texture.getImageHeight();
    }

    private static boolean inBetween(float from, float to, float is) {
        return is > from && is < to;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void draw() {
        if (texture != null)
            Artist.drawTexture(texture, x, y, width, height);
        if (text != null)
            text.draw();
    }

    public String getName() {
        return name;
    }

    public boolean isClicked(float cx, float cy) {
        if (inBetween(x, x + width, cx)) {
            if (inBetween(y, y + height, cy)) {
                return true;
            }
        }
        return false;
    }

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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void alignSouth(Button next, float gap) {
        next.setX(this.x);
        next.setY(this.y + height + gap);
    }
}
