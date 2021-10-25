package monkeyChase;

import jig.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * This state is active between levels. In this state, sound is
 * turned off. The user can only interact with the game by pressing the
 * SPACE key which allows the user to continue to the next level
 *
 *
 * Transitions From PlayingState
 *
 * Transitions To PlayingState
 */

public class LevelOverState extends BasicGameState{


    private int timer;


    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {

        MonkeyGame mg = (MonkeyGame)game;
        g.drawString("Lives: " + mg.lives, 10, 30);
        g.drawString("Score: " + mg.score, 110, 10);
        g.drawString("Level: " + mg.level, 110, 30);
        g.drawImage(ResourceManager.getImage(MonkeyGame.STARTUP_BANNER_RSC),
                225, 270);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MonkeyGame mg = (MonkeyGame)game;


        if (input.isKeyPressed(Input.KEY_SPACE)) {
            mg.level++;
            mg.enterState(MonkeyGame.PLAYINGSTATE);
        }

    }

    @Override
    public int getID() {
        return MonkeyGame.LEVELOVERSTATE;
    }

}