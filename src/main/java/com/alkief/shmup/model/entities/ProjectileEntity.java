package shmup.model.entities;

import shmup.view.GameView;
import shmup.model.GameState;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.lang.Math;

public class ProjectileEntity extends Entity {
	double angle; // Angle between entity and target location

	public ProjectileEntity (GameView gv, GameState gs, Point2D origin, double angle, boolean playerOwned) {
		super(gv, gs, playerOwned);
		width = gameView.getWidth() / 500;
		height = gameView.getHeight() / 50;
		speed = 150;
		position = new Point2D.Double(origin.getX(), origin.getY());

		this.angle = angle;
	}

	public Area getHitBox () {
		// Convert line to Rectangle2D using width
		Rectangle2D.Double projectile = new Rectangle2D.Double(getPosition().getX(), getPosition().getY(),
																						 getWidth(), getHeight());

		// Rotate using stored angle with offset for AffineTransform (pi / 2)
		AffineTransform transform = AffineTransform.getRotateInstance(angle - (Math.PI / 2), getPosition().getX(), getPosition().getY());
		Shape s = transform.createTransformedShape((Shape) projectile);

		return new Area(s); // Return the area
	}

	// Return the endpoint to extend to using position as origin
	public Point2D getEndPoint () {
		double dy = Math.sin(angle) * getHeight();
		double dx = Math.cos(angle) * getHeight();

		return new Point2D.Double(position.getX() + dx, position.getY() + dy);
	}

	public void move (double dt) {
		// (dt * getSpeed()) is hypotenuse of triangle, calc sides as change in position
		double dx = Math.cos(angle) * dt * getSpeed(),
					 dy = Math.sin(angle) * dt * getSpeed();

		// Update position state using deltas
		setPosition(position.getX() + dx,
								position.getY() + dy);
	}

	public String toString () {
		String s = "";

		s += "Position: (" + position.getX() + ", " + position.getY() + ")\n";
		s += "Speed: " + speed + "\n";
		s += "Width: " + width + "\n";
		s += "Height: " + height+ "\n";
		s += "Angle: " + angle + "\n";

		return s;
	}

	public double getAngle () {
		return angle;
	}
}