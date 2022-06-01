import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import tiles.*;

public class Map {

    /**
     * Reads current level file and maps each letter to respective tile type.
     * @param level current level game is on.
     * @return returns hashmap with tile placement by coordinate.
     */
    public HashMap<Coords,Tile> loadMap(int level, PlayerMovement movement) {
        HashMap<Coords, Tile> mapping = null;
        FileReader fr;

        try {
            if (new File("maps/level"+ level +".txt").isFile()) {
                fr = new FileReader("maps/level" + level + ".txt");
            } else {
                fr = new FileReader("maps/end.txt");
            }

            mapping = new HashMap<>();

            int i;

            int x = 0;
            int y = 0;

            while ((i = fr.read()) != -1) {
                switch (i) {
                    case 'W':
                        mapping.put(new Coords(x, y), new Wall(false, true));
                        break;
                    case 'F':
                        mapping.put(new Coords(x, y), new Floor(false, false));
                        break;
                    case 'B':
                        mapping.put(new Coords(x, y), new Box(true, false));
                        break;
                    case 'D':
                        mapping.put(new Coords(x, y), new Diamond(false, false));
                        movement.addTnt();
                        break;
                    case 'P':
                        mapping.put(new Coords(x, y), new Player(false, false));
                        break;
                    case '\r':
                        break;
                    case '\n':
                        x = 0;
                        y += 1;
                        continue;
                    default:
                        throw new RuntimeException("Invalid tile type: " + (char)i + " at x: " + x + " y: " + y);
                }
                x += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }
}
