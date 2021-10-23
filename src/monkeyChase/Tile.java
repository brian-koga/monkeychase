package monkeyChase;

import java.util.ArrayList;

public class Tile {
    int x;
    int y;
    ArrayList<Tile> validNeighbors;
    int d;
    boolean valid;

    public Tile(int x, int y, boolean valid) {
        this.x = x;
        this.y = y;
        this.valid = valid;
    }

    public void setNeighbors(ArrayList<Tile> validNeighbors) {
        this.validNeighbors = validNeighbors;
    }

    public void setD(int d) {
        this.d = d;
    }
}
