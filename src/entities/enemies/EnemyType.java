package entities.enemies;

import helper.Artist;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by sebi on 004 04/11/2016.
 */
public enum EnemyType {
    RED("red", 100, 100, 1, 10), BLUE("blue", 200, 50, 2, 15);

    private float health, speed;
    private String name;
    private Texture tex;
    private int id;
    private int reward;

    EnemyType(String name, float health, float speed, int id, int reward) {
        this.health = health;
        this.speed = speed;
        this.name = name;
        this.tex = Artist.loadTexture(name);
        this.id = id;
        this.reward = reward;
    }

    public static EnemyType getById(String id) {
        int i = Integer.parseInt(id);
        for (EnemyType t : values()
                ) {
            if (t.getId() == i)
                return t;
        }
        return RED;
    }

    public float getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public Texture getTex() {
        return tex;
    }

    public int getId() {
        return id;
    }

    public int getReward() {
        return reward;
    }
}
