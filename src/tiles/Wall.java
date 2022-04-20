package tiles;

public class Wall extends Tile {
    private final String ImageLocation =  "/tiles/images/wall.jpg";

    public Wall(boolean bPushable, boolean bBlocked) {
        super(bPushable, bBlocked);
    }

    @Override
    public String getImage() {
        return ImageLocation;
    }
}
