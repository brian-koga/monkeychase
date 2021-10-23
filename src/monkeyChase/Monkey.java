package monkeyChase;

import jig.Entity;
import jig.ResourceManager;
import java.util.Stack;

public class Monkey extends Entity {
    int gridX;
    int gridY;
    boolean isCentered;
    char lastMove;
    Stack<Character> plannedMoves;
    int stepSize;


    public Monkey(final float x, final float y, int tilex, int tiley) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage(MonkeyGame.Monkey_RSC));
        isCentered = true;
        gridX = tilex;
        gridY = tiley;
        lastMove = 'X';
        plannedMoves = new Stack<Character>();
        stepSize = 2;
    }

    public void update(final int delta) {
        //translate(velocity.scale(delta));
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
