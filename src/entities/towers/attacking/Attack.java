package entities.towers.attacking;

import entities.enemies.Enemy;
import entities.enemies.WaveManager;
import entities.towers.Tower;

/**
 * Created by sebi on 007 07/11/2016.
 */
public abstract class Attack {
    protected WaveManager manager;
    protected Tower owner;
    protected float attackDelay, currentDelay;
    protected int damage;

    public Attack(WaveManager manager, float attackDelay, int damage) {
        this.manager = manager;
        this.attackDelay = attackDelay;
        this.damage = damage;
    }

    public void setOwner(Tower owner) {
        this.owner = owner;
    }

    public abstract void update();

    public abstract void draw();

    public abstract Enemy getTarget();

    private void fire() {
    }

    public enum Priority {
        LAST, FIRST, CLOSEST
    }
}
