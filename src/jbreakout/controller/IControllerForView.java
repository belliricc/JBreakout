package jbreakout.controller;

import jbreakout.model.Ball;
import jbreakout.model.Paddle;

public interface IControllerForView {

	// ----- MAIN MENU WINDOW -----
	public void openStartWindow();
	public void closeStartWindow();

	// ----- SAVED GAMES WINDOW -----
	public void openSavedGamesWindow();
	public void closeSavedGamesWindow();
	public void loadListOfSavedGames();
	public void clearButtonPressed(int buttonNumber);
	public void loadSaveGameNumber(int saveSlotNumber);
	
	// ----- SCOREBOARD WINDOW -----
	public void openScoreboardWindow();
	public void closeScoreBoardWindow();
	
	// ----- SETTINGS WINDOW -----
	public void openSettingsWindow();
	public void closeSettingsWindow();
	
	// ----- NEW GAME INTERFACE WINDOW -----
	public void openNewGameWindow();
	public void closeNewGameWindow();
	public void openNewJbreakoutGame();
	
	// ----- ACTUAL GAME WINDOW -----
	public void initGame();
	
	public void rightArrowPressed();
	public void rightArrowReleased();
	
	public void leftArrowPressed();
	public void leftArrowReleased();
	
	public void pressedKeyP();
	
	public void pauseResumeGame();
	
	public String getPlayerName();
	public void setPlayerName(String playerName);
	
	public int getScore();
	public void incrementScoreBy(int increment);
	
	public Paddle getPaddle();
	public void setPaddle(Paddle paddle);
	
	public Ball getBall();
	public void setBall(Ball ball);

	public void movePaddle(double byThisAmount);
	
	public void nextFrame();
	public void pressedSpace();
	void pauseResumeEvent();
	long getFrameCounter();
	void setFrameCounter(long frameCounter);
	void gameCompleted();
	int firstSlotFreeNumber();
	void saveGameToSlotNumber(int num);
	
} // end interface

