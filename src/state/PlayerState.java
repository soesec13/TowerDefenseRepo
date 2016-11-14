package state;

/**
 * Created by sebi on 004 04/11/2016.
 */
public enum PlayerState {
    PLACING_TOWER, SELECTING;
    private static PlayerState current;

    public static PlayerState getCurrent() {
        return current;
    }

    public static void setCurrent(PlayerState current) {
        PlayerState.current = current;
    }
}
