import tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class GameGUI extends JFrame {

    private final JFrame frame = new JFrame("GameGUI");
    private JPanel window = new JPanel();
    private JLabel[][] gridDisplayPoints = new JLabel[12][12];

    /**
     * Sets bounds and displays window.
     */
    public void display() {
        frame.setPreferredSize(new Dimension(400, 400));

        GridLayout grid = new GridLayout();
        grid.setRows(12);
        grid.setColumns(12);
        window.setSize(400, 400);
        window.setLayout(grid);

        frame.setContentPane(window);
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
                    window.remove(gridDisplayPoints[i][j]);
                }

                gridDisplayPoints[i][j] = new JLabel();
                gridDisplayPoints[i][j].setIcon(image);
                gridDisplayPoints[i][j].setHorizontalAlignment(JLabel.CENTER);
                window.add(gridDisplayPoints[i][j]);

                if (square instanceof Player) {
                    movement.setPlayerCoords(j, i);
                    movement.setComponent(gridDisplayPoints[i][j]);
                }
            }
        }
        frame.setVisible(true);
    }
}
