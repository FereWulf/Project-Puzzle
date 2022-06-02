import tiles.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameGUI extends JFrame {

    // Initialising swing objects to be used later.
    private final JFrame frame = new JFrame("GameGUI");
    private JLayeredPane layerPanels = new JLayeredPane();

    private JPanel gamePanel = new JPanel();
    private JLabel[][] gridDisplayPoints = new JLabel[12][12];

    private JPanel movesPanel = new JPanel();
    private JLabel movesTxt = new JLabel();


    // Set bounds and displays window.
    public void display() {
        frame.setPreferredSize(new Dimension(416, 440));

        GridLayout grid = new GridLayout();
        grid.setRows(12);
        grid.setColumns(12);
        gamePanel.setSize(400, 400);
        gamePanel.setLayout(grid);

        layerPanels.add(gamePanel, 0, 0);

        frame.setContentPane(layerPanels);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
    }

    /**
     * Loads tile images and displays them in their map positions on the window.
     * @param mapping tile placement by coordinate.
     * @param movement player movement class.
     */
    public void populateWorld(HashMap<Coords, Tile> mapping, PlayerMovement movement) {
        for (int i = 0; i < gridDisplayPoints.length; i++) {
            for (int j = 0; j < gridDisplayPoints.length; j++) {
                Tile square = mapping.get(new Coords(j, i));
                Icon image = new ImageIcon(getClass().getResource(square.getImage()));

                // Clears grid point before setting it with new tile image.
                if (gridDisplayPoints[i][j] != null) {
                    gamePanel.remove(gridDisplayPoints[i][j]);
                }

                gridDisplayPoints[i][j] = new JLabel();
                gridDisplayPoints[i][j].setIcon(image);
                gridDisplayPoints[i][j].setHorizontalAlignment(JLabel.CENTER);
                gamePanel.add(gridDisplayPoints[i][j]);

                // Checks if tile is player class so that player movement can be set up.
                if (square instanceof Player) {
                    movement.setPlayerCoords(j, i);
                    movement.setComponent(gridDisplayPoints[i][j]);
                }
            }
        }
        frame.setVisible(true);
    }

    /**
     * Sets bounds and displays end of level screen with number of moves the player took to finish the level.
     * @param moves the number of moves the player took to finish the level.
     */
    public void showMoves(int moves) {
        // HTML used to center text and make all the text white except for the number of moves, which is green.
        movesTxt.setText("<html><div style='text-align: center; color: white;'>Congratulations! You completed <br> this level in <span style='color: #62ed32;'>" + moves + "</span> moves</div><html>");

        movesPanel.setLayout(new GridBagLayout());
        movesPanel.setSize(398,397);
        movesPanel.setBackground(new Color(0, 0, 0, 0.5f));
        movesPanel.add(movesTxt);

        layerPanels.add(movesPanel, 1, 1);
    }

    // Removes level end screen from displaying.
    public void clearMoves() {
        layerPanels.remove(movesPanel);
    }
}
