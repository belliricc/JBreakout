package jbreakout.model;

import java.util.HashMap;

public interface ILevel {

	boolean isThisIdAlreadyUsed(int id);

	// Adds a new brick into the structure. If something goes wrong, it returns -1.
	int addNewBrick(Brick new_brick);

	// This method removes from the data structure the brick with the specified ID.
	// It eliminates the brick whether the brick can be hit or not.
	// If the brick is found and removed returns true, and false otherwise(ID not found).
	boolean removeBrickWithId(int id);

	// Returns the brick with the specified ID; if it's not found it returns null.
	Brick getBrickWithId(int id);

	// "Hits" the brick with the specified id.
	// Returns true if the block is hit with success, and false otherwise. 
	boolean hitBrickWithId(int id);

	// This method removes all the bricks from the collection.
	void removeAllBricks();

	// Returns the number of bricks present in the collection.
	int getNumberOfBricks();

	Ball getLevelBall();

	void setLevelBall(Ball level_ball);

	Paddle getLevelPaddle();

	void setLevelPaddle(Paddle level_paddle);

	String getPlayerName();

	void setPlayerName(String playerName);
	
	int getScore();

	void setScore(int score);
	
	void setNewLevel(String playername);

	boolean isPaddleMovingRight();

	boolean isPaddleMovingLeft();

	void setPaddleMovingRight(boolean b);

	void setPaddleMovingLeft(boolean b);

	HashMap<Integer, Brick> getBricksAsHashMap();

	void increaseScoreBy(int amount);
	
	boolean gameIsPaused();

	void resumeGame();

	void pauseGame();
	
	String toString();

	void decreaseLivesByOne();

	int getLivesLeft();

	void setLivesLeft(int lives_left);

	void setBallIsOnPaddle(boolean ballIsOnPaddle);

	boolean isBallOnPaddle();

	void loadLevelConfigurationFrom(String file);
	
	int getNumberOfIndestructibleBricks();

}