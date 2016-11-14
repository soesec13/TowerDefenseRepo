package helper;

import java.awt.*;

/**
 * Created by sebi on 010 10/11/2016.
 */
public final class Checking {
    public static boolean collision(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }

    public static boolean inRange(float x0, float y0, float x1, float y1, float w1, float h1, float range) {
        float dx1 = x1 - x0;
        float dx2 = x1 + w1 - x0;
        float dx = Math.min(Math.abs(dx1), Math.abs(dx2));
        float dy1 = y1 - y0;
        float dy2 = y1 + h1 - y0;
        float dy = Math.min(Math.abs(dy1), Math.abs(dy2));
        float dis = (float) Math.sqrt(dx * dx + dy * dy);
        return dis <= range;
    }
}
