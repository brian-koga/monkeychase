Pacman-like game that has a player controlled character, items to collect, and enemies that chase the player.

Project Readme:
```
Important info for this game

use w, s and space to navigate the menus
use w, s, a and d to control the monkey
there are cheat codes
press 1 to start level 1
press 2 to start level 2
press 9 to go to the game over screen
press 0 while playing to toggle showing the results of dijkstra's that the aliens use to pathfind
    Dijkstra's is only run while play is active, so using this at the beginning of a level or between lives
    will result in an incorrect display


The goal of the game is to collect all of the bananas. Once that is done, the level is complete. There are two levels,
and if both are completed, a game over screen is displayed with the option to keep playing from level 1, or to return
to the main menu. The player has 3 lives, and if all lives are lost, th e game over screen is displayed with the options
to start over at level 1, or to return to the main menu.

Once a level has begun, and after the player has died, press space to start the game and begin movement.

While playing, the player will collect several bunches of bananas. When one of these bunches is collected, a gorilla is
spawned at the players location that chases the aliens away. If the gorilla is currently on the board, the timer is
simply reset. While the gorilla is on the board, the state of the aliens is changed from chasing the player, to
fleeing the gorilla. Dijkstra's is still used for this, but the gorilla's location is used as the source, and the
aliens make the move that takes them further away from the gorilla. Also, while the gorilla is active, the aliens are
not allowed to turn around and move to a tile that they just came from. This is done to prevent an alien from simply
moving back and forth between two tiles.

Note: the aliens are not chasing the player while the gorilla is active, but the player can still die if they come into
contact with one of the aliens.


Low Bar Goals:
Obstacle Filled World with Collectibles : Complete
Player Controlled Entity with Smooth Movement : Complete
AI Entities using Pathfinding to Pursue Player : Complete
Special Item (bunch of banana) : Complete
State-Changed Enemies : Complete

Other Goals:
Intelligent Gorilla : Complete
    Instead of random movements, the gorilla actually uses Dijkstra's to pathfind towards one of the aliens,
    active pursuit rather than simply inspiring the aliens to move away

The original plan was to try and implement the player controlling two monkeys, I decided against that for several
reasons, including size of board, difficulty of play, and behavior of enemies

```

Game comment:
```
The game has four states: StartUp, Playing, GameOver, LevelOver the game
progresses through these states based on the user's input and the events that
occur. Each state is different in terms of what is displayed and
what input is accepted.

In the playing state, the game displays a controllable monkey being chased by
aliens. The player's goal is to eat all of the bananas and avoid being caught
by the aliens. If the aliens catch the player, a life is lost. If there are no
lives left, the game over state is entered.

When the bunch of bananas is eaten a gorilla is summoned at the player's location
that chases the aliens away.

The game also tracks the number of bananas that have been eaten and syncs
the game update loop with the monitor's refresh rate.
```
