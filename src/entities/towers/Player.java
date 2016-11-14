package entities.towers;

import entities.enemies.Enemy;
import entities.enemies.WaveManager;
import helper.Artist;
import helper.Clock;
import map.TileGrid;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import state.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebi on 004 04/11/2016.
 */
public class Player {
    private static Purse purse;
    private List<Tower> towers;
    private Tower selectedTower;
    private int selectedGridX, selectedGridY;
    private TowerFactory factory;
    private TileGrid grid;
    private int tickCount = 0;//To Prevent swapping errors
    private int initialHealth, currentHealth;

    public Player(TileGrid grid, WaveManager manager) {
        towers = new ArrayList<>();
        this.grid = grid;
        this.factory = new TowerFactory(manager);
        initialHealth = 100;
        currentHealth = initialHealth;
        purse = new Purse(50);
    }

    public static Purse getPurse() {
        return purse;
    }

    public void update() {
        if (this.currentHealth < 0) {
            GameState.setCurrentState(GameState.LOST);
        } else
            updateRunning();

    }

    public void draw() {
        int gridX = Mouse.getX() / Artist.TILE_W;
        int gridY = (Artist.SCREEN_HEIGHT - Mouse.getY()) / Artist.TILE_H;
        towers.forEach(Tower::draw);
        if (Modes.getCurrent() == Modes.PLACING && selectedTower != null && grid.isBuildable(gridX, gridY)) {
            selectedTower.drawAt(Mouse.getX() / Artist.TILE_W, (Artist.SCREEN_HEIGHT - Mouse.getY()) / Artist.TILE_H);
        }
    }

    private void updateRunning() {
        if (tickCount < 8) {
            tickCount++;
            while (Mouse.next())
                continue;
            while (Keyboard.next())
                continue;
            return;
        }
        towers.forEach(tower -> tower.update());
        if (Clock.isPaused())
            return;
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 0) { // Pressed
                    selectedGridX = Mouse.getX() / Artist.TILE_W;
                    selectedGridY = (Artist.SCREEN_HEIGHT - Mouse.getY()) / Artist.TILE_H;

                }
            } else {
                if (Mouse.getEventButton() == 0) { // Released
                    handleMouse();
                }
            }
        }
        while (Keyboard.next()) {
            handleKeyboardInput();
        }
    }

    private void handleKeyboardInput() {
        int key = Keyboard.getEventKey();
        switch (key) {
            case Keyboard.KEY_1:
                selectedTower = factory.createBasicTower(-1, -1);
                Modes.setCurrent(Modes.PLACING);
                break;
            case Keyboard.KEY_LEFT:
                Clock.setMultiplier(Clock.getMultiplier() - 1);
                break;
            case Keyboard.KEY_RIGHT:
                Clock.setMultiplier(Clock.getMultiplier() + 1);
                break;
            case Keyboard.KEY_DOWN:
                Clock.setMultiplier(1);
                break;
            case Keyboard.KEY_P:
                if (Keyboard.getEventKeyState()) {
                } else {
                    Clock.setPaused(true);
                    GameState.setCurrentState(GameState.PAUSED);
                }
                break;
        }
    }

    private void handleMouse() {
        if (Modes.getCurrent() == Modes.PLACING) {
            if (tryAddTower()) {
                //Modes.setCurrent(Modes.DEFAULT);
            }
        }
    }

    public void setSelectedTower(TowerFactory.Towers type) {
        Modes.setCurrent(Modes.PLACING);
        selectedTower = factory.createTower(-1, -1, type);
    }

    private boolean tryAddTower() {
        int gridX = Mouse.getX() / Artist.TILE_W;
        int gridY = (Artist.SCREEN_HEIGHT - Mouse.getY()) / Artist.TILE_H;
        //Moved Mouse away
        if (selectedTower == null)
            return false;
        if (gridX != selectedGridX || gridY != selectedGridY)
            return false;
        if (hasTowerInLocation(gridX, gridY))
            return false;
        if (!grid.isBuildable(gridX, gridY))
            return false;
        if (!purse.canSubtract(selectedTower.getCost())) {
            selectedTower = null;
            return false;
        }
        towers.add(selectedTower);
        purse.subtract(selectedTower.getCost());
        selectedTower.setX(gridX);
        selectedTower.setY(gridY);
        selectedTower = null;
        return true;
    }

    public void damagePlayer(Enemy e) {
        currentHealth -= e.damage();
    }

    private boolean hasTowerInLocation(float gridX, float gridY) {
        return towers.stream().anyMatch(t -> t.getX() == gridX && t.getY() == gridY);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    private enum Modes {
        PLACING, DEFAULT;
        private static Modes current = DEFAULT;

        public static Modes getCurrent() {
            return current;
        }

        public static void setCurrent(Modes current) {
            Modes.current = current;
        }
    }

    public class Purse {
        private int current;

        public Purse(int current) {
            this.current = current;
        }

        public boolean canSubtract(int val) {
            return current - val >= 0;
        }

        public void subtract(int val) {
            current -= val;
        }

        public void add(int val) {
            current += val;
        }

        public int getCurrent() {
            return current;
        }
    }
}
