package tiles;

public class Coin extends Tile {
    private final String ImageLocation =  "/tiles/images/coin.jpg";

    public Coin(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
