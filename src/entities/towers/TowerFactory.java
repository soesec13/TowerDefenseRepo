package entities.towers;

import entities.enemies.WaveManager;
import entities.towers.attacking.AttackFactory;
import helper.Artist;

/**
 * Created by sebi on 008 08/11/2016.
 */
public class TowerFactory {
    private WaveManager manager;
    private AttackFactory attackFactory;

    public TowerFactory(WaveManager manager) {
        this.manager = manager;
        attackFactory = new AttackFactory(manager);
    }

    public Tower createBasicTower(int gridX, int gridY) {
        Tower t = new Tower(Artist.loadTexture("basicturrent"),
                gridX,
                gridY,
                Artist.TILE_W,
                Artist.TILE_H,
                10);
        t.setAttack(attackFactory.createBulletAttack());
        return t;
    }

    public Tower createTower(int gridX, int gridY, Towers t) {
        switch (t) {
            case BASIC:
                return createBasicTower(gridX, gridY);
            default:
                return createBasicTower(gridX, gridY);
        }
    }

    public Tower createTower(int gridX, int gridY, String name) {
        return createTower(gridX, gridY, Towers.getByName(name));
    }

    public enum Towers {
        BASIC("basicturrent");
        String name;

        Towers(String name) {
            this.name = name;
        }

        public static Towers getByName(String name) {
            for (Towers t : values()) {
                if (t.getName().equals(name))
                    return t;
            }
            return BASIC;
        }

        public String getName() {
            return name;
        }
    }
}
