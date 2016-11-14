package main;

import helper.Artist;
import helper.FileChoosing;
import map.TileGrid;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.opengl.Texture;
import state.ProgramStateManager;
import ui.Button;
import ui.Menu;
import ui.Screen;
import ui.UiElement;

import java.io.File;
import java.io.IOException;

/**
 * Created by sebi on 002 02/11/2016.
 */


public class MainMenu extends Screen {
    public static boolean hasLoaded = false;
    public static Document loadedFile = null;
    public static TileGrid loadedTileGrid;
    private Menu startMenu;
    private Texture background;

    public MainMenu() {
        super(Artist.SCREEN_WIDTH, Artist.SCREEN_HEIGHT);
        Texture startTexture = Artist.loadTexture("start2");
        this.background = Artist.loadTexture("startscreen");

        startMenu = new Menu();
        Button start = new Button(Artist.SCREEN_WIDTH / 2 - 300 / 2, Artist.SCREEN_WIDTH / 2 - 300, startTexture, "start");
        Button load = new Button(Artist.loadTexture("load"), "load");
        Button editor = new Button(Artist.loadTexture("editor"), "editor");

        start.setWidth(300);
        start.setHeight(100);
        load.setWidth(300);
        load.setHeight(100);
        editor.setWidth(300);
        editor.setHeight(100);


        start.alignSouth(load, 10);
        load.alignSouth(editor, 10);

        startMenu.add(start, t -> start(t));
        startMenu.add(load, b -> load(b));
        startMenu.add(editor, b -> editor(b));

        super.addMenu(startMenu);
    }

    @Override
    public void update() {
        super.updateMenues();
    }

    @Override
    public void render() {
        Artist.drawTexture(background, 0, 0, Artist.SCREEN_WIDTH, Artist.SCREEN_HEIGHT);
        super.draw();
    }

    public void start(UiElement b) {
        Game g = ProgramStateManager.getGame();
        g.start();
        ProgramStateManager.setCurrentState(ProgramStateManager.States.Game);
    }

    public void load(UiElement b) {
        try {
            File f = FileChoosing.openFile(Boot.levelLocation);
            SAXBuilder sax = new SAXBuilder();
            loadedFile = sax.build(f);
            loadedTileGrid = TileGrid.loadFrom(loadedFile);
            hasLoaded = true;
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editor(UiElement b) {
        ProgramStateManager.setCurrentState(ProgramStateManager.States.MapEditor);
    }
}
