package ui;

import org.newdawn.slick.TrueTypeFont;

/**
 * Created by sebi on 011 11/11/2016.
 */
public class Text implements UiElement {
    private float x, y;
    private String text;
    private TrueTypeFont font;

    public Text(float x, float y, String text, TrueTypeFont font) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
    }

    public void draw() {
        font.drawString(x, y, text);
    }

    public void changeContent(String s) {
        this.text = s;
    }

    @Override
    public float getWidth() {
        return font.getWidth(text);
    }

    @Override
    public void setWidth(float width) {
    }

    @Override
    public float getHeight() {
        return font.getLineHeight();
    }

    @Override
    public void setHeight(float height) {
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
}
