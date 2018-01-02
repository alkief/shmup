# Shmup

This is a simple implementation of basic mechanics for a shoot-em-up game
as a project to refresh myself on some Java basics and to learn how to leverage some built in features
(mainly the java.awt.geom package, and some simple graphics). The controls use WASD movement,
and point+click to shoot. You can also reset with 'R'.

At the moment, there are a few points that can be improved upon:

- The main game loop updates state and renders as quickly as possible with an assumed constant change
in time between each update+render. Ideally this should be changed so that the time delta used for
updates reflects the amount of time the previous render took in some way. This, however, brings
additional challenges in maintaining replicability of game state, and investing time into a solid
implementation wasn't my primary focus in creating this project. For more info on this, see:
[this article](https://gafferongames.com/post/fix_your_timestep/).
- The game is resizeable and will scale, however, I'm not entirely confident that
the game behavior will remain the same as the frame resolution changes (namely entity speeds)
- My usage of Graphics could be simplified by leveraging the `fill(Shape s)` function defined
in Graphics2D, but this would require some changes in how Entity data is represented
- You can't die (yet)
- There is no scoring mechanism (yet)
- Enemies don't shoot back (yet)

## Implementation

The game is broken up as follows:

- Engine
	- This is the main controller of the app
	- All updates to entity position, as well as collision detection are triggered by the engine (though
	the corresponding logic is contained within each respective entity class)
	- The main operations used are `integrate()` for triggering state updates using a given timestep,
	and `render()` which simply triggers a `repaint()` of the GameView JPanel.
- Listeners
	- Two listeners for Keyboard (MovementListener) and Mouse (ShootListener) are used
	- The KeyboardListener updates information on player direction based on keypresses
	- The MouseListener sets a boolean to denote the player is firing when pressed, and also sets
	a Point representation of where the player is firing in the PlayerEntity object.
- GameState
	- Somewhat self-explanatory. This class stores all existing entity instances, and exposes
	functions for adding entities as well as retrieving them.
- Entities
	- Entity
		- This class is defined to support necessary operations such as movement and obtaining
		a hitbox for the entity, with the assumption that the entity is represented by a rectangle.
		- Direction of travel is represented using [-1, 0, 1] in both the X and Y directions. This is useful
		because when moving, entity displacement in each direction can simply be multiplied by its direction instead
		of considering direction within the representation of entity speed (velocity)
		- Width and height are defined as a fraction of the root frame size to maintain graphics scalability,
		and functions are used to convert fractions to pixel sizes
	- Player
		- The player is a rectangle, so apart from the addition of a shooting mechanism, the Player
		class uses much of the base	Entity class' logic
		- A boolean is used to denote whether or not the player has their mouse pressed to fire,
		and a Timer within the ShootListener class triggers the player entity's `shoot()` function
		on a fixed interval using the given triggerCooldown variable
	- Enemies
		- Enemies follow basically the same pattern as the player, and so the only thing that needed
		done was to redefine how their hitbox is made since they aren't rectangles but ellipses
	- Projectiles
		- Projectiles gave me a bit of trouble as my implementation of collision detection used
		the Area class. Projectile hitboxes were initially represented using a Line2D, and creating an Area
		using said Line2D yields an empty Area. Thus no intersection would ever occur.
		- I then attempted to make use of the PathIterator available through basic Shapes to construct
		a list of short, straight-line segments along the outside of my entities, then test each line
		segment for	intersection with my projectiles. This was a 'do it just so I could say I did it' attempt, the
		behavior didn't turn out to be as I expected, and rather than spend time forcing a circle into
		a square hole I resolved to	convert my Lines to thin Rectangles (though it should be possible
		to follow the logic I had in mind).
		- After converting my Line representation to Rectangles, I was met with the small challenge of
		rewriting how projectiles were drawn since I couldn't connect two points and be done anymore.
		I needed to make use of an already existing 'angle' variable calculated between	the player's
		location and the location where the cursor was clicked in order to rotate my Graphics context before
		drawing rectangles.
	- GameView
		- This class extends JPanel and holds a reference to the active GameState object which the Engine
		is using. Since entities are basic shapes, each call to `repaint()` simply iterates over all
		entities stored in the game state and uses relevant values (position, height, width) to make images.
		As mentioned before, the most challenging aspect of drawing was rotating the Graphics context
		to correctly display projectiles.

## To run

The project was written using Java v9.0.1 along with Apache Maven, so have these both installed.
I do not believe I used anything available only in Java9 so it may very well run on previous versions.


`cd <directory you want to store the project>`


`git clone <this repo>`


`cd <project root (where pom.xml is)>`


`mvn compile exec:java`

## Screenshots

![pic1](https://i.imgur.com/T8R8zTx.png)