package monkeyChase;

import java.util.Iterator;

import jig.ResourceManager;

import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This state is active prior to the Game starting. The user can only interact
 * with the game by pressing the w, s or SPACE keys which allows the selection
 * of a level, then transitions to the Playing State.
 *
 * 
 * Transitions From (Initialization), GameOverState
 * 
 * Transitions To PlayingState
 */

class StartUpState extends BasicGameState {
	int menuItem = 0;
	int levelMenuItem = 1;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);

		MonkeyGame mg = (MonkeyGame)game;

		mg.lives = 3;
		mg.score = 0;
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		MonkeyGame mg = (MonkeyGame)game;


		g.drawString("Brian Koga", 10, mg.ScreenHeight -20);

		g.drawImage(ResourceManager.getImage(MonkeyGame.Title_RSC), 164,
				mg.ScreenHeight*0.15f);



		// simulate choosing an option, this is based on the menuItem variable
		// it can be 0, 1, or 2. if it is 0, the start is focused, if it is 1, the level is focused
		// and if it is 2, then the person is choosing levels

		if(menuItem == 0) {
			g.drawString("< Start >", mg.ScreenWidth/2 -30, mg.ScreenHeight/2 -20);
			g.drawString("  Levels ", mg.ScreenWidth/2 - 30, (mg.ScreenHeight/2 + 20));
		// levels focused
		} else if(menuItem == 1) {
			g.drawString("  Start  ", mg.ScreenWidth / 2 - 30, mg.ScreenHeight / 2 - 20);
			g.drawString("< Levels >", mg.ScreenWidth/2 - 30, (mg.ScreenHeight/2 + 20));
		// in the level select menu
		} else if (menuItem == 2) {
			// level 1 focused
			if (levelMenuItem == 1) {
				g.drawString("< Level 1 >", mg.ScreenWidth / 2 - 30, mg.ScreenHeight / 2 - 20);
				g.drawString("  Level 2  ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2));
				//g.drawString("  Level 3  ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
				g.drawString("   Back   ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
				// level 2 focused
			} else if (levelMenuItem == 2) {
				g.drawString("  Level 1  ", mg.ScreenWidth / 2 - 30, mg.ScreenHeight / 2 - 20);
				g.drawString("< Level 2 >", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2));
				//g.drawString("  Level 3  ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
				g.drawString("   Back   ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
				// level 3 focused
			/*} else if (levelMenuItem == 3) {
				g.drawString("  Level 1  ", mg.ScreenWidth / 2 - 30, mg.ScreenHeight / 2 - 20);
				g.drawString("  Level 2  ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2));
				g.drawString("< Level 3 >", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
				g.drawString("   Back   ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 40));
				*/
			} else if (levelMenuItem == 0) {
				g.drawString("  Level 1  ", mg.ScreenWidth / 2 - 30, mg.ScreenHeight / 2 - 20);
				g.drawString("  Level 2  ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2));
				//g.drawString("  Level 3  ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
				g.drawString(" < Back > ", mg.ScreenWidth / 2 - 30, (mg.ScreenHeight / 2 + 20));
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		MonkeyGame mg = (MonkeyGame)game;

		// cheats
		if (input.isKeyPressed(Input.KEY_1)) {
			mg.level = 1;
			game.enterState(MonkeyGame.PLAYINGSTATE);
			// start level 2
		} else if (input.isKeyPressed(Input.KEY_2)) {
			mg.level = 2;
			game.enterState(MonkeyGame.PLAYINGSTATE);
			// start level 3
		/*} else if (input.isKeyPressed(Input.KEY_3)) {
			mg.level = 3;
			game.enterState(MonkeyGame.PLAYINGSTATE);

		 */
		}


		// in the level select menu
		// here, the options are which level to start on and a back
		if(menuItem == 2) {
			// move the selector down
			if (input.isKeyPressed(Input.KEY_S)) {
				levelMenuItem++;
				if (levelMenuItem == 3) {
					levelMenuItem = 0;
				}
			// move the selector up
			} else if (input.isKeyPressed(Input.KEY_W)) {
				levelMenuItem--;
				if (levelMenuItem == -1) {
					levelMenuItem = 2;
				}
			// choose an option
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				// if on back
				if(levelMenuItem == 0) {
					// change back to choosing between start and levels
					menuItem = 0;
					// set levelMenuItem to 1 so that level one is selected next time
					levelMenuItem = 1;
				} else {
					// if not on back then want to start the level they were highlighting
					mg.level = levelMenuItem;
					mg.enterState(MonkeyGame.PLAYINGSTATE);
				}
			}
		} else {
			// menu item is 0 or 1 or 2, either way, the w and s behavior are the same
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
				// if space is pressed, different things occur
				if(menuItem == 0) {
					// start level 1
					mg.level = 1;
					mg.enterState(MonkeyGame.PLAYINGSTATE);
				} else if(menuItem == 1){
					// enter level select menu
					menuItem = 2;
				} else {
					// start demo
					mg.level = 1;
				}
			}
		}

	}

	@Override
	public int getID() {
		return MonkeyGame.STARTUPSTATE;
	}
	
}