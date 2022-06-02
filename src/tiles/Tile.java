package tiles;

// Parent class of all tiles.
// Defines bPushable and bBlocked variables and sets them to when tiles are created.
// getImage defined to be used in child class', so that on map load the game gui can display image on each respective tile.
public abstract class Tile {
    public boolean bPushable;
    public boolean bBlocked;

    public Tile(boolean bPushable, boolean bBlocked) {
        this.bPushable = bPushable;
        this.bBlocked = bBlocked;
    }

    public abstract String getImage();
}
