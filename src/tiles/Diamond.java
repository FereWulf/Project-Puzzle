package tiles;

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
