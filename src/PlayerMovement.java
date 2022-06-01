import tiles.*;
import tiles.Box;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerMovement {

    private JLabel component;
    private static final int windowFocus = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";
    private static final String RESTART = "restart";

    private int x;
    private int y;

    private int currentTnt;
    private int totalTnt;
    private int moves;

    private Tile priorTile;

    private HashMap<Coords, Tile> mapping;
    private GameGUI gui;
    private Game game;

    public void setPlayerCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setMap(HashMap<Coords, Tile> mapping, GameGUI gui, Game game) {
        this.mapping = mapping;
        this.gui = gui;
        this.game = game;
    }

    public void addTnt() {
        totalTnt += 1;
    }

    public void clearTallies() {
        currentTnt = 0;
        totalTnt = 0;
        moves = 0;
    }

    /**
     * Binds movement to respective keys and MoveCharacter class function.
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
         * Checks if the player can move onto the next tile before moving character.
         * Also checks if player moves into diamond, they go to next level.
         * @param e the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            Tile tile = mapping.get(new Coords(nextX, nextY));
            Tile player = mapping.get(new Coords(x, y));

            if (tile.bPushable) {
                Tile pushCheck = mapping.get(new Coords(nextX + right, nextY + down));

                if (pushCheck instanceof Diamond && tile instanceof Box) {
                    currentTnt += 1;

                    mapping.put(new Coords(nextX + right, nextY + down), new Tnt(false, true));
                    mapping.put(new Coords(nextX, nextY), player);
                    retile(tile);

                    gui.populateWorld(mapping, PlayerMovement.this);

                    if (currentTnt == totalTnt) {
                        gui.showMoves(moves);

                        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        executorService.schedule(() -> { game.nextLevel(); }, 3, TimeUnit.SECONDS);
                    }
                } else if (!pushCheck.bBlocked && !(pushCheck instanceof Box)) {
                    mapping.put(new Coords(nextX + right, nextY + down), tile);
                    mapping.put(new Coords(nextX, nextY), player);
                    retile(tile);
                    gui.populateWorld(mapping, PlayerMovement.this);
                }
            } else if (!tile.bBlocked) {
                mapping.put(new Coords(nextX, nextY), player);
                retile(tile);
                gui.populateWorld(mapping, PlayerMovement.this);
            }
        }

        private void retile(Tile tile) {
            moves += 1;
            if (priorTile == null) {
                mapping.put(new Coords(x, y), new Floor(false, false));
            } else {
                mapping.put(new Coords(x, y), priorTile);
            }

            if (!(tile instanceof Box)) {
                priorTile = tile;
            } else {
                priorTile = new Floor(false, false);
            }
        }
    }
}
