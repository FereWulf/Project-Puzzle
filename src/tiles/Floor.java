package tiles;

// Floor class inherits from tile.
// Stores floor image file location for map load.
public class Floor extends Tile {
    private final String ImageLocation =  "/tiles/images/floor.jpg";

    public Floor(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
