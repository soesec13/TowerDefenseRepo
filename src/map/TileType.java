package map;

import helper.Artist;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by sebi on 002 02/11/2016.
 */
public enum TileType {
    GRASS(0, "grass", true, false), DIRT(1, "dirt", false, true), WATER(2, "water", false, false),
    SPAWN(3, "spawn", false, true), GEM(4, "target", false, true);
    private int id;
    private String name;
    private boolean buildable, walkable;
    private Texture text;

    TileType(int id, String name, boolean buildable, boolean walkable) {
        this.id = id;
        this.name = name;
        this.buildable = buildable;
        this.walkable = walkable;
        this.text = Artist.loadTexture(name);
    }

    public static TileType getById(int i) {
        for (TileType t : values()) {
            if (t.getId() == i)
                return t;
        }
        return GRASS;
    }

    public Texture getTexture() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
