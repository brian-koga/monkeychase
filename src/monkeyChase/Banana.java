package monkeyChase;

import jig.Entity;
import jig.ResourceManager;


public class Banana extends Entity {
    int gridX;
    int gridY;

    public Banana(final float x, final float y, int tilex, int tiley) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage(MonkeyGame.Banana_RSC));
        gridX = tilex;
        gridY = tiley;
    }
}
