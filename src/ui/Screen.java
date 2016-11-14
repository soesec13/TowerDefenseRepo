package ui;

import state.ProgramState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebi on 002 02/11/2016.
 */
public abstract class Screen implements ProgramState {
    private List<Menu> menues;
    private int width, height;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        menues = new ArrayList<>();
    }

    public void addMenu(Menu m) {
        menues.add(m);
    }

    protected void updateMenues() {
        for (Menu m : menues) {
            m.update();
        }
    }

    protected void draw() {
        for (Menu m : menues) {
            m.draw();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
