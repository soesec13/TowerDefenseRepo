package helper;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class Artist {
    public static final int SCREEN_WIDTH = 1280 + 3 * 64, SCREEN_HEIGHT = 960;
    public static final int GAMEFIELD_WIDTH = 1280, GAMEFIELD_HEIGHT = 960;
    public static final int TILE_W = 64, TILE_H = 64;

    public static void beginSession(int width, int height) {
        try {
            Display.destroy();
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //Display.getParent().setSize(WINDOW_WIDTH,WINDOW_WIDTH);


        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public static void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_CLEAR_VALUE);
        GL11.glLoadIdentity();
    }

    public static void drawTexture(Texture t, float x, float y, float w, float h) {
        t.bind(); // or GL11.glBind(texture.getTextureID());
        GL11.glTranslatef(x, y, 0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(w, 0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(w, h);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, h);
        GL11.glEnd();
        GL11.glLoadIdentity();
    }

    public static void drawTileTexture(Texture t, int gridX, int gridY, float w, float h, float rotation) {
        t.bind(); // or GL11.glBind(texture.getTextureID());
        GL11.glTranslatef(gridX * TILE_W + w / 2, gridY * TILE_H + h / 2, 0);
        GL11.glRotatef(rotation, 0, 0, 1);
        GL11.glTranslatef(-w / 2, -h / 2, 0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(TILE_W, 0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(TILE_W, TILE_H);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, TILE_H);
        GL11.glEnd();
        GL11.glLoadIdentity();
    }

    public static void drawTileTexture(Texture t, int gridX, int gridY, float w, float h) {
        t.bind(); // or GL11.glBind(texture.getTextureID());
        GL11.glTranslatef(gridX * TILE_W, gridY * TILE_H, 0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(TILE_W, 0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(TILE_W, TILE_H);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, TILE_H);
        GL11.glEnd();
        GL11.glLoadIdentity();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments) {
        float theta = (float) (2 * 3.1415926 / (float) num_segments);
        float tangetial_factor = (float) Math.tan(theta);//calculate the tangential factor

        float radial_factor = (float) Math.cos(theta);//calculate the radial factor

        float x = r;//we start at angle = 0

        float y = 0;

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int ii = 0; ii < num_segments; ii++) {
            GL11.glVertex2f(x + cx, y + cy);//output vertex

            //calculate the tangential vector
            //remember, the radial vector is (x, y)
            //to get the tangential vector we flip those coordinates and negate one of them

            float tx = -y;
            float ty = x;

            //add the tangential vector

            x += tx * tangetial_factor;
            y += ty * tangetial_factor;

            //correct using the radial factor

            x *= radial_factor;
            y *= radial_factor;
        }
        GL11.glEnd();
    }


    public static void drawTexture(Texture t) {
        drawTexture(t, 0, 0, t.getImageWidth(), t.getImageHeight());
    }

    public static Texture loadTexture(String name) {
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream("res/" + name + ".png");
        try {
            tex = TextureLoader.getTexture("PNG", in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tex;
    }

}
