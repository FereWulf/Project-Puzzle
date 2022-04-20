package tiles;

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
