package main;

import helper.Artist;
import helper.FileChoosing;
import map.TileGrid;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import state.ProgramStateManager;
import ui.Screen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MapEditor extends Screen {
    private int selectedTile = 0;
    private TileGrid grid;
    private String name;

    public MapEditor(int width, int height, String name) {
        super(width * Artist.TILE_W, height * Artist.TILE_H);
        if (MainMenu.hasLoaded) {
            grid = MainMenu.loadedTileGrid;
        } else {
            grid = new TileGrid(width, height);
        }

        this.name = name;
    }

    @Override
    public void update() {
        while (Keyboard.next() && !Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                save();
            } else if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                ProgramStateManager.setCurrentState(ProgramStateManager.States.MainMenu);
            } else {
                selectedTile = Keyboard.getEventKey() - 2;
            }
        }
        if (Mouse.isButtonDown(0)) {
            grid.setTile(Mouse.getX(), Artist.SCREEN_HEIGHT - Mouse.getY(), selectedTile);
        }
    }

    @Override
    public void render() {
        grid.draw();
        super.draw();
    }

    private void save() {
        try {

            Element level = new Element("Level");
            level.addContent(grid.getMapElement());
            Document doc = new Document(level);
            XMLOutputter xout = new XMLOutputter();
            File levelFile = FileChoosing.saveFile(Boot.levelLocation);
            FileWriter fw = new FileWriter(levelFile);
            xout.output(doc, fw);
            fw.flush();
            fw.close();
            ProgramStateManager.setCurrentState(ProgramStateManager.States.MainMenu);
            ProgramStateManager.getGame().setTg(grid);
            grid.loadPath();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
