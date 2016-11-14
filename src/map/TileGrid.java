package map;

import helper.Artist;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class TileGrid {
    private Tile[][] map;
    private Path enemiesPath;

    public TileGrid(int w, int h) {
        map = new Tile[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                map[i][j] = new Tile(TileType.GRASS, i, j);
        loadPath();
    }

    public TileGrid(int w, int h, String tiles) {
        map = new Tile[w][h];
        int stringpos = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int id = Character.getNumericValue(tiles.charAt(stringpos));
                map[i][j] = new Tile(TileType.getById(id), i, j);
                stringpos++;
            }
        }
        loadPath();
    }

    public static TileGrid loadFrom(Document doc) {
        Element m = doc.getRootElement().getChild("map");
        int w = Integer.parseInt(m.getAttributeValue("width"));
        int h = Integer.parseInt(m.getAttributeValue("height"));
        String tiles = m.getText();
        return new TileGrid(w, h, tiles);
    }

    public static Point getDirection(Tile from, Tile to) {
        int x = 0, y = 0;
        if (from.getY() == to.getY())
            x = from.getX() < to.getX() ? 1 : -1;
        else
            y = from.getY() < to.getY() ? 1 : -1;
        return new Point(x, y);
    }

    public void draw() {
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                map[i][j].draw();
    }

    public void loadPath() {
        enemiesPath = new Path(this);
    }

    public Tile[] getNeighbours(Tile t) {
        Point p = getGridCoord(t);
        int x = p.x;
        int y = p.y;
        Tile[] neighbours = new Tile[4];
        try {
            neighbours[0] = map[x + 1][y];
        } catch (Exception e) {
        }
        try {
            neighbours[1] = map[x - 1][y];
        } catch (Exception e) {
        }
        try {
            neighbours[2] = map[x][y + 1];
        } catch (Exception e) {
        }
        try {
            neighbours[3] = map[x][y - 1];
        } catch (Exception e) {
        }
        return neighbours;
    }

    private Point getGridCoord(Tile t) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                Tile c = map[i][j];
                if (c.equals(t))
                    return new Point(i, j);
            }
        }
        throw new RuntimeException("Tile doesn't exist!");
    }

    public Tile getStartTile() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                Tile t = map[i][j];
                if (t.getType().equals(TileType.SPAWN))
                    return t;
            }
        }
        return map[0][0];
    }

    public Tile getEndTile() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                Tile t = map[i][j];
                if (t.getType().equals(TileType.GEM))
                    return t;
            }
        }
        return map[map.length - 1][map[0].length - 1];
    }

    public boolean isBuildable(int gridX, int gridY) {
        if (gridX > map.length - 1)
            return false;
        if (gridY > map[0].length - 1)
            return false;
        return mapAt(gridX, gridY).getType().isBuildable();
    }

    private Tile mapAt(int gridX, int gridY) {
        if (gridX > map.length - 1)
            gridX = map.length - 1;
        if (gridY > map[0].length - 1)
            gridY = map.length - 1;
        if (gridX < 0)
            gridX = 0;
        if (gridY < 0)
            gridY = 0;
        return map[gridX][gridY];
    }

    public void setTile(float x, float y, TileType t) {
        int tx = (int) (x / Artist.TILE_W);
        int ty = (int) (y / Artist.TILE_H);
        map[tx][ty].setType(t);
    }

    public void setTile(float x, float y, int id) {
        int tx = (int) (x / Artist.TILE_W);
        int ty = (int) (y / Artist.TILE_H);
        map[tx][ty].setType(TileType.getById(id));
    }

    public Element getMapElement() {
        Element mapElem = new Element("map");
        mapElem.setAttribute("width", "" + map.length);
        mapElem.setAttribute("height", "" + map[0].length);
        StringBuffer bw = new StringBuffer();
        try {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    bw.append(map[i][j].getType().getId());
                }
            }
            mapElem.addContent(bw.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to save!");
        }
        return mapElem;
    }

    public Path getEnemiesPath() {
        return enemiesPath;
    }
}
