package main;

import entities.enemies.Wave;
import entities.enemies.WaveManager;
import entities.towers.Player;
import entities.towers.TowerFactory;
import helper.Artist;
import helper.Clock;
import map.TileGrid;
import org.lwjgl.input.Keyboard;
import state.GameState;
import state.ProgramStateManager;
import ui.*;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class Game extends Screen {
    private static boolean keydown = false;
    private TileGrid tg;
    private WaveManager waveManager;
    private Player player;
    private Text health, money;

    public Game() {
        super(Artist.SCREEN_WIDTH, Artist.SCREEN_HEIGHT);
        this.tg = new TileGrid(Artist.SCREEN_WIDTH / Artist.TILE_W, Artist.SCREEN_HEIGHT / Artist.TILE_H);
        player = new Player(tg, null);
    }

    private static boolean isRunning() {
        return GameState.getCurrentState() == GameState.RUNNING;
    }

    public void start() {
        //Artist.beginSession(Artist.WINDOW_WIDTH,Artist.WINDOW_WIDTH);

        if (MainMenu.hasLoaded) {
            this.setTg(MainMenu.loadedTileGrid);
            waveManager = new WaveManager(MainMenu.loadedFile.getRootElement().getChild("waves"), tg);
        } else {
            waveManager = new WaveManager(new Wave[]{Wave.createTestWave(tg), Wave.createTestWave(tg)});
        }
        GameState.setCurrentState(GameState.RUNNING);
        player = new Player(tg, waveManager);
        Menu towerpicker = new Menu(Artist.GAMEFIELD_WIDTH, 0);
        towerpicker.setBackground(Artist.loadTexture("pickerbackground"), (Artist.SCREEN_WIDTH - Artist.GAMEFIELD_WIDTH), Artist.GAMEFIELD_HEIGHT);
        Button bulletTower = new Button(
                Artist.TILE_W, 80,
                Artist.TILE_W, Artist.TILE_H,
                Artist.loadTexture(TowerFactory.Towers.BASIC.getName()),
                TowerFactory.Towers.BASIC.getName());

        towerpicker.add(bulletTower, this::setSelectedTower);
        towerpicker.add(new Text(35, 24, "Towers", Fonts.getTextFont()), (t) -> {
        });
        health = new Text(35, 800, "Health: " + player.getCurrentHealth(), Fonts.getInfoFont());
        money = new Text(35, 850, "Money: " + Player.getPurse().getCurrent(), Fonts.getInfoFont());
        towerpicker.add(health, t -> {
        });
        towerpicker.add(money, t -> {
        });
        super.addMenu(towerpicker);
        Artist.clearScreen();
    }

    private void setSelectedTower(UiElement u) {
        if (!(u instanceof Button))
            return;
        Button sender = (Button) u;
        player.setSelectedTower(TowerFactory.Towers.getByName(sender.getName()));

    }

    public TileGrid getTg() {
        return tg;
    }

    public void setTg(TileGrid tg) {
        this.tg = tg;
    }

    @Override
    public void update() {
        if (GameState.getCurrentState() == GameState.RUNNING) {
            waveManager.update();
            player.update();
            updateInfo();
        } else if (GameState.getCurrentState() == GameState.WON) {
            ProgramStateManager.setCurrentState(ProgramStateManager.States.MainMenu);
        } else if (GameState.getCurrentState() == GameState.LOST) {
            while (Keyboard.next()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
                    ProgramStateManager.setCurrentState(ProgramStateManager.States.MainMenu);

            }
        } else if (GameState.getCurrentState() == GameState.PAUSED) {
            while (Keyboard.next()) {
                System.out.println("paused and pressed");
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_ESCAPE:
                        ProgramStateManager.setCurrentState(ProgramStateManager.States.MainMenu);
                        break;
                    case Keyboard.KEY_P:
                        if (Keyboard.getEventKeyState()) {
                        } else {
                            GameState.setCurrentState(GameState.RUNNING);
                            Clock.setPaused(false);
                        }
                        return;
                }
            }
        }
        super.updateMenues();
    }

    private void updateInfo() {
        health.changeContent("Health: " + player.getCurrentHealth());
        money.changeContent("Money: " + Player.getPurse().getCurrent());
    }

    @Override
    public void render() {
        //Enemy e = new Enemy(EnemyType.BLUE,64,64,tg);
        //e.draw();
        if (isRunning()) {
            tg.draw();
            waveManager.draw();
            player.draw();
        } else if (GameState.getCurrentState() == GameState.LOST) {
            Artist.drawTexture(Artist.loadTexture("lost"), 0, 0, Artist.GAMEFIELD_WIDTH, Artist.GAMEFIELD_HEIGHT);
        }
        super.draw();
    }

    public Player getPlayer() {
        return player;
    }
}
