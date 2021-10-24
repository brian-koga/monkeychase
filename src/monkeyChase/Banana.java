package monkeyChase;

import jig.Entity;
import jig.ResourceManager;


public class Banana extends Entity {
    int gridX;
    int gridY;
    char type;

    public Banana(final float x, final float y, int tilex, int tiley, char type) {
        super(x, y);
        gridX = tilex;
        gridY = tiley;
        // type is either B or S
        this.type = type;
        if(type == 'S') {
            addImageWithBoundingBox(ResourceManager
                    .getImage(MonkeyGame.Banana_RSC));
        } else {
            addImageWithBoundingBox(ResourceManager
                    .getImage(MonkeyGame.BananaBunch_RSC));
        }
    }
}
