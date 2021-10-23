package monkeyChase;

import jig.Entity;
import jig.ResourceManager;


public class Alien extends Entity {

    int gridX;
    int gridY;
    boolean isCentered;
    char lastMove;
    int stepSize = 1;


    public Alien(final float x, final float y, int tilex, int tiley, int stepSize) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage(MonkeyGame.Alien_RSC));
        isCentered = true;
        gridX = tilex;
        gridY = tiley;
        lastMove = 'X';
        this.stepSize = stepSize;
    }

    public void setTile(int x, int y) {
        gridX = x;
        gridY = y;
        isCentered = true;
    }

    public void setLastMove(char lastMove) {
        this.lastMove = lastMove;
    }
}
