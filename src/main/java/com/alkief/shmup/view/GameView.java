package shmup.view;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.awt.geom.AffineTransform;

import shmup.model.GameState;
import shmup.view.*;
import shmup.model.entities.*;

public class GameView extends JPanel {
	GameState gameState = null;

	public GameView () {
		setBackground(Color.BLACK);
	}

	public void setState (GameState gs) {
		gameState = gs;
	}

	// Call draw functions based on game state
	public void paintComponent (Graphics g) {
		if (gameState != null) {
			// Clear previous render
			g.clearRect(0, 0, (int) Math.round(getWidth()), (int) Math.round(getHeight()));

			drawPlayer(g, gameState.getPlayer());
			drawProjectiles(g, gameState.getProjectiles());
			drawEnemies(g, gameState.getEnemies());
		}
	}

	// Rectangle for the player entity
	public void drawPlayer (Graphics g, PlayerEntity playerEntity) {
		g.setColor(Color.GREEN);

		g.fillRect((int) playerEntity.getPosition().getX(), (int) playerEntity.getPosition().getY(),
								 (int) playerEntity.getPixelWidth(), (int) playerEntity.getPixelHeight());
	}

	// Rectangle for all projectiles
	public void drawProjectiles (Graphics g, List<ProjectileEntity> projectiles) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform(); // Store the initial graphics transform
		g2d.setColor(Color.BLUE);

		synchronized (projectiles) {
			for (ProjectileEntity pe : projectiles) {
				// Rotate using projectile angle (with offset)
				g2d.rotate(pe.getAngle() - Math.PI / 2,
									 pe.getPosition().getX(),
									 pe.getPosition().getY());

				g2d.fillRect((int) pe.getPosition().getX(), (int) pe.getPosition().getY(),
											(int) pe.getWidth(), (int) pe.getHeight());
				g2d.setTransform(old); // Reset to old transform so we don't rotate everything
			}
		}
	}

	// Ellipse for enemies
	public void drawEnemies (Graphics g, List<EnemyEntity> enemies) {
		g.setColor(Color.RED);

		synchronized (enemies) {
			for (EnemyEntity enemy : enemies) {
				g.fillOval((int) enemy.getPosition().getX(), (int) enemy.getPosition().getY(),
									 (int) enemy.getPixelWidth(), (int) enemy.getPixelHeight());
			}
		}
	}
}