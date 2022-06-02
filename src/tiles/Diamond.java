package tiles;

// Diamond class inherits from tile.
// Stores diamond image file location for map load.
public class Diamond extends Tile {
    private final String ImageLocation = "/tiles/images/diamond.jpg";

    public Diamond(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
