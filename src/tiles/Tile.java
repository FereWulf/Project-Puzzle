package tiles;

public abstract class Tile {
    public boolean bPushable;
    public boolean bBlocked;

    public Tile(boolean bPushable, boolean bBlocked) {
        this.bPushable = bPushable;
        this.bBlocked = bBlocked;
    }

    public abstract String getImage();
}
