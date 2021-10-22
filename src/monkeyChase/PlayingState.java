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

		for(int i = 2; i< 27; i++) {
			mg.grid[i][3] = 1;
			mg.grid[i][22] = 1;
		}

		for(int i = 4; i < 22; i++) {
			mg.grid[2][i] = 1;
			mg.grid[11][i] = 1;
			mg.grid[14][i] = 1;
			mg.grid[17][i] = 1;
			mg.grid[26][i] = 1;
		}

		for(int i = 8; i < 21; i++) {
			mg.grid[i][9] = 1;
			mg.grid[i][16] = 1;
		}

		for(int i = 3; i < 8; i++) {
			mg.grid[i][11] = 1;
			mg.grid[i][14] = 1;
		}

		for(int i = 21; i < 27; i++) {
			mg.grid[i][11] = 1;
			mg.grid[i][14] = 1;
		}

		for(int i = 3; i < 6; i++) {
			mg.grid[i][8] = 1;
			mg.grid[i+20][8] = 1;
			mg.grid[i][17] = 1;
			mg.grid[i+20][17] = 1;
		}

		for(int i = 10; i < 16; i++) {
			mg.grid[8][i] = 1;
			mg.grid[20][i] = 1;
		}

		for(int i = 4; i < 7; i++) {
			mg.grid[7][i] = 1;
			mg.grid[7][i+15] = 1;
			mg.grid[21][i] = 1;
			mg.grid[21][i + 15] = 1;
		}

		mg.grid[5][6] = 1;
		mg.grid[5][7] = 1;
		mg.grid[6][6] = 1;

		mg.grid[22][6] = 1;
		mg.grid[23][6] = 1;
		mg.grid[23][7] = 1;

		mg.grid[5][18] = 1;
		mg.grid[5][19] = 1;
		mg.grid[6][19] = 1;

		mg.grid[22][19] = 1;
		mg.grid[23][19] = 1;
		mg.grid[23][18] = 1;

		// create Trees
		int c = 0;
		float x = mg.tileSize;
		float y = mg.tileSize*2f;
		float bottomY = mg.ScreenHeight - mg.tileSize;
		for(int i = 0; i < 28; i++) {
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), x,
			//		y);
			mg.trees.add(new Tree(x, y));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), x,
			//		bottomY);
			mg.trees.add(new Tree(x, bottomY));
			x += mg.tileSize;
		}

		x = mg.tileSize;
		y = mg.tileSize*3f;
		float rightX = mg.ScreenWidth - mg.tileSize;
		for(int i = 0; i < 21; i++) {
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), x,
			//		y);
			mg.trees.add(new Tree(x, y));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), rightX,
			//		y);
			mg.trees.add(new Tree(rightX, y));
			y += mg.tileSize;
		}

		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
		//		mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.tileSize*4f, mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
		//		//mg.tileSize*5.5f);
		mg.trees.add(new Tree(mg.tileSize*4f, mg.tileSize*6f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*5.5f,
		//		mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.tileSize*6f, mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*4.5f,
		//		mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.tileSize*5f, mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
		//		mg.tileSize*6.5f);
		mg.trees.add(new Tree(mg.tileSize*4f, mg.tileSize*7f));


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
		//		mg.ScreenHeight-mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*4f,mg.ScreenHeight-mg.tileSize*4f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*5.5f,
		//		mg.ScreenHeight-mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*5f,mg.ScreenHeight-mg.tileSize*4f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*6.5f,
		//		mg.ScreenHeight-mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*6f,mg.ScreenHeight-mg.tileSize*4f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
		//		mg.ScreenHeight-mg.tileSize*5.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*4f,mg.ScreenHeight-mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
		//		mg.ScreenHeight-mg.tileSize*6.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*4f,mg.ScreenHeight-mg.tileSize*6f));


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
		//		mg.ScreenHeight-mg.tileSize*6.5f);
		mg.trees.add(new Tree(mg.tileSize*4f,mg.ScreenHeight-mg.tileSize*6f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
		//		mg.ScreenHeight-mg.tileSize*5.5f);
		mg.trees.add(new Tree(mg.tileSize*4f,mg.ScreenHeight-mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*3.5f,
		//		mg.ScreenHeight-mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.tileSize*4f,mg.ScreenHeight-mg.tileSize*4f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*4.5f,
		//		mg.ScreenHeight-mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.tileSize*5f,mg.ScreenHeight-mg.tileSize*4f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*5.5f,
		//		mg.ScreenHeight-mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.tileSize*6f,mg.ScreenHeight-mg.tileSize*4f));


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
		//		mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*4f,mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*5.5f,
		//		mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*5f,mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*6.5f,
		//		mg.tileSize*4.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*6f,mg.tileSize*5f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
		//		mg.tileSize*5.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*4f,mg.tileSize*6f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*4.5f,
		//		mg.tileSize*6.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*4f,mg.tileSize*7f));



		for(int i = 0; i < 4; i++) {
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*(3.5f + i),
			//		mg.tileSize*9.5f);
			mg.trees.add(new Tree(mg.tileSize*(4f + i),mg.tileSize*10f));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*(3.5f + i),
			//		mg.tileSize*12.5f);
			mg.trees.add(new Tree(mg.tileSize*(4f + i),mg.tileSize*13f));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*(3.5f + i),
			//		mg.tileSize*15.5f);
			mg.trees.add(new Tree(mg.tileSize*(4f + i),mg.tileSize*16f));

			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*(3.5f + i + 1),
			//		mg.tileSize*9.5f);
			mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*(3f + i + 1),mg.tileSize*10f));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*(3.5f + i + 1),
			//		mg.tileSize*12.5f);
			mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*(3f + i + 1),mg.tileSize*13f));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*(3.5f + i + 1),
			//		mg.tileSize*15.5f);
			mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*(3f + i + 1),mg.tileSize*16f));
		}

		for(int i = 0; i < 4; i++) {
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*8.5f,
			//		mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*9f,mg.tileSize*(5f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
			//		mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*10f,mg.tileSize*(5f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
			//		mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*13f,mg.tileSize*(5f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*15.5f,
			//		mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*16f,mg.tileSize*(5f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*18.5f,
			//		mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*19f,mg.tileSize*(5f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*19.5f,
			//		mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*20f,mg.tileSize*(5f+ i)));

			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*8.5f,
			//		mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*9f,mg.ScreenHeight-mg.tileSize*(4f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
			//		mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*10f,mg.ScreenHeight-mg.tileSize*(4f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
			//		mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*13f,mg.ScreenHeight-mg.tileSize*(4f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*15.5f,
			//		mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*16f,mg.ScreenHeight-mg.tileSize*(4f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*18.5f,
			//		mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*19f,mg.ScreenHeight-mg.tileSize*(4f+ i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*19.5f,
			//		mg.ScreenHeight-mg.tileSize*(4.5f+ i));
			mg.trees.add(new Tree(mg.tileSize*20f,mg.ScreenHeight-mg.tileSize*(4f+ i)));
		}

		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
		//		mg.ScreenHeight-mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.tileSize*7f,mg.ScreenHeight-mg.tileSize*7f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
		//		mg.ScreenHeight-mg.tileSize*8.5f);
		mg.trees.add(new Tree(mg.tileSize*7f,mg.ScreenHeight-mg.tileSize*8f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*7.5f,
		//		mg.ScreenHeight-mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.tileSize*8f,mg.ScreenHeight-mg.tileSize*7f));


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
		//		mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.tileSize*7f,mg.tileSize*8f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*6.5f,
		//		mg.tileSize*8.5f);
		mg.trees.add(new Tree(mg.tileSize*7f,mg.tileSize*9f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*7.5f,
		//		mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.tileSize*8f,mg.tileSize*8f));


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*8.5f,
		//		mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*8f,mg.tileSize*8f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
		//		mg.tileSize*8.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*7f,mg.tileSize*9f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
		//		mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*7f,mg.tileSize*8f));


		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*8.5f,
		//		mg.ScreenHeight-mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*8f,mg.ScreenHeight-mg.tileSize*7f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
		//		mg.ScreenHeight-mg.tileSize*8.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*7f,mg.ScreenHeight-mg.tileSize*8f));
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*7.5f,
		//		mg.ScreenHeight-mg.tileSize*7.5f);
		mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*7f,mg.ScreenHeight-mg.tileSize*7f));



		for(int i = 0; i < 5; i++) {
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*12.5f,
			//		mg.tileSize*(10.5f+i));
			mg.trees.add(new Tree(mg.tileSize*13f,mg.tileSize*(11f+i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.tileSize*9.5f,
			//		mg.tileSize*(10.5f+i));
			mg.trees.add(new Tree(mg.tileSize*10f,mg.tileSize*(11f+i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*13.5f,
			//		mg.tileSize*(10.5f+i));
			mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*13f,mg.tileSize*(11f+i)));
			//g.drawImage(ResourceManager.getImage(MonkeyGame.Tree_RSC), mg.ScreenWidth-mg.tileSize*10.5f,
			//		mg.tileSize*(10.5f+i));
			mg.trees.add(new Tree(mg.ScreenWidth-mg.tileSize*10f,mg.tileSize*(11f+i)));
		}

	}

	public void tryMoveLeft(MonkeyGame mg, int x1, int y1, boolean nearlyCenteredVertically) {
		// see if monkey is currently moving this direction or the opposite or stopped
		if(mg.monkey1.lastMove == 'L' || mg.monkey1.lastMove == 'R' || mg.monkey1.lastMove == 'X') {
			// last move was left or it was stopped
			if(mg.monkey1.getX()%32 != 16) {
				//not in the center of a tile
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
			} else if(mg.grid[x1-1][y1] == 1) {
				// can move left
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
			}
		// haven't been moving left, but now want to, check tile to monkey's left
		} else if(mg.grid[x1-1][y1] == 1 && nearlyCenteredVertically) {
			// can move left in the grid
			float m1YMod32 = mg.monkey1.getY()%32;
			System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move L");
			float diff = 16.0f - m1YMod32;
			if(diff == 0) {
				// it is centered
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
			} else if (diff < 0) {
				// add moving left to the planned moves
				mg.monkey1.plannedMoves.push('L');

				// to be centered, need to move up one step
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
				diff += mg.monkey1.stepSize;

				// push the necessary number of moves
				while(diff < 0) {
					mg.monkey1.plannedMoves.push('U');
					diff += mg.monkey1.stepSize;
				}
			} else { // greater than 0
				// add moving left to the planned moves
				mg.monkey1.plannedMoves.push('L');

				// to be centered, need to move down one step
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('D');
					diff -= mg.monkey1.stepSize;
				}
			}
			/*
			if(Math.abs(16.0f - m1YMod32) != 0) {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize*(x1 + 0.5f),
						mg.tileSize*(y1 + 0.5f)));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.movementLocked = true;
			} else {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize * (x1 + 0.5f) - 2,
						mg.tileSize * (y1 + 0.5f)));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
			}

			 */
			//update tile
			// has entered the tile to the left
			mg.monkey1.setTile(x1-1, y1);
		}
	}

	public void tryMoveRight(MonkeyGame mg, int x1, int y1, boolean nearlyCenteredVertically) {
		// see if monkey is currently moving this direction or stopped
		if(mg.monkey1.lastMove == 'R' || mg.monkey1.lastMove == 'L' || mg.monkey1.lastMove == 'X') {
			// last move was right or it was stopped
			if(mg.monkey1.getX()%32 != 16) {
				//not in the center of a tile
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
			} else if(mg.grid[x1+1][y1] == 1) {
				// can move right
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
			}
			// haven't been moving right, but now want to, check tile to monkey's left
		} else if(mg.grid[x1+1][y1] == 1 && nearlyCenteredVertically) {
			// can move right in the grid
			float m1YMod32 = mg.monkey1.getY()%32;
			System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move R");
			float diff = 16.0f - m1YMod32;
			if(diff == 0) {
				// it is centered
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
			} else if (diff < 0) {
				// add moving right to the planned moves
				mg.monkey1.plannedMoves.push('R');

				// to be centered, need to move up one step
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
				diff += mg.monkey1.stepSize;

				while(diff < 0) {
					mg.monkey1.plannedMoves.push('U');
					diff += mg.monkey1.stepSize;
				}
			} else { // greater than 0
				// add moving right to the planned moves
				mg.monkey1.plannedMoves.push('R');

				// to be centered, need to move down one step
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('D');
					diff -= mg.monkey1.stepSize;
				}
			}
			/*
			if(Math.abs(16.0f - m1YMod32) != 0) {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize*(x1 + 0.5f),
						mg.tileSize*(y1 + 0.5f)));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.movementLocked = true;
			} else {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize * (x1 + 0.5f) + 2,
						mg.tileSize * (y1 + 0.5f)));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
			}

			 */
			//update tile
			// has entered the tile to the right
			mg.monkey1.setTile(x1+1, y1);
			//mg.monkey1.setLastMove('R');
		}
	}

	public void tryMoveUp(MonkeyGame mg, int x1, int y1, boolean nearlyCenteredHorizontally) {
		// see if monkey is currently moving this direction or stopped
		if(mg.monkey1.lastMove == 'U' || mg.monkey1.lastMove == 'D' || mg.monkey1.lastMove == 'X') {
			// last move was right or it was stopped
			if(mg.monkey1.getY()%32 != 16) {
				//not in the center of a tile
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
			} else if(mg.grid[x1][y1-1] == 1) {
				// can move up
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
			}
			// haven't been moving right, but now want to, check tile to monkey's left
		} else if(mg.grid[x1][y1-1] == 1 && nearlyCenteredHorizontally) {
			// can move down in the grid
			float m1XMod32 = mg.monkey1.getX()%32;
			System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move U");
			float diff = 16.0f - m1XMod32;
			if(diff == 0) {
				// it is centered
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
			} else if (diff < 0) {
				// add moving up to the planned moves
				mg.monkey1.plannedMoves.push('U');

				// to be centered, need to move left
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
				diff += mg.monkey1.stepSize;

				while(diff < 0) {
					mg.monkey1.plannedMoves.push('L');
					diff += mg.monkey1.stepSize;
				}
			} else { // greater than 0
				// add moving up to the planned moves
				mg.monkey1.plannedMoves.push('U');

				// to be centered, need to move right
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('R');
					diff -= mg.monkey1.stepSize;
				}
			}


			/*
			if(Math.abs(16.0f - m1XMod32) != 0) {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize*(x1 + 0.5f),
						mg.tileSize*(y1 + 0.5f)));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.movementLocked = true;
			} else {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize * (x1 + 0.5f),
						mg.tileSize * (y1 + 0.5f) - 2));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
			}

			 */
			//update tile
			// has entered the tile to the bottom
			mg.monkey1.setTile(x1, y1-1);
			//mg.monkey1.setLastMove('U');
		}
	}

	public void tryMoveDown(MonkeyGame mg, int x1, int y1, boolean nearlyCenteredHorizontally) {
		// see if monkey is currently moving this direction or stopped
		if(mg.monkey1.lastMove == 'D' || mg.monkey1.lastMove == 'U' || mg.monkey1.lastMove == 'X') {
			// last move was right or it was stopped
			if(mg.monkey1.getY()%32 != 16) {
				//not in the center of a tile
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
			} else if(mg.grid[x1][y1+1] == 1) {
				// can move down
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
			}
			// haven't been moving right, but now want to, check tile to monkey's left
		} else if(mg.grid[x1][y1+1] == 1 && nearlyCenteredHorizontally) {
			// can move down in the grid
			//mg.monkey1.translate(-2, 0);
			float m1XMod32 = mg.monkey1.getX()%32;
			System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move D");
			float diff = 16.0f - m1XMod32;
			if(diff == 0) {
				// it is centered
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
			} else if (diff < 0) {
				// add moving up to the planned moves
				mg.monkey1.plannedMoves.push('D');

				// to be centered, need to move left
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
				diff += mg.monkey1.stepSize;

				while(diff < 0) {
					mg.monkey1.plannedMoves.push('L');
					diff += mg.monkey1.stepSize;
				}
			} else { // greater than 0
				// add moving down to the planned moves
				mg.monkey1.plannedMoves.push('D');

				// to be centered, need to move right
				System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('R');
					diff -= mg.monkey1.stepSize;
				}
			}
			/*
			if(Math.abs(16.0f - m1XMod32) != 0) {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize*(x1 + 0.5f),
						mg.tileSize*(y1 + 0.5f)));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.movementLocked = true;
			} else {
				System.out.println("before set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.setPosition(new Vector(mg.tileSize * (x1 + 0.5f),
						mg.tileSize * (y1 + 0.5f) + 2));
				System.out.println("after set position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
			}

			 */
			//update tile
			// has entered the tile to the bottom
			mg.monkey1.setTile(x1, y1+1);
			//mg.monkey1.setLastMove('D');
		}
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		MonkeyGame mg = (MonkeyGame)game;

		g.drawString("Lives: " + mg.lives, 10, 30);
		g.drawString("Score: " + mg.score, 110, 10);
		g.drawString("Level: " + mg.level, 110, 30);

		float x = 0;
		float y = 0;


	/*

		for(int i = 0; i < 29; i++) {
			g.drawLine(x, 0, x, mg.ScreenHeight);
			x += mg.tileSize;
		}


		for(int i = 0; i < 25; i++) {
			g.drawLine(0, y, mg.ScreenWidth, y);
			y += mg.tileSize;
		}

	 */







		// render monkey
		float drawX = (mg.monkey1.gridX -1)+ 0.5f;
		float drawY = (mg.monkey1.gridY -1) + 0.5f;
		//g.drawImage(ResourceManager.getImage(MonkeyGame.Monkey_RSC), mg.tileSize*drawX, mg.tileSize*drawY);

		g.drawImage(ResourceManager.getImage(MonkeyGame.Alien_RSC), mg.tileSize*13.5f, mg.tileSize*5.5f);

		mg.monkey1.render(g);

		//g.drawImage(ResourceManager.getImage(MonkeyGame.Banana_RSC), mg.tileSize*7.25f, mg.ScreenHeight-mg.tileSize*2.75f);

		// draw trees
		for(Tree temp : mg.trees) {
			temp.render(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		MonkeyGame mg = (MonkeyGame)game;

		boolean movementMade = false;

		int x1 = mg.monkey1.gridX;
		int y1 = mg.monkey1.gridY;

		// will treat horizontal and vertical movement separately

		// Horizontal movement

		boolean nearlyCenteredHorizontally = false;

		// if within 2 pixels of either side of center
		float m1XMod32 = mg.monkey1.getX()%32;
		if(Math.abs(16.0f - m1XMod32) <= 8) {
			nearlyCenteredHorizontally = true;
		}

		// Vertical movement
		boolean nearlyCenteredVertically = false;

		// if within 2 pixels of either side of center
		float m1YMod32 = mg.monkey1.getY()%32;
		if(Math.abs(16.0f - m1YMod32) <= 8) {
			nearlyCenteredVertically = true;
		}

		/*
		if(mg.monkey1.movementLocked) {
			if(mg.monkey1.lastMove == 'L') {
				mg.monkey1.translate(-2, 0);
			} else if(mg.monkey1.lastMove == 'R') {
				mg.monkey1.translate(2, 0);
			} else if(mg.monkey1.lastMove == 'U') {
				mg.monkey1.translate(0, -2);
			} else {
				mg.monkey1.translate(0, 2);
			}
			mg.monkey1.movementLocked = false;

		 */
		if(!mg.monkey1.plannedMoves.isEmpty()) {
			// moves are planned, need to execute them
			char moveToMake = mg.monkey1.plannedMoves.pop();
			if(moveToMake == 'L') {
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.lastMove = 'L';
			} else if(moveToMake == 'R') {
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.lastMove = 'R';
			} else if(moveToMake == 'U') {
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.lastMove = 'U';
			} else {
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.lastMove = 'D';
			}
		} else {

			// in the case of multiple keys being held, want to handle one that is the same
			// axis as current movement
			// left right axis
			if (mg.monkey1.lastMove == 'U' || mg.monkey1.lastMove == 'D') {
				if (input.isKeyDown(Input.KEY_W)) {
					tryMoveUp(mg, x1, y1, nearlyCenteredHorizontally);
				} else if (input.isKeyDown(Input.KEY_S)) {
					tryMoveDown(mg, x1, y1, nearlyCenteredHorizontally);
				} else if (input.isKeyDown(Input.KEY_A)) {
					tryMoveLeft(mg, x1, y1, nearlyCenteredVertically);
				} else if (input.isKeyDown(Input.KEY_D)) {
					tryMoveRight(mg, x1, y1, nearlyCenteredVertically);
				}
				// up down axis
			} else {
				if (input.isKeyDown(Input.KEY_A)) {
					tryMoveLeft(mg, x1, y1, nearlyCenteredVertically);
				} else if (input.isKeyDown(Input.KEY_D)) {
					tryMoveRight(mg, x1, y1, nearlyCenteredVertically);
				} else if (input.isKeyDown(Input.KEY_W)) {
					tryMoveUp(mg, x1, y1, nearlyCenteredHorizontally);
				} else if (input.isKeyDown(Input.KEY_S)) {
					tryMoveDown(mg, x1, y1, nearlyCenteredHorizontally);
				}
			}
		}

		// update monkey
		mg.monkey1.setTile((int)Math.floor(mg.monkey1.getX()/32), (int) Math.floor(mg.monkey1.getY()/32));


	}

	@Override
	public int getID() {
		return MonkeyGame.PLAYINGSTATE;
	}
	
}