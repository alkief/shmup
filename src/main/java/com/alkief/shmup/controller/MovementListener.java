package shmup.controller;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import shmup.model.entities.Entity;

public class MovementListener implements KeyListener {
	Engine engine;
	boolean up, down, left, right;

	public MovementListener (Engine e) {
		this.engine = e;
		up = false;
		down = false;
		left = false;
		right = false;
	}

	public void keyPressed (KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_W) {
			up = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_S) {
			down = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_A) {
			left = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_D) {
			right = true;
		}

		// Reset if R is pressed
		if(ke.getKeyCode() == KeyEvent.VK_R) {
			engine.init();
		}

		updateDirection();
	}

	public void keyReleased (KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_W) {
			up = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_S) {
			down = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_A) {
			left = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_D) {
			right = false;
		}

		updateDirection();
	}

	// Update player state to be travelling the corresponding directions
	private void updateDirection () {
		if (up && !down && !left && !right) {
			// NORTH
			engine.getGameState().getPlayer().setDirectionX(0);
			engine.getGameState().getPlayer().setDirectionY(-1);
		} else if (!up && down && !left && !right) {
			// SOUTH
			engine.getGameState().getPlayer().setDirectionX(0);
			engine.getGameState().getPlayer().setDirectionY(1);
		} else if (!up && !down && !left && right) {
			// EAST
			engine.getGameState().getPlayer().setDirectionX(1);
			engine.getGameState().getPlayer().setDirectionY(0);
		} else if (!up && !down && left && !right) {
			// WEST
			engine.getGameState().getPlayer().setDirectionX(-1);
			engine.getGameState().getPlayer().setDirectionY(0);
		} else if (up && !down && left && !right) {
			// NORTHWEST
			engine.getGameState().getPlayer().setDirectionX(-1);
			engine.getGameState().getPlayer().setDirectionY(-1);
		} else if (up && !down && !left && right) {
			// NORTHEAST
			engine.getGameState().getPlayer().setDirectionX(1);
			engine.getGameState().getPlayer().setDirectionY(-1);
		} else if (!up && down && left && !right) {
			// SOUTHWEST
			engine.getGameState().getPlayer().setDirectionX(-1);
			engine.getGameState().getPlayer().setDirectionY(1);
		} else if (!up && down && !left && right) {
			// SOUTHEAST
			engine.getGameState().getPlayer().setDirectionX(1);
			engine.getGameState().getPlayer().setDirectionY(1);
		} else {
			// SIT STILL
			engine.getGameState().getPlayer().setDirectionY(0);
			engine.getGameState().getPlayer().setDirectionX(0);
		}
	}

	public void keyTyped (KeyEvent ke) {}
}