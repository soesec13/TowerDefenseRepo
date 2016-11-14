package entities.towers;

import entities.Entity;
import entities.enemies.Enemy;
import entities.towers.attacking.Attack;
import entities.towers.attacking.IRangeAttack;
import helper.Artist;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by sebi on 007 07/11/2016.
 */
public class Tower extends Entity {
    private static final Texture rangeTexture = Artist.loadTexture("radius");
    private int cost;
    private Attack attack;
    private Texture t;
    private Attack.Priority priority = Attack.Priority.FIRST;
    private float deg = 270;

    public Tower(Texture t, float gridX, float gridY, int w, int h, int cost) {
        this.x = gridX;
        this.y = gridY;
        this.width = w;
        this.height = h;
        this.cost = cost;
        this.t = t;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
        attack.setOwner(this);
    }

    @Override
    public void draw() {
        Enemy currentTarget = attack.getTarget();
        if (currentTarget != null) {
            float dx = currentTarget.getX() - this.getX() * Artist.TILE_W;
            float dy = currentTarget.getY() - this.getY() * Artist.TILE_H;

            float rad = (float) Math.atan2(dy, dx);
            deg = (float) Math.toDegrees(rad);

            if (deg < 0)
                deg += 360;
        }

        Artist.drawTileTexture(t, (int) x, (int) y, width, height, deg + 90);
        attack.draw();
    }

    @Override
    public void update() {
        attack.update();
    }

    public void drawAt(int gridX, int gridY) {
        Artist.drawTileTexture(t, gridX, gridY, width, height);
        if (attack instanceof IRangeAttack) {

            IRangeAttack a = (IRangeAttack) attack;
            float posx = gridX * Artist.TILE_W - a.getRange() + width / 2;
            float posY = gridY * Artist.TILE_H - a.getRange() + height / 2;
            Artist.drawTexture(rangeTexture,
                    posx,
                    posY,
                    a.getRange() * 2, a.getRange() * 2);
            Texture bullet = Artist.loadTexture("basicbullet");
            //Artist.drawTexture(bullet,gridX*Artist.TILE_W-a.getRange(),gridY*Artist.TILE_H-a.getRange(),16,16);
            //Artist.drawTexture(bullet,gridX*Artist.TILE_W+a.getRange(),gridY*Artist.TILE_H+a.getRange(),16,16);
        }
    }

    public Attack.Priority getPriority() {
        return priority;
    }

    public void setPriority(Attack.Priority priority) {
        this.priority = priority;
    }

    public int getCost() {
        return cost;
    }
}
