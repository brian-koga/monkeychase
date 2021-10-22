package monkeyChase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.util.Scanner;

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

		// setup level
		levelNumber = mg.level;

		if(levelNumber == 1) {
			setupLevel1(mg);
		}

		// create Trees and Bananas
		for(int i = 0; i < 28; i++) {
			for(int j = 0; j < 24; j++) {
				// add trees
				if(mg.grid[i][j] == 0 && mg.grid[i+1][j] == 0 && mg.grid[i][j+1] == 0 && mg.grid[i+1][j+1] == 0) {
					mg.trees.add(new Tree(mg.tileSize*(i+1), mg.tileSize*(j+1)));
				}
				// add bananas
				if(mg.bananaGrid[i][j] == 1) {
					// don't want to add where the monkey spawns
					if(!((i == 13 && j == 22) || (i == 14 && j == 22) || (i == 15 && j == 22) || (i == 14 && j == 21)))
						mg.bananas.add(new Banana(mg.tileSize*(i + 0.5f), mg.tileSize*(j+ 0.5f), i, j));
				}
			}
		}

	}

	public void setupLevel1(MonkeyGame mg) {
		try {
			File f = new File("monkeyChase/src/monkeyChase/resource/Level1.txt");
			Scanner scan = new Scanner(f);
			int j = 0;
			while (scan.hasNextLine()) {
				String data = scan.nextLine();
				for(int i = 0; i < data.length(); i++) {
					if(data.charAt(i) == '-') {
						// should only be top row
						mg.grid[i][j] = -1;
						mg.bananaGrid[i][j] = -1;
					} else if(data.charAt(i) == '0') {
						// signify wall tiles
						mg.grid[i][j] = 0;
						mg.bananaGrid[i][j] = 0;
					} else if(data.charAt(i) == '1') {
						// tiles that are open and have a banana
						mg.grid[i][j] = 1;
						mg.bananaGrid[i][j] = 1;
					} else if(data.charAt(i) == '2') {
						// tiles that are open and have a banana bunch
						mg.grid[i][j] = 1;
						mg.bananaGrid[i][j] = 2;
					} else {
						// something has gone wrong
						System.out.println("Unknown character encountered in level file.");
					}
				}
				j++;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void tryMoveLeft(MonkeyGame mg, int x1, int y1, boolean nearlyCenteredVertically) {
		// see if monkey is currently moving this direction or the opposite or stopped
		if(mg.monkey1.lastMove == 'L' || mg.monkey1.lastMove == 'R' || mg.monkey1.lastMove == 'X') {
			// last move was left or it was stopped
			if(mg.monkey1.getX()%32 != 16) {
				//not in the center of a tile
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
			} else if(mg.grid[x1-1][y1] == 1) {
				// can move left
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
			}
		// haven't been moving left, but now want to, check tile to monkey's left
		} else if(mg.grid[x1-1][y1] == 1 && nearlyCenteredVertically) {
			// can move left in the grid
			float m1YMod32 = mg.monkey1.getY()%32;
			//System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move L");
			float diff = 16.0f - m1YMod32;
			if(diff == 0) {
				// it is centered
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(-1*mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('L');
			} else if (diff < 0) {
				// add moving left to the planned moves
				mg.monkey1.plannedMoves.push('L');

				// to be centered, need to move up one step
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('D');
					diff -= mg.monkey1.stepSize;
				}
			}
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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
			} else if(mg.grid[x1+1][y1] == 1) {
				// can move right
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
			}
			// haven't been moving right, but now want to, check tile to monkey's left
		} else if(mg.grid[x1+1][y1] == 1 && nearlyCenteredVertically) {
			// can move right in the grid
			float m1YMod32 = mg.monkey1.getY()%32;
			//System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move R");
			float diff = 16.0f - m1YMod32;
			if(diff == 0) {
				// it is centered
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
			} else if (diff < 0) {
				// add moving right to the planned moves
				mg.monkey1.plannedMoves.push('R');

				// to be centered, need to move up one step
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('D');
					diff -= mg.monkey1.stepSize;
				}
			}
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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
			} else if(mg.grid[x1][y1-1] == 1) {
				// can move up
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
			}
			// haven't been moving right, but now want to, check tile to monkey's left
		} else if(mg.grid[x1][y1-1] == 1 && nearlyCenteredHorizontally) {
			// can move down in the grid
			float m1XMod32 = mg.monkey1.getX()%32;
			//System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move U");
			float diff = 16.0f - m1XMod32;
			if(diff == 0) {
				// it is centered
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, -1*mg.monkey1.stepSize);
				mg.monkey1.setLastMove('U');
			} else if (diff < 0) {
				// add moving up to the planned moves
				mg.monkey1.plannedMoves.push('U');

				// to be centered, need to move left
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('R');
					diff -= mg.monkey1.stepSize;
				}
			}

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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
			} else if(mg.grid[x1][y1+1] == 1) {
				// can move down
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
			}
			// haven't been moving right, but now want to, check tile to monkey's left
		} else if(mg.grid[x1][y1+1] == 1 && nearlyCenteredHorizontally) {
			// can move down in the grid
			//mg.monkey1.translate(-2, 0);
			float m1XMod32 = mg.monkey1.getX()%32;
			//System.out.println("Moving " + mg.monkey1.lastMove + ", trying to move D");
			float diff = 16.0f - m1XMod32;
			if(diff == 0) {
				// it is centered
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(0, mg.monkey1.stepSize);
				mg.monkey1.setLastMove('D');
			} else if (diff < 0) {
				// add moving up to the planned moves
				mg.monkey1.plannedMoves.push('D');

				// to be centered, need to move left
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
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
				//System.out.println("current position:" + mg.monkey1.getX() + ", " + mg.monkey1.getY());
				mg.monkey1.translate(mg.monkey1.stepSize, 0);
				mg.monkey1.setLastMove('R');
				diff -= mg.monkey1.stepSize;

				while(diff > 0) {
					mg.monkey1.plannedMoves.push('R');
					diff -= mg.monkey1.stepSize;
				}
			}
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




		for(int i = 0; i < 29; i++) {
			g.drawLine(x, 0, x, mg.ScreenHeight);
			x += mg.tileSize;
		}


		for(int i = 0; i < 25; i++) {
			g.drawLine(0, y, mg.ScreenWidth, y);
			y += mg.tileSize;
		}




		//g.drawImage(ResourceManager.getImage(MonkeyGame.Alien_RSC), mg.tileSize*13.5f, mg.tileSize*5.5f);

		//draw bananas
		for(Banana temp : mg.bananas) {
			temp.render(g);
		}

		// draw monkey
		mg.monkey1.render(g);

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

		// if there are moves that are 'planned' execute them first, if not, allow input
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

		// check bananas
		for (Iterator<Banana> i = mg.bananas.iterator(); i.hasNext();) {
			Banana temp = i.next();
			if (mg.monkey1.gridX == temp.gridX && mg.monkey1.gridY == temp.gridY) {
				mg.score++;
				i.remove();
			}
		}

		//if(mg.bananas.isEmpty()) {
			// end game
		//	mg.monkey1.stepSize = 0;
		//}

	}

	@Override
	public int getID() {
		return MonkeyGame.PLAYINGSTATE;
	}
	
}