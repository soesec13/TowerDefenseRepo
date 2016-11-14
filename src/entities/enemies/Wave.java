package entities.enemies;

import helper.Artist;
import helper.Checking;
import helper.Clock;
import map.TileGrid;
import org.jdom2.Element;
import state.ProgramStateManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by sebi on 004 04/11/2016.
 * <Wave>
 * <Enemy id=1 count=3 delay=2></Enemy>
 * <Enemy id=2 count=5 delay=1></Enemy>
 * </Wave>
 */
public class Wave {
    private List<Enemy> enemies;
    private Stack<Enemy> prepared;
    private Stack<Float> delay;
    private TileGrid map;
    private float currentDelay;

    private Wave(Stack<Enemy> prepared, Stack<Float> delay, TileGrid map) {
        enemies = new LinkedList<>();
        this.prepared = prepared;
        this.delay = delay;
        this.map = map;
    }

    public static Wave createWave(Element wave, TileGrid grid) {
        Stack<Enemy> prepared = new Stack<>();
        Stack<Float> delay = new Stack<>();
        List<Element> enemies = wave.getChildren("enemy");
        for (Element en : enemies) {
            EnemyType t = EnemyType.getById(en.getAttribute("id").getValue());
            //Enemy e = new Enemy(t, Artist.TILE_W,Artist.TILE_H,grid);
            float del = Float.parseFloat(en.getAttribute("delay").getValue());
            int count = Integer.parseInt(en.getAttribute("count").getValue());
            for (int i = 0; i < count; i++) {
                prepared.add(new Enemy(t, Artist.TILE_W, Artist.TILE_H, grid));
                delay.add(del);
            }
        }
        return new Wave(prepared, delay, grid);
    }

    @Deprecated
    public static Wave createTestWave(TileGrid grid) {
        Stack<Enemy> prepared = new Stack<>();
        Stack<Float> delay = new Stack<>();
        for (int i = 0; i < 10; i++) {
            Enemy e = new Enemy(EnemyType.RED, Artist.TILE_W, Artist.TILE_H, grid);
            prepared.add(e);
            delay.add(100.f * i);
        }
        return new Wave(prepared, delay, grid);
    }

    public boolean isBeaten() {
        return prepared.isEmpty() && enemies.isEmpty();
    }

    public void draw() {
        enemies.forEach(e -> e.draw());
    }

    public void update() {
        currentDelay -= Clock.delta();
        if (currentDelay <= 0 && !delay.isEmpty()) {
            enemies.add(prepared.pop());
            currentDelay = delay.pop();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (!e.isAlive()) {
                enemies.remove(i);
                i--;
            }
            if (e.hasReachedTarget()) {
                ProgramStateManager.getGame().getPlayer().damagePlayer(e);
                e.setAlive(false);
            }
            e.update();
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Enemy> getEnemiesInRange(float sx, float sy, float srange) {
        List<Enemy> inRange = new LinkedList<>();
        for (Enemy e : enemies) {
            if (Checking.inRange(sx, sy, e.getX(), e.getY(), e.getWidth(), e.getHeight(), srange)) {
                inRange.add(e);
            }
        }
        return inRange;
    }
}
