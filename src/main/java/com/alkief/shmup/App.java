package com.alkief.shmup;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import shmup.controller.Engine;
import shmup.view.GameView;
import shmup.controller.MovementListener;
import shmup.controller.ShootListener;

public class App extends JFrame {

	Engine engine;
	GameView gameView;

	public App () {
		gameView = new GameView();
		engine = new Engine(gameView); // Let engine manage manipulation of game view

		// Boiler stuff
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		// Display the game view
		add(gameView); // Show the game view within our app frame
		setVisible(true);

		engine.init(); // Init state, render GUI

		// Listen for player movement using WASD
		addKeyListener(new MovementListener(engine));

		// Listen for mouse motion to trigger player shooting
		ShootListener shootListener = new ShootListener(engine);
		gameView.addMouseListener(shootListener);
		gameView.addMouseMotionListener(shootListener);

		engine.gameLoop(); // Start main game loop
	}

  public static void main( String[] args ) {
  	App game = new App();
  }
}
