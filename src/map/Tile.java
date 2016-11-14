package map;

import entities.Entity;
import helper.Artist;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class Tile extends Entity {
    private TileType type;

    public Tile(TileType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = type.getTexture().getImageWidth();
        this.height = type.getTexture().getImageHeight();
    }

    public float getMapX() {
        return this.getX() * Artist.TILE_W;
    }

    public float getMapY() {
        return this.getY() * Artist.TILE_H;
    }

    @Override
    public void draw() {
        Artist.drawTileTexture(type.getTexture(), (int) x, (int) y, width, height);
    }

    @Override
    public void update() {

    }


    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

}
