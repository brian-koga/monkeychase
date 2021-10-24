package monkeyChase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A Simple Game of Breakout.
 * 
 * The game has four states: StartUp, Playing, and GameOver, LevelOver the game
 * progresses through these states based on the user's input and the events that
 * occur. Each state is different in terms of what is displayed and
 * what input is accepted.
 * 
 * In the playing state, the game displays a moving rectangular "ball" that
 * bounces off the sides of the game container and the blocks that are spawned
 * based on which level. The user controls a paddle at the bottom of the screen.
 * If the ball falls off the bottom of the screen, a life is lost and the ball
 * respawns on top of the paddle. If there are no lives left, the game over state
 * is entered.
 * 
 * When the ball bounces, it appears broken for a short time afterwards and an
 * explosion animation is played at the impact site to add a bit of eye-candy
 * additionally, we play a short explosion sound effect when the game is
 * actively being played.
 * 
 * The game also tracks the number of blocks that have been destroyed and syncs
 * the game update loop with the monitor's refresh rate.
 * 
 * Graphics resources courtesy of qubodup:
 * http://opengameart.org/content/bomb-explosion-animation
 * 
 * Sound resources courtesy of DJ Chronos:
 * http://www.freesound.org/people/DJ%20Chronos/sounds/123236/
 * 
 * 
 * @authors Brian Koga
 * 
 */
public class MonkeyGame extends StateBasedGame {
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int LEVELOVERSTATE = 3;

	public static final String GAMEOVER_BANNER_RSC = "monkeyChase/resource/gameover.png";
	public static final String STARTUP_BANNER_RSC = "monkeyChase/resource/PressSpace.png";

	public static final String Tree_RSC = "monkeyChase/resource/minitree.png";
	//https://opengameart.org/content/minitrees
	public static final String Monkey_RSC = "monkeyChase/resource/Monkey.png";
	public static final String Banana_RSC = "monkeyChase/resource/Banana.png";
	public static final String BananaBunch_RSC = "monkeyChase/resource/BananaBunch.png";
	public static final String Alien_RSC = "monkeyChase/resource/Alien.png";
	public static final String Gorilla_RSC = "monkeyChase/resource/Gorilla.png";




	public final int ScreenWidth;
	public final int ScreenHeight;



	int lives;
	int level;
	int score;

	int tileSize;

	int maxLevel = 3;

	int[][] grid;
	int[][] bananaGrid;
	Tile[][] tileGrid;

	int[][] d;
	Tile[][] pi;

	Monkey monkey1;

	ArrayList<Tree> trees = new ArrayList<>();
	ArrayList<Banana> bananas = new ArrayList<>();

	ArrayList<Alien> aliens = new ArrayList<>();

	Gorilla gorilla;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public MonkeyGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);

		lives = 3;
		level = 0;
		score = 0;
		tileSize = 32;

		d = new int[29][25];
		pi = new Tile[29][25];
		grid = new int[29][25];
		bananaGrid = new int[29][25];
		tileGrid = new Tile[29][25];

		for(int i = 0; i < 29; i++) {
			Arrays.fill(grid[i], 0);
			Arrays.fill(bananaGrid[i], 0);
		}
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		addState(new LevelOverState());
		
		// the sound resource takes a particularly long time to load,
		// we preload it here to (1) reduce latency when we first play it
		// and (2) because loading it will load the audio libraries and
		// unless that is done now, we can't *disable* sound as we
		// attempt to do in the startUp() method.

		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		ResourceManager.loadImage(STARTUP_BANNER_RSC);

		ResourceManager.loadImage(Tree_RSC);
		ResourceManager.loadImage(Monkey_RSC);
		ResourceManager.loadImage(Banana_RSC);
		ResourceManager.loadImage(BananaBunch_RSC);
		ResourceManager.loadImage(Alien_RSC);
		ResourceManager.loadImage(Gorilla_RSC);

		monkey1 = new Monkey(tileSize*14.5f, tileSize*22.5f, 14, 22);

		aliens.add(new Alien(tileSize*2.5f, tileSize*11.5f, 2, 11, 2));
		aliens.add(new Alien(tileSize*26.5f, tileSize*14.5f, 26, 14, 2));

		gorilla = new Gorilla(tileSize*26.5f, tileSize*14.5f, 26, 14, 2);
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new MonkeyGame("Monkey See, Monkey Run!", 928, 800));
			app.setDisplayMode(928, 800, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	
}
