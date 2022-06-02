package tiles;

// Player class inherits from tile.
// Stores player image file location for map load.
public class Player extends Tile {
    private final String ImageLocation =  "/tiles/images/player.jpg";

    public Player(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
