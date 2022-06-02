import tiles.Tile;

import java.util.HashMap;

// Entry point for the game.
public class Game {
    private int level;
    private final GameGUI gui = new GameGUI();
    private final Map map = new Map();
    private final PlayerMovement movement = new PlayerMovement();

    // Loading and displaying first level on start.
    public Game() {
        level = 0;

        gui.display();

        world();
    }

    public static void main(String args[]) {
        Game game = new Game();
    }

    // Gets map from text file and displays it on screen.
    public void world() {
        HashMap<Coords, Tile> mapping = map.loadMap(level, movement);

        gui.populateWorld(mapping, movement);
        movement.setMap(mapping, gui, this);
    }

    // Resets values, remove end level screen, and loads next level.
    public void nextLevel() {
        level += 1;
        movement.clearTallies();
        gui.clearMoves();
        world();
    }
}
