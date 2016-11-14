package entities.towers.attacking;

import entities.Entity;
import entities.enemies.Enemy;
import helper.Artist;
import helper.Clock;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by sebi on 009 09/11/2016.
 */
public class Projectile extends Entity implements Cloneable {
    private float speed, dirX, dirY;
    private Texture t;
    private Enemy target;

    public Projectile(Texture t, float x, float y, int w, int h, float speed, float dirX, float dirY) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.speed = speed;
        this.dirY = dirY;
        this.dirX = dirX;
        this.t = t;
    }

    public void updateDirection(float dirX, float dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public void aimAt(Enemy e) {
        if (e == null)
            return;
        float targetX = e.getX() + e.getWidth() / 2;
        float targetY = e.getY() + e.getHeight() / 2;
        float distX = targetX - (this.getX() + this.getWidth() / 2);
        float distY = targetY - (this.getY() + this.getHeight() / 2);

        float len = (float) Math.sqrt(distX * distX + distY * distY);

        updateDirection(distX / len, distY / len);
        target = e;

    }

    public boolean isInBounds(float x0, float y0, float x1, float y1) {
        boolean rightOk = x + width < x1;
        boolean leftOk = x + width > x0;
        boolean bottomOk = y + height < y1;
        boolean topOk = y + height > y0;
        return rightOk && leftOk && bottomOk && topOk;
    }

    public void setT(Texture t) {
        this.t = t;
    }

    @Override
    public void draw() {
        if (target == null)
            return;
        Artist.drawTexture(t, x, y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update() {
        if (target == null)
            return;
        x += dirX * speed * Clock.delta();
        y += dirY * speed * Clock.delta();
    }
}
