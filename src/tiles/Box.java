package tiles;

public class Box extends Tile {
    private final String ImageLocation =  "/tiles/images/box.jpg";

    public Box(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
