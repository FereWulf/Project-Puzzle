import tiles.Tile;

import java.util.HashMap;

/**
 * Entry point for the game.
 */
public class Game {
    private int level;
    private final GameGUI gui = new GameGUI();
    private final Map map = new Map();
    private final PlayerMovement movement = new PlayerMovement();

    public Game() {
        level = 0;

        gui.display();

        world();
    }

    public static void main(String args[]) {
        Game game = new Game();
    }

    public void world() {
        HashMap<Coords, Tile> mapping = map.loadMap(level);

        gui.populateWorld(mapping, movement);
        movement.setMap(mapping, gui, this);
    }

    public void nextLevel() {
        level += 1;
        world();
    }
}
