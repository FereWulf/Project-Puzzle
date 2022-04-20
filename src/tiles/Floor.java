package tiles;

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
