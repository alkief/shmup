package shmup.model;

public class GameOptions {
	int maxEnemies;

	public GameOptions () {
		maxEnemies = 25;
	}

	public int getMaxEnemies () {
		return maxEnemies;
	}

	public static GameOptions getDefaultOptions () {
		return new GameOptions ();
	}
}