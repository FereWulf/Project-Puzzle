import javax.swing.*;
import java.awt.*;

public class GameGUI {

    private JLabel[][] gridDisplayPoints = new JLabel[12][12];

    public void display() {
        JFrame frame = new JFrame("GameGUI");
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setMinimumSize(new Dimension(200, 200));

        JPanel window = new JPanel();
        GridLayout grid = new GridLayout();
        grid.setRows(12);
        grid.setColumns(12);
        window.setSize(400, 400);
        window.setLayout(grid);

        frame.setContentPane(window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        for (int i = 0; i < gridDisplayPoints.length; i++) {
            for (int j = 0; j < gridDisplayPoints.length; j++) {
                gridDisplayPoints[i][j] = new JLabel();
                gridDisplayPoints[i][j].setText(".");
                gridDisplayPoints[i][j].setHorizontalAlignment(JLabel.CENTER);
                window.add(gridDisplayPoints[i][j]);
            }
        }
    }
}
