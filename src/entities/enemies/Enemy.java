package entities.enemies;

import entities.Entity;
import entities.towers.Player;
import helper.Artist;
import helper.Clock;
import map.Tile;
import map.TileGrid;
import org.newdawn.slick.opengl.Texture;

import java.awt.*;

/**
 * Created by sebi on 004 04/11/2016.
 */
public class Enemy extends Entity {
    private static final Texture hBoarder = Artist.loadTexture("hborder");
    private static final Texture hBackground = Artist.loadTexture("hbackground");
    private static final Texture hForeground = Artist.loadTexture("hforeground");
    private EnemyType t;
    private int dirX, dirY;
    private boolean alive;
    private float cHealth;
    private Tile currentTile;
    private Tile nextTile;
    private TileGrid grid;

    public Enemy(EnemyType t, int width, int height, TileGrid grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.t = t;
        currentTile = grid.getStartTile();
        nextTile = grid.getEnemiesPath().getNext(currentTile);
        this.x = currentTile.getMapX();
        this.y = currentTile.getMapY();
        alive = true;
        cHealth = t.getHealth();
        adjustDirection();
    }

    public void damage(float amount) {
        cHealth -= amount;
        if (cHealth < 0)
            die();
    }

    public void die() {
        alive = false;
        Player.getPurse().add(this.t.getReward());
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    @Override
    public void draw() {
        Artist.drawTexture(t.getTex(), x, y, width, height);
        Artist.drawTexture(hBackground, x - 7, y - 15, Artist.TILE_W + 14, 10);
        if (cHealth >= 0)
            Artist.drawTexture(hForeground, x - 7, y - 15, (Artist.TILE_W + 14) * (cHealth / t.getHealth()), 10);
        Artist.drawTexture(hBoarder, x - 7, y - 15, Artist.TILE_W + 14, 10);
    }

    @Override
    public void update() {
        if (hasReachedNext()) {
            adjustToNext();
        }

        x += dirX * t.getSpeed() * Clock.delta();
        y += dirY * t.getSpeed() * Clock.delta();
    }

    private void adjustToNext() {
        this.x = nextTile.getMapX();
        this.y = nextTile.getMapY();
        currentTile = nextTile;
        nextTile = grid.getEnemiesPath().getNext(currentTile);
        adjustDirection();
    }

    private void adjustDirection() {
        Point dir = TileGrid.getDirection(currentTile, nextTile);
        dirX = dir.x;
        dirY = dir.y;
    }

    private boolean hasReachedNext() {
        if (dirX > 0) {
            return x > nextTile.getMapX();
        } else if (dirX < 0) {
            return x < nextTile.getMapX();
        } else if (dirY > 0) {
            return y > nextTile.getMapY();
        } else if (dirY < 0) {
            return y < nextTile.getMapY();
        }
        return false;
    }

    public int damage() {
        return (int) cHealth;
    }

    public float getPathPosition() {
        return grid.getEnemiesPath().indexOf(currentTile);
    }

    public boolean hasReachedTarget() {
        return currentTile == grid.getEndTile();
    }
}
