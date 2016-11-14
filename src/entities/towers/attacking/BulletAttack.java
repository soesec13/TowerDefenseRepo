package entities.towers.attacking;

import entities.enemies.Enemy;
import entities.enemies.Wave;
import entities.enemies.WaveManager;
import helper.Artist;
import helper.Checking;
import helper.Clock;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sebi on 008 08/11/2016.
 */
public class BulletAttack extends Attack implements IRangeAttack {
    private List<Projectile> shots = new ArrayList<>();
    private float range;

    @Deprecated
    public BulletAttack(WaveManager manager) {
        super(manager, 0.001f, 10);
    }

    public BulletAttack(WaveManager manager, float delay, int damage, float range) {
        super(manager, delay, damage);
        this.range = range;
    }

    public boolean fire() {
        if (manager.getCurrentWave().getEnemies().isEmpty())
            return false;
        shots.add(makeNewProjectile());
        return true;
    }

    @Override
    public void update() {
        this.currentDelay -= Clock.delta();
        if (currentDelay < 0) {
            if (fire())
                currentDelay = attackDelay;
        }
        Wave current = manager.getCurrentWave();
        boolean hasHit = false;
        Projectile p;
        Rectangle bounds;
        for (int i = 0; i < shots.size(); i++) {
            p = shots.get(i);
            bounds = p.getBounds();
            for (Enemy e : current.getEnemies()) {
                if (Checking.collision(bounds, e.getBounds())) {
                    e.damage(damage);
                    shots.remove(p);
                    i--;
                    break;
                }
            }
        }
        shots.forEach(b -> b.update());
        shots.removeIf(b -> !b.isInBounds(0, 0, Artist.SCREEN_WIDTH, Artist.SCREEN_HEIGHT));
    }

    @Override
    public void draw() {
        shots.forEach(b -> b.draw());
    }

    private Projectile makeNewProjectile() {
        int size = 16;
        Projectile p = new Projectile(Artist.loadTexture("basicbullet"),
                owner.getX() * Artist.TILE_W + owner.getWidth() / 2 - size / 2,
                owner.getY() * Artist.TILE_H + owner.getHeight() / 2 - size / 2,
                size, size,
                700,
                1, 1);
        p.aimAt(getTarget());
        return p;
    }

    @Override
    public Enemy getTarget() {
        try {
            switch (owner.getPriority()) {
                case FIRST:
                    return Collections.min(manager.getCurrentWave().getEnemiesInRange(owner.getX() * Artist.TILE_W + owner.getWidth() / 2, owner.getY() * Artist.TILE_H + owner.getHeight() / 2, getRange()),
                            (e1, e2) -> (int) (e2.getPathPosition() - e1.getPathPosition()));
            }
        } catch (Exception e) {
            return null;
        }
        return manager.getCurrentWave().getEnemies().get(0);
    }

    private Enemy auqireFirst() {
        float xCenterOwner = owner.getX() * Artist.TILE_W + owner.getWidth() / 2;
        float yCenterOwner = owner.getY() * Artist.TILE_H + owner.getHeight() / 2;
        List<Enemy> inRange = manager.getCurrentWave().getEnemiesInRange(xCenterOwner, yCenterOwner, getRange());
        return Collections.min(inRange, (e1, e2) -> (int) (e2.getPathPosition() - e1.getPathPosition()));
    }

    @Override
    public float getRange() {
        return range;
    }
}
