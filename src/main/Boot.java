package main;

import helper.Artist;
import helper.Clock;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.util.ResourceLoader;

import java.net.URI;
import java.net.URISyntaxException;

import static state.ProgramStateManager.*;

public class Boot {
    public static URI levelLocation;

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", "C:\\Users\\sebas\\Desktop\\Schule\\Programming\\Libraries\\lwjgl-2.9.3\\native\\windows");
        try {
            levelLocation = ResourceLoader.getResource("res/levels").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        Artist.beginSession(Artist.SCREEN_WIDTH, Artist.SCREEN_HEIGHT);
        setCurrentState(States.MainMenu);
        while (!Display.isCloseRequested()) {
            Clock.update();
            getCurrentState().update();
            getCurrentState().render();
            if (Display.wasResized()) GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            Display.update();
            Display.sync(60);
        }
    }
}
