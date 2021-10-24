package monkeyChase;

import jig.Entity;
import jig.ResourceManager;

public class Gorilla extends Entity {
    int gridX;
    int gridY;
    boolean isCentered;
    char lastMove;
    int stepSize = 1;
    boolean active = false;
    int timeToInactive = 500;

    public Gorilla(final float x, final float y, int tilex, int tiley, int stepSize) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage(MonkeyGame.Gorilla_RSC));
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
}
