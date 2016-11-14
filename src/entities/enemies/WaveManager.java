package entities.enemies;

import map.TileGrid;
import org.jdom2.Element;
import state.GameState;

import java.util.LinkedList;
import java.util.List;

/**
 * <waves>
 * <wave>
 * <enemy id=1 count=3 delay=2></Enemy>
 * <Enemy id=2 count=5 delay=1></Enemy>
 * </wave>
 * <p>
 * <waves/>
 */
public class WaveManager {
    private List<Wave> waves = new LinkedList<>();
    private Wave currentWave;
    private int current = 0;

    public WaveManager(Element waves, TileGrid grid) {
        for (Element wave : waves.getChildren()) {
            this.waves.add(Wave.createWave(wave, grid));
        }
        currentWave = this.waves.get(current);
    }

    public WaveManager(Wave[] waves) {
        for (Wave w : waves) {
            this.waves.add(w);
        }
        currentWave = this.waves.get(current);
    }

    public void draw() {
        currentWave.draw();
    }

    public void update() {
        currentWave.update();
        if (currentWave.isBeaten()) {
            current++;
            try {
                currentWave = waves.get(current);
            } catch (Exception e) {
                GameState.setCurrentState(GameState.WON);
            }
        }
    }

    public Wave getCurrentWave() {
        return currentWave;
    }
}
