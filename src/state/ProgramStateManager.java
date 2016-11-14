package state;

import helper.Artist;
import main.Game;
import main.MainMenu;
import main.MapEditor;

/**
 * Created by sebi on 002 02/11/2016.
 */
public class ProgramStateManager {
    private static Game game;
    private static MainMenu mainMenu;
    private static MapEditor editor;
    private static States currentState = States.MainMenu;

    public static ProgramState getCurrentState() {
        return getState(currentState);
    }

    public static void setCurrentState(States currentState) {
        ProgramStateManager.currentState = currentState;
    }

    public static ProgramState getState(States s) {
        switch (s) {
            case Game:
                if (game == null)
                    game = new Game();
                return game;
            case MainMenu:
                if (mainMenu == null)
                    mainMenu = new MainMenu();
                return mainMenu;
            case MapEditor:
                if (editor == null)
                    editor = new MapEditor(Artist.SCREEN_WIDTH / Artist.TILE_W, Artist.SCREEN_HEIGHT / Artist.TILE_H, "test");
                return editor;
            default:
                mainMenu = new MainMenu();
                currentState = States.MainMenu;
                return mainMenu;
        }
    }

    public static Game getGame() {
        return (Game) getState(States.Game);
    }

    public static MainMenu getMainMenu() {
        return (MainMenu) getState(States.MainMenu);
    }

    public static MapEditor getEditor() {
        return (MapEditor) getState(States.MapEditor);
    }

    public enum States {
        MainMenu, Game, MapEditor
    }
}
