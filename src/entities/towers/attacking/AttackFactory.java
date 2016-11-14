package entities.towers.attacking;

import entities.enemies.WaveManager;
import helper.Artist;

/**
 * Created by sebi on 008 08/11/2016.
 */
public class AttackFactory {
    private WaveManager manager;

    public AttackFactory(WaveManager manager) {
        this.manager = manager;
    }

    public Attack createBulletAttack() {
        Attack a = new BulletAttack(manager, 3, 28, Artist.TILE_W * 5);
        return a;

    }
}
