import tiles.*;
import tiles.Box;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerMovement {

    // Variables defined for key registering (player movement/restarting level).
    private JLabel component;
    private static final int windowFocus = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";
    private static final String RESTART = "restart";

    // Defining variables for player movement.
    private int x;
    private int y;

    private int currentTnt;
    private int totalTnt;
    private int moves;

    private Tile priorTile;

    private HashMap<Coords, Tile> mapping;
    private GameGUI gui;
    private Game game;

    /**
     * Gets the player's initial coordinates.
     * @param x x point of player.
     * @param y y point of player.
     */
    public void setPlayerCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Takes in classes/hashmap to store in pre-defined variable and to use later.
     * @param mapping map coords and tile one each coords stored in a hashmap.
     * @param gui game gui class.
     * @param game game class.
     */
    public void setMap(HashMap<Coords, Tile> mapping, GameGUI gui, Game game) {
        this.mapping = mapping;
        this.gui = gui;
        this.game = game;
    }

    // Called on level load to tally how many diamonds need to be turned into tnt.
    public void addTnt() {
        totalTnt += 1;
    }

    // Resets tally's for next level/redo of level.
    public void clearTallies() {
        currentTnt = 0;
        totalTnt = 0;
        moves = 0;
    }

    /**
     * Binds movement to respective keys and class MoveCharacter class function.
     * Also binds restart to R key, which calls function to restart current level.
     * @param component current JLabel grid point.
     */
    public void setComponent(JLabel component) {
        this.component = component;

        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("W"), MOVE_UP);

        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("S"), MOVE_DOWN);

        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("A"), MOVE_LEFT);

        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);

        component.getActionMap().put(MOVE_UP, new MoveCharacter(-1, 0));
        component.getActionMap().put(MOVE_DOWN, new MoveCharacter(1, 0));
        component.getActionMap().put(MOVE_LEFT, new MoveCharacter(0, -1));
        component.getActionMap().put(MOVE_RIGHT, new MoveCharacter(0, 1));


        component.getInputMap(windowFocus).put(KeyStroke.getKeyStroke("R"), RESTART);

        component.getActionMap().put(RESTART, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTallies();
                game.world();
            }
        });
    }


    private class MoveCharacter extends AbstractAction {
        // Defining variables.
        private int nextX;
        private int nextY;
        private int down;
        private int right;

        /**
         * Sets variables to be used in actionPerformed.
         * @param down -1 = up, 1 = down.
         * @param right -1 = left, 1 = right.
         */
        private MoveCharacter(int down, int right) {
            this.nextX = x + right;
            this.nextY = y + down;
            this.down = down;
            this.right = right;
        }

        /**
         * Function called when a player presses W, A, S or D.
         * @param e the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Gets and sets current and tile the player wants to move onto.
            Tile tile = mapping.get(new Coords(nextX, nextY));
            Tile player = mapping.get(new Coords(x, y));

            // Checks if the tile the player wants to move to is pushable before checking if it is blocked.
            if (tile.bPushable) {
                // Gets tile that is a tile ahead of the tile the player wants to move to.
                Tile pushCheck = mapping.get(new Coords(nextX + right, nextY + down));

                // Checks if the player is pushing a box onto a diamond. Else, check if the player is not pushing a box into another box.
                if (pushCheck instanceof Diamond && tile instanceof Box) {
                    // Create tnt and add to currentTnt tally. Update map.
                    currentTnt += 1;

                    mapping.put(new Coords(nextX + right, nextY + down), new Tnt(false, true));
                    mapping.put(new Coords(nextX, nextY), player);
                    retile(tile);

                    gui.populateWorld(mapping, PlayerMovement.this);

                    // If the player has made all diamonds into tnt, show total moves then go onto next map in 3 seconds.
                    if (currentTnt == totalTnt) {
                        gui.showMoves(moves);

                        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        executorService.schedule(() -> { game.nextLevel(); }, 3, TimeUnit.SECONDS);
                    }
                } else if (!pushCheck.bBlocked && !(pushCheck instanceof Box)) {
                    // Update map with new box and player coords.
                    mapping.put(new Coords(nextX + right, nextY + down), tile);
                    mapping.put(new Coords(nextX, nextY), player);
                    retile(tile);
                    gui.populateWorld(mapping, PlayerMovement.this);
                }
            } else if (!tile.bBlocked) {
                // Update map with new player coords.
                mapping.put(new Coords(nextX, nextY), player);
                retile(tile);
                gui.populateWorld(mapping, PlayerMovement.this);
            }
        }

        /**
         * Function calls whenever the player can move. Purpose is to fill the void left when the player moves with what the player was previously on (floor/diamond).
         * Function also tallies up the total moves it took to complete the level.
         * @param tile the tile that the player is about to occupy.
         */
        private void retile(Tile tile) {
            moves += 1;
            if (priorTile == null) {
                mapping.put(new Coords(x, y), new Floor(false, false));
            } else {
                mapping.put(new Coords(x, y), priorTile);
            }

            // Checks if the tile the player is about to occupy was not a box so that the player does not create boxes.
            if (!(tile instanceof Box)) {
                priorTile = tile;
            } else {
                priorTile = new Floor(false, false);
            }
        }
    }
}
