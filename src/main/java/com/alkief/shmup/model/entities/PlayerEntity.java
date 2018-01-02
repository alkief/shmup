package shmup.model.entities;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.lang.Math;

import shmup.view.GameView;
import shmup.model.GameState;

public class PlayerEntity extends Entity {

	Point2D.Double shootTarget; // Tracks mouse position via ShootListener mouseevents

	public PlayerEntity (GameView gv, GameState gs) {
		super(gv, gs, true);
		width = 1 / 25.0;
		height = 1 / 25.0;
		speed = 75;
		triggerCooldown = 100;
		shootTarget = new Point2D.Double(); // Init so we have no null pointers
	}

	public void shoot () {
		if (isFiring()) {
			Point2D.Double origin = new Point2D.Double(getPosition().getX() + (getPixelWidth() / 2),
																								 getPosition().getY() + (getPixelHeight() / 2));
			Point2D.Double target = new Point2D.Double(shootTarget.getX(), shootTarget.getY());

			gameState.addPlayerProjectile(origin, target);
		}
	}

	public void setShootTarget (double x, double y) {
		shootTarget = new Point2D.Double(x, y);
	}

	public Point2D getShootTarget () {
		return shootTarget;
	}
}