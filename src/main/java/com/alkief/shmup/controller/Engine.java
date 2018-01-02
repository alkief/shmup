package shmup.controller;

import shmup.view.GameView;
import shmup.model.GameState;
import shmup.model.entities.*;

import java.util.Date;
import java.lang.Math;
import java.util.Iterator;
import java.lang.Thread;

public class Engine {

	GameView gameView; // The root panel for displaying all visuals
	GameState gameState; // State information for representing visuals

	public void gameLoop () {
		Date prevTime = new Date(),
				 curTime = new Date();

		double dt;

		// Main update loop
		while (gameState.isPlayerAlive()) {
			dt = 0.1; // Constant change in time regardless of how long rendering take
			// dt = curTime.getTime() - prevTime.getTime(); // Calculate change in time since last update

			integrate(dt); // Update unit positions based on time change
			render(); // Display the current game state

			// Update times
			prevTime = curTime; // Store the time @ start of the previous render
			curTime = new Date(); // Update curTime to represent the start of the next update cycle

			// Manual stop to see each update
			try {
				Thread.sleep(10);
			} catch (Exception e) {

			}
		}
	}

	// Calculate updates to game state
	private void integrate (double dt) {
		// Iterator declarations (use iterators for safe deletion due to concurrency)
		Iterator<EnemyEntity> enemyIterator;
		Iterator<ProjectileEntity> projectileIterator;

		// Update player position
		gameState.getPlayer().move(dt);

		//Iterate over enemy entities (not sure synchronized is needed here)
		synchronized (gameState.getEnemies()) {
			enemyIterator = gameState.getEnemies().iterator();
			while (enemyIterator.hasNext()) {
				EnemyEntity enemy = enemyIterator.next();
				enemy.move(dt); // Update entity position
			}
		}

		// Iterate over projectiles
		synchronized (gameState.getProjectiles()) {
			projectileIterator = gameState.getProjectiles().iterator();
			while (projectileIterator.hasNext()) {
				ProjectileEntity pe = projectileIterator.next(); // The current projectile

				pe.move(dt); // Update position

				// Remove any off-screen projectiles
				if (pe.getPosition().getX() < 0 || pe.getPosition().getY() < 0
						|| pe.getPosition().getX() > gameView.getWidth()
						|| pe.getPosition().getY() > gameView.getHeight()) {
					projectileIterator.remove();
					continue; // Skip to next projectile
				}

				// Check collision of player projectiles against enemies
				if (pe.isPlayerOwned()) {
					enemyIterator = gameState.getEnemies().iterator();
					while (enemyIterator.hasNext()) {
						EnemyEntity enemy = enemyIterator.next();

						if (pe.checkCollision(enemy.getHitBox())) {
							enemyIterator.remove(); // Remove if we have a collision
						}
					}
				}
			}
		}

		// Respawn enemies to max if anyone dies
		while (gameState.getEnemies().size() < gameState.getCurrentOptions().getMaxEnemies()) {
			gameState.spawnEnemyEntity();
		}
	}

	// Draw the current gamestate
	private void render () {
		gameView.repaint();
	}

	public void init () {
		gameState = new GameState(gameView); // Initialize state
		gameView.setState(gameState);

		// Set player to middle of screen
		gameState.getPlayer().setPosition(gameView.getWidth() / 2, gameView.getHeight() / 2);

		render(); // Render UI and starting game state
	}

	public GameState getGameState () {
		return gameState;
	}

	public Engine (GameView gv) {
		gameView = gv; // Store a reference to the View window
	}
}