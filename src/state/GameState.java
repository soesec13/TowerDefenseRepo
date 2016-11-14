package state;

/**
 * Created by sebi on 004 04/11/2016.
 */
public enum GameState {
    RUNNING, PAUSED, WON, LOST;
    private static GameState current = RUNNING;

    public static GameState getCurrentState() {
        return current;
    }

    public static void setCurrentState(GameState current) {
        GameState.current = current;
    }
}
