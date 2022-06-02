package tiles;

// Wall class inherits from tile.
// Stores wall image file location for map load.
public class Wall extends Tile {
    private final String ImageLocation =  "/tiles/images/wall.jpg";

    public Wall(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
