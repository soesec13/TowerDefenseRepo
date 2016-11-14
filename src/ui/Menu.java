package ui;

import helper.Artist;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class Menu {
    private final Map<UiElement, Consumer<UiElement>> elementMap;
    private float x = 0, y = 0, w = 0, h = 0;
    private Optional<Texture> background;

    public Menu() {
        elementMap = new Hashtable<>();
        background = Optional.empty();
    }

    public Menu(float x, float y) {
        this.x = x;
        this.y = y;
        elementMap = new Hashtable<>();
        background = Optional.empty();
    }

    public void setBackground(Texture t, float w, float h) {
        this.w = w;
        this.h = h;
        background = Optional.of(t);
    }

    public void draw() {
        background.ifPresent(t -> Artist.drawTexture(t, x, y, w, h));
        elementMap.keySet().forEach(UiElement::draw);
    }

    public void alignAsGrid(int row, int col) {
        List<UiElement> elements = elementMap.keySet().stream().collect(Collectors.toList());
        int count = 0;
        float previousButtonWidth;
        float currentYPosition = y;
        float minimumYPosAddition = 0;

        for (int i = 0; i < row; i++) {
            previousButtonWidth = 0;
            for (int j = 0; j < col; j++) {
                if (count == elementMap.size())
                    return;
                UiElement elem = elements.get(count);
                elem.setX(x + previousButtonWidth);
                elem.setY(currentYPosition);
                previousButtonWidth = elem.getWidth();
                if (minimumYPosAddition < elem.getHeight())
                    minimumYPosAddition += elem.getHeight();
                count++;
            }
            currentYPosition = +minimumYPosAddition;
            minimumYPosAddition = 0;
        }
    }

    public void add(UiElement b, Consumer<UiElement> method) {
        b.setX(b.getX() + x);
        b.setY(b.getY() + y);
        elementMap.put(b, method);
    }

    public void update() {
        if (!Mouse.isButtonDown(0))
            return;
        int x = Mouse.getX();
        int y = Artist.SCREEN_HEIGHT - Mouse.getY();
        for (UiElement o : elementMap.keySet()) {
            if (o instanceof Button) {
                Button b = (Button) o;
                if (b.isClicked(x, y)) {
                    elementMap.get(b).accept(b);
                    return;
                }
            }

        }
    }
}
