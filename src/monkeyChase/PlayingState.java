package monkeyChase;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * This state is active when the Game is being played. In this state, sound is
 * turned on, and the user can control the paddle using the WAS & D keys. If the ball
 * falls out the bottom, a life is deducted and a new ball can be launched from the paddle.
 * If there are no lives left, the game is over. If there are no blocks left, the level is over
 * 
 * Transitions From StartUpState, LevelOverState
 * 
 * Transitions To GameOverState, LevelOverState
 */
class PlayingState extends BasicGameState {
	int levelNumber;

	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {

		container.setSoundOn(true);
		MonkeyGame mg = (MonkeyGame)game;

	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		MonkeyGame mg = (MonkeyGame)game;

		g.drawString("Lives: " + mg.lives, 10, 30);
		g.drawString("Score: " + mg.score, 10, 50);
		g.drawString("Level: " + mg.level, 10, 70);

		float x = 0;
		for(int i = 0; i < 29; i++) {
			g.drawLine(x, 0, x, mg.ScreenHeight);
			x += mg.tileSize;
		}

		float y = 0;
		for(int i = 0; i < 25; i++) {
			g.drawLine(0, y, mg.ScreenWidth, y);
			y += mg.tileSize;
		}

		int c = 0;
		x = mg.tileSize*0.5f;
		y = mg.tileSize*1.5f;
		float bottomY = mg.ScreenHeight - mg.tileSize*1.5f;
		for(int i = 0; i < 28; i++) {
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), x,
					y);
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), x,
					bottomY);
			x += mg.tileSize;
		}

		x = mg.tileSize*0.5f;
		y = mg.tileSize*2.5f;
		float rightX = mg.ScreenWidth - mg.tileSize*1.5f;
		for(int i = 0; i < 22; i++) {
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), x,
					y);
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), rightX,
					y);
			y += mg.tileSize;
		}

		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
				mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
				mg.tileSize*5.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*5.5f,
				mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*4.5f,
				mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
				mg.tileSize*6.5f);


		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
				mg.ScreenHeight-mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*5.5f,
				mg.ScreenHeight-mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*6.5f,
				mg.ScreenHeight-mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
				mg.ScreenHeight-mg.tileSize*5.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
				mg.ScreenHeight-mg.tileSize*6.5f);


		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
				mg.ScreenHeight-mg.tileSize*6.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
				mg.ScreenHeight-mg.tileSize*5.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
				mg.ScreenHeight-mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*4.5f,
				mg.ScreenHeight-mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*5.5f,
				mg.ScreenHeight-mg.tileSize*4.5f);


		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
				mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*5.5f,
				mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*6.5f,
				mg.tileSize*4.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
				mg.tileSize*5.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
				mg.tileSize*6.5f);



		for(int i = 0; i < 4; i++) {
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*(3.5f + i),
					mg.tileSize*9.5f);
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*(3.5f + i),
					mg.tileSize*12.5f);
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*(3.5f + i),
					mg.tileSize*15.5f);

			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*(3.5f + i + 1),
					mg.tileSize*9.5f);
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*(3.5f + i + 1),
					mg.tileSize*12.5f);
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*(3.5f + i + 1),
					mg.tileSize*15.5f);
		}

		for(int i = 0; i < 4; i++) {
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*8.5f,
					mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
					mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
					mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*15.5f,
					mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*18.5f,
					mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*19.5f,
					mg.tileSize*(4.5f+ i));

			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*8.5f,
					mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
					mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
					mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*15.5f,
					mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*18.5f,
					mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*19.5f,
					mg.ScreenHeight-mg.tileSize*(4.5f+ i));
		}

		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
				mg.ScreenHeight-mg.tileSize*7.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
				mg.ScreenHeight-mg.tileSize*8.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*7.5f,
				mg.ScreenHeight-mg.tileSize*7.5f);


		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
				mg.tileSize*7.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
				mg.tileSize*8.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*7.5f,
				mg.tileSize*7.5f);


		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*8.5f,
				mg.tileSize*7.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
				mg.tileSize*8.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
				mg.tileSize*7.5f);


		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*8.5f,
				mg.ScreenHeight-mg.tileSize*7.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
				mg.ScreenHeight-mg.tileSize*8.5f);
		g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
				mg.ScreenHeight-mg.tileSize*7.5f);


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
		//		mg.ScreenHeight-mg.tileSize*8.5f);
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*13.5f,
		//		mg.ScreenHeight-mg.tileSize*8.5f);

		for(int i = 0; i < 5; i++) {
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
					mg.tileSize*(10.5f+i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
					mg.tileSize*(10.5f+i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*13.5f,
					mg.tileSize*(10.5f+i));
			g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*10.5f,
					mg.tileSize*(10.5f+i));
		}

		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
		//		mg.tileSize*14.5f);
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*10.5f,
		//		mg.tileSize*14.5f);




	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		MonkeyGame mg = (MonkeyGame)game;


	}

	@Override
	public int getID() {
		return MonkeyGame.PLAYINGSTATE;
	}
	
}