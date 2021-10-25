package monkeyChase;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;


/**
 * This state is active when the Game is over. In this state, the ball is
 * neither drawn nor updated; and a gameover menu is displayed. Also a timer
 * automatically transitions back to the StartUp State if the user doesn't enter
 * anything for awhile.
 * 
 * Transitions From PlayingState
 * 
 * Transitions To StartUpState, PLayingState
 */
class GameOverState extends BasicGameState {

	int menuItem = 0;
	private int timer;
	private int lastKnownScore; // the user's score, to be displayed, but not updated.
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 40000;

		MonkeyGame mg = (MonkeyGame)game;

	}

	public void setUserScore(int score) {
		lastKnownScore = score;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		MonkeyGame mg = (MonkeyGame)game;
		g.drawString("Score: " + mg.score, 10, 30);

		g.drawImage(ResourceManager.getImage(MonkeyGame.GAMEOVER_BANNER_RSC), mg.ScreenWidth*0.33f,
				mg.ScreenHeight*0.25f);

		// simulate choosing an option, this is based on the menuItem variable
		// it can be 0 or. if it is 0, the start over is focused, if it is 1, main menu is focused
		if(menuItem == 0) {
			if(mg.lives <= 0) {
				g.drawString("< New Game >", mg.ScreenWidth/2 -35, mg.ScreenHeight/2 -20);
			} else {
				g.drawString("< Keep Playing >", mg.ScreenWidth / 2 - 35, mg.ScreenHeight / 2 - 20);
			}
			g.drawString("  Main Menu ", mg.ScreenWidth/2 - 35, (mg.ScreenHeight/2));
		// main menu focused
		} else if(menuItem == 1) {
			if(mg.lives <= 0) {
				g.drawString("  New Game  ", mg.ScreenWidth/2 -35, mg.ScreenHeight/2 -20);
			} else {
				g.drawString("  Keep Playing  ", mg.ScreenWidth / 2 - 35, mg.ScreenHeight / 2 - 20);
			}
			g.drawString("< Main Menu >", mg.ScreenWidth/2 - 35, (mg.ScreenHeight/2));
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		MonkeyGame mg = (MonkeyGame)game;

		// cheats
		if (input.isKeyPressed(Input.KEY_1)) {
			// if lives is 0, reset to 3 and reset the score
			if(mg.lives <= 0) {
				mg.lives = 3;
				mg.score = 0;
			}

			mg.level = 1;
			game.enterState(MonkeyGame.PLAYINGSTATE);
			// start level 2
		} else if (input.isKeyPressed(Input.KEY_2)) {
			// if lives is 0, reset to 3 and reset the score
			if(mg.lives <= 0) {
				mg.lives = 3;
				mg.score = 0;
			}

			mg.level = 2;
			game.enterState(MonkeyGame.PLAYINGSTATE);
			// start level 3
		} /*else if (input.isKeyPressed(Input.KEY_3)) {
			// if lives is 0, reset to 3 and reset the score
			if(mg.lives <= 0) {
				mg.lives = 3;
				mg.score = 0;
			}

			mg.level = 3;
			game.enterState(MonkeyGame.PLAYINGSTATE);
		}
		*/

		// menu item is 0 or 1, either way, the w and s behavior are the same
		if (input.isKeyPressed(Input.KEY_S)) {
			menuItem++;
			if (menuItem == 2) {
				menuItem = 0;
			}
		} else if (input.isKeyPressed(Input.KEY_W)) {
			menuItem--;
			if (menuItem == -1) {
				menuItem = 1;
			}
		} else if (input.isKeyPressed(Input.KEY_SPACE)) {
			// if lives is 0, reset to 3 and reset the score
			if(mg.lives <= 0) {
				mg.lives = 3;
				mg.score = 0;
			}

			// if space is pressed, different things occur
			if(menuItem == 0) {
				// start level 1
				mg.level = 1;
				mg.enterState(MonkeyGame.PLAYINGSTATE);
			} else {
				game.enterState(MonkeyGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition() );
			}
		}
		
		timer -= delta;
		if (timer <= 0)
			game.enterState(MonkeyGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition() );

	}

	@Override
	public int getID() {
		return MonkeyGame.GAMEOVERSTATE;
	}
	
}