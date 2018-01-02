package shmup.controller;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

import shmup.controller.Engine;

public class ShootListener implements MouseInputListener {

	Engine engine;
	Timer shootTimer;

	private class ShootTask extends TimerTask {
		public ShootTask () {}

		public void run () {
			engine.getGameState().getPlayer().shoot(); // Attempt to make the player shoot
			// Schedule a new shoot task with delay set to the player's trigger cooldown
			shootTimer.schedule(new ShootTask(),
													engine.getGameState().getPlayer().getTriggerCooldown());
		}
	}

	public ShootListener (Engine e) {
		engine = e; // Store engine instance to trigger shoot function

		// Set up timer loop to call player shoot
		shootTimer = new Timer();
		shootTimer.schedule(new ShootTask(),
												(long) engine.getGameState().getPlayer().getTriggerCooldown());
	}

	public void mousePressed (MouseEvent e) {
		// Start player shooting
		engine.getGameState().getPlayer().setFiring(true);

	}

	public void mouseReleased (MouseEvent e) {
		// Stop player firing
		engine.getGameState().getPlayer().setFiring(false);
	}

	// Both 'moved' and 'dragged' are needed or bullets sometimes go in random directions
	public void mouseMoved (MouseEvent e) {
		// Update the target position for player projectiles
		engine.getGameState().getPlayer().setShootTarget((double) e.getPoint().getX(),
																										(double) e.getPoint().getY());
	}

	public void mouseDragged (MouseEvent e) {
		// Update the target position for player projectiles
		engine.getGameState().getPlayer().setShootTarget((double) e.getPoint().getX(),
																										(double) e.getPoint().getY());
	}
	public void mouseClicked (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
}