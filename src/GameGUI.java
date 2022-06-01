import tiles.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameGUI extends JFrame {

    private final JFrame frame = new JFrame("GameGUI");
    private JLayeredPane layerPanels = new JLayeredPane();

    private JPanel gamePanel = new JPanel();
    private JLabel[][] gridDisplayPoints = new JLabel[12][12];

    private JPanel movesPanel = new JPanel();
    private JLabel movesTxt = new JLabel();

    /**
     * Sets bounds and displays window.
     */
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

                if (gridDisplayPoints[i][j] != null) {
                    gamePanel.remove(gridDisplayPoints[i][j]);
                }

                gridDisplayPoints[i][j] = new JLabel();
                gridDisplayPoints[i][j].setIcon(image);
                gridDisplayPoints[i][j].setHorizontalAlignment(JLabel.CENTER);
                gamePanel.add(gridDisplayPoints[i][j]);

                if (square instanceof Player) {
                    movement.setPlayerCoords(j, i);
                    movement.setComponent(gridDisplayPoints[i][j]);
                }
            }
        }
        frame.setVisible(true);
    }

    public void showMoves(int moves) {
        movesTxt.setText("<html><div style='text-align: center; color: white;'>Congratulations! You completed <br> this level in <span style='color: #62ed32;'>" + moves + "</span> moves</div><html>");

        movesPanel.setLayout(new GridBagLayout());
        movesPanel.setSize(398,397);
        movesPanel.setBackground(new Color(0, 0, 0, 0.5f));
        movesPanel.add(movesTxt);

        layerPanels.add(movesPanel, 1, 1);
    }

    public void clearMoves() {
        layerPanels.remove(movesPanel);
    }
}
