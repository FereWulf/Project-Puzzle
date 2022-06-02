package tiles;

// Tnt class inherits from tile.
// Stores tnt image file location for map load.
public class Tnt extends Tile {
    private final String ImageLocation = "/tiles/images/tnt.jpg";

    public Tnt(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
