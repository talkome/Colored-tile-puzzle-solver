
/**
 * this class represent the puzzle tiles
 * each tile have color and value
 * @autor Ko tal
 */
public class Tile {

    enum Colors { BLACK, RED, GREEN };

    private Colors color;
    private int value;

    public Tile(int value,Colors color) {
        this.value = value;
        this.color = color;
    }

    public Colors getColor() {
        return this.color;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "(" + this.value + "," + this.color + ")";
    }
}
