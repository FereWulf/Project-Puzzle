package tiles;

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
