package shmup.model.entities;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;

import shmup.view.GameView;
import shmup.model.GameState;
import shmup.model.entities.Entity;

public class EnemyEntity extends Entity {
	public EnemyEntity (GameView gv, GameState gs) {
		super(gv, gs, false);
		triggerCooldown = 500;
		speed = ThreadLocalRandom.current().nextInt(10, 35);
		width = 1 / 50.0;
		height = 1 / 50.0;
	}

	public Area getHitBox () {
		return new Area(new Ellipse2D.Double(getPosition().getX(),
										getPosition().getY(),
										getPixelWidth(),
										getPixelHeight()));
	}

	public void move (double dt) {
		// Calculate the angle between this entity and the player
		double xDist = gameState.getPlayer().getPosition().getX() - getPosition().getX(),
					 yDist = gameState.getPlayer().getPosition().getY() - getPosition().getY(),
					 angle = Math.atan(yDist / xDist);

		if (xDist < 0) angle += (Math.PI); // For leftward movement

		// Calculate change in position
		double dx = getSpeed() * dt * Math.cos(angle),
					 dy = getSpeed() * dt * Math.sin(angle);

		// New position
		double newX = getPosition().getX() + dx,
					 newY = getPosition().getY() + dy;

		setPosition(newX, newY); // Update state
	}
}