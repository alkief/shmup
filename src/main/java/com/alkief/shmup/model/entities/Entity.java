package shmup.model.entities;

import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import shmup.view.GameView;
import shmup.model.GameState;

public class Entity {

	GameState gameState; // The state this entity belongs to (for adding projectiles via shoot())
	GameView gameView; // Reference to the main window for calculating entity size

	Point2D.Double position = new Point2D.Double(); // The entity position

	// Movement properties
	int speed = 0,
		  directionX = 0, // -1 for left, 1 for right
		  directionY = 0; // -1 for up, 1 for down

  double acceleration = 0.1; // Currently unused

  double width = 0,	// Size of box encapsulating the entity
  			 height = 0;

	long triggerCooldown; // 'long' here is for convenience in using Timer class

	boolean firing = false,
					playerOwned; // Denotes a friendly entity for collision

	public Entity (GameView gv, GameState gs, boolean playerOwned) {
		gameView = gv;
		gameState = gs;
		this.playerOwned = playerOwned;
	}

	public Area getHitBox () {
		return new Area(new Rectangle2D.Double(getPosition().getX(),
								 getPosition().getY(),
								 getPixelWidth(),
								 getPixelHeight()));
	}

	public boolean checkCollision (Area other) {
		Area hitbox = getHitBox();
		hitbox.intersect(other);
		return !hitbox.isEmpty();
	}

	public boolean isPlayerOwned () {
		return playerOwned;
	}

	public void setFiring (boolean f) {
		firing = f;
	}

	public boolean isFiring () {
		return firing;
	}

	public long getTriggerCooldown () {
		return triggerCooldown;
	}

	public void setTriggerCooldown (long cd) {
		triggerCooldown = cd;
	}

	public void setWidth (double w) {
		width = w;
	}

	public void setHeight (double h) {
		height = h;
	}

	public double getWidth () {
		return width;
	}

	public double getHeight () {
		return height;
	}

	public double getPixelWidth () {
		double pixelWidth = width * gameView.getWidth();
		if (pixelWidth == 0) pixelWidth = 1;

		return pixelWidth;
	}

	public double getPixelHeight () {
		double pixelHeight = height * gameView.getHeight();
		if (pixelHeight == 0) pixelHeight = 1;

		return pixelHeight;
	}

	public Point2D getPosition () {
		return position;
	}

	public void setPosition (double x, double y) {
		position = new Point2D.Double(x, y);
	}

	public int getSpeed () {
		return speed;
	}

	public void setDirectionX (int d) {
		directionX = d;
	}

	public int getDirectionX () {
		return directionX;
	}

	public void  setDirectionY (int d) {
		directionY = d;
	}

	public int getDirectionY () {
		return directionY;
	}

	// Update entity position using movement properties given a change in time
	public void move (double dt) {
		// Calculate change in position
		double dx = getSpeed() * dt * getDirectionX(),
					 dy = getSpeed() * dt * getDirectionY();

		// New position
		double newX = getPosition().getX() + dx,
					 newY = getPosition().getY() + dy;

		// Check bounds
		if (newX < 0) {
			newX = 0.0;
		} else if (newX + getPixelWidth() > gameView.getWidth()) {
			newX = gameView.getWidth() - getPixelWidth();
		}

		if (newY < 0) {
			newY = 0.0;
		} else if (newY + getPixelHeight() > gameView.getHeight()) {
			newY = gameView.getHeight() - getPixelHeight();
		}

		setPosition(newX, newY);
	}

	// Fire a projectile using the entity position as source, and given xy pair as target
	public void shoot (double x, double y) {

	}

	public String toString () {
		String s = "";
		s += "Position: (" + position.getX() + ", " + position.getY() + ")\n";
		s += "Speed: " + speed + "\n";
		s += "Direction X: " + directionX + "\n";
		s += "Direction Y: " + directionY + "\n";
		s += "Pixel Width: " + getPixelWidth() + "\n";
		s += "Pixel Height: " + getPixelHeight() + "\n";

		return s;
	}
}