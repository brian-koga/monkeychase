package monkeyChase;

import jig.Entity;
import jig.ResourceManager;

public class Tree extends Entity {

    public Tree(final float x, final float y) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage(MonkeyGame.Tree_RSC));
    }
}
