package shmup.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.lang.Math;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import shmup.model.entities.*;
import shmup.view.GameView;
import shmup.model.GameOptions;

public class GameState {
	PlayerEntity p;
	boolean playerAlive;

	GameView gameView;
	GameOptions options;

	List<ProjectileEntity> projectiles = Collections.synchronizedList(new ArrayList<ProjectileEntity>());
	List<EnemyEntity> enemies = new ArrayList<EnemyEntity>();

	public GameState (GameView gv) {
		gameView = gv;
		options = GameOptions.getDefaultOptions();
		// Create player entity
		p = new PlayerEntity(gameView, this);
		playerAlive = true;

		// Create enemy entities
		while (enemies.size() < options.getMaxEnemies()) {
			spawnEnemyEntity();
		}
	}

	public void spawnEnemyEntity () {
		double x, y; // Entity position coordinates
		EnemyEntity enemy = new EnemyEntity(gameView, this);

		// X position will be outside board
		if (ThreadLocalRandom.current().nextBoolean()) {

			if (ThreadLocalRandom.current().nextBoolean()) { // Left of board
				x = ThreadLocalRandom.current().nextDouble(-100.0, 0);
			} else { // Right of board
				x = ThreadLocalRandom.current().nextDouble(gameView.getWidth(), gameView.getWidth() + 100);
			}
			// Random Y within board height bounds
			y = ThreadLocalRandom.current().nextDouble(0, gameView.getHeight() + 1);
		// Y position will be outside the board
		} else {

			if (ThreadLocalRandom.current().nextBoolean()) { // Above board
				y = ThreadLocalRandom.current().nextDouble(-100.0, 0);
			} else { // Below board
				y = ThreadLocalRandom.current().nextDouble(gameView.getHeight(), gameView.getHeight() + 100);
			}

			// Random X within board width bounds
			x = ThreadLocalRandom.current().nextDouble(0, gameView.getWidth() + 1);

		}

		enemy.setPosition(x, y);
		enemies.add(enemy);
	}

	public void addPlayerProjectile (Point2D origin, Point2D target) {
		addProjectile(origin, target, true);
	}

	public void addEnemyProjectile (Point2D origin, Point2D target) {
		addProjectile(origin, target, false);
	}

	private void addProjectile (Point2D origin, Point2D target, boolean isPlayerProjectile) {
		double dx = origin.getX() - target.getX(),
					 dy = origin.getY() - target.getY(),
					 angle = Math.atan(dy / dx);

		// For leftward movement
		if (target.getX() < origin.getX()) {
			angle += Math.PI;
		}

		projectiles.add(new ProjectileEntity(gameView, this, origin, angle, isPlayerProjectile));
	}

	public List<EnemyEntity> getEnemies () {
		return enemies;
	}

	public List<ProjectileEntity> getProjectiles () {
		return projectiles;
	}

	public PlayerEntity getPlayer () {
		return p;
	}

	public boolean isPlayerAlive () {
		return playerAlive;
	}

	public GameOptions getCurrentOptions () {
		return options;
	}

	public String toString () {
		String s = "";

		return s;
	}
}