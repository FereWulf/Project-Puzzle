import java.util.Objects;

public class Coords {
    // Sets coords values as a hashmap hashcode.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x && y == coords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    // Defining variables to store x and y of each tile.
    private int x;
    private int y;

    /**
     * Stores a tiles coordinates.
     * @param x x point of tile.
     * @param y y point of tile.
     */
    public Coords(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
