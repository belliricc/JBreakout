package jbreakout.controller;

import static jbreakout.util.MathTools.degToRad;
import static jbreakout.util.MathTools.randomNumberInclusive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.swing.JLabel;

import jbreakout.geom.CollisionChecker;
import jbreakout.geom.Vector2D;
import jbreakout.model.Ball;
import jbreakout.model.Level;
import jbreakout.model.Paddle;
import jbreakout.util.Config;
import jbreakout.util.ReadCSV;
import jbreakout.util.SoundPlayer;
import jbreakout.view.GameOverWindow;
import jbreakout.view.GameWindow;
import jbreakout.view.MainWindow;
import jbreakout.view.NewGameWindow;
import jbreakout.view.SavedGamesWindow;
import jbreakout.view.ScoreBoardWindow;
import jbreakout.view.SettingsWindow;

public class ControllerForView implements IControllerForView {
	
	//---------------------------------------------------------------
	// STATIC INSTANCE
	//---------------------------------------------------------------
	private static ControllerForView instance = null;
	
	private Ball ball, nextPositionBall;
	
	private Paddle paddle;
	
	private long frameCounter;
	
	private int randomPos; // Needed to get a random x position on the paddle when the player loses a life.

	private ControllerForView() {
		this.frameCounter = 0;
	}
	
	@Override
	public void openStartWindow() {
		MainWindow.getInstance();
		MainWindow.getInstance().setVisible(true);
	}
	
	@Override
	public void openNewGameWindow() {
		MainWindow.getInstance().setVisible(false);
		
		NewGameWindow.getInstance();
		NewGameWindow.getInstance().setVisible(true);
	}
	
	@Override
	public void openNewJbreakoutGame() {
		NewGameWindow.getInstance().dispose();
		MainWindow.getInstance().dispose();
		
		Level.getInstance();
		
		String playerName = NewGameWindow.getInstance().getPlayerName();
		
		Level.getInstance().setNewLevel(playerName);
		
		this.nextPositionBall = Level.getInstance().getLevelBall();
		this.nextPositionBall.applyOwnDirection();
		
		GameWindow.getInstance();
		GameWindow.getInstance().setVisible(true);
		
		GameWindow.getInstance().setPlayerName(NewGameWindow.getInstance().getPlayerName());
		GameWindow.getInstance().setScore(0);
		
	}
	
	@Override
	public void openSavedGamesWindow() {
		MainWindow.getInstance().setVisible(false);
		
		SavedGamesWindow.getInstance();
		SavedGamesWindow.getInstance().setVisible(true);
		ControllerForView.getInstance().loadListOfSavedGames();
	}

	@Override
	public void closeStartWindow() {		
	}

	@Override
	public int firstSlotFreeNumber() {
		int num = 0;
		
		BufferedReader br;
		
		for(int i = 1; i <= SavedGamesWindow.SAVE_SLOTS_NUMBER; i++) {
			try {
				br = new BufferedReader(new FileReader("savedLevels/saveslot" + i + ".txt"));
				if (br.readLine() == null) {
				    num = i;
				    break;
				}
				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		
		
		return num;
	}
	
	@Override
	public void saveGameToSlotNumber(int num) {
		
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("savedLevels/saveslot" + num + ".txt"), "utf-8"));
		    writer.write(Level.getInstance().toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
		   try {
			   writer.close();
		   } catch (Exception ex) {}
		}
	}

	@Override
	public void loadSaveGameNumber(int saveSlotNumber) {
		if(!"Save slot empty.".equals(SavedGamesWindow.getInstance().getLabels()[saveSlotNumber - 1].getText())) {
			MainWindow.getInstance().dispose();
			
			Level.getInstance();
			
			Level.getInstance().loadLevelConfigurationFrom("savedLevels/saveslot" + saveSlotNumber);
			
			this.nextPositionBall = Level.getInstance().getLevelBall();
			this.nextPositionBall.applyOwnDirection();
			
			SavedGamesWindow.getInstance().dispose();
			
			GameWindow.getInstance();
			GameWindow.getInstance().setVisible(true);
			
			GameWindow.getInstance().setPlayerName(Level.getInstance().getPlayerName());
			GameWindow.getInstance().setScore(Level.getInstance().getScore());
		}
	}
	


	@Override
	public void clearButtonPressed(int buttonNumber) {
    	if(buttonNumber >= 1 && buttonNumber <= SavedGamesWindow.SAVE_SLOTS_NUMBER) {
    		try {
    			// Clears the file.
				new PrintWriter("savedLevels/saveslot"+buttonNumber+".txt").close();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}
    		ControllerForView.getInstance().loadListOfSavedGames();
    	}
    }
    
	@Override
    public void loadListOfSavedGames() {
    	JLabel[] labels = SavedGamesWindow.getInstance().getLabels();
    	for(int i = 1; i <= SavedGamesWindow.SAVE_SLOTS_NUMBER; i++) {
    		String newLabelText = "";
    		try {
	    		LinkedList<String[]> myll = ReadCSV.getRows("savedLevels/saveslot"+i+".txt", "utf-8");
	    		String[] nameAndScore = myll.getFirst();
	    		newLabelText += "Playername: "+nameAndScore[0]+"; Score: "+nameAndScore[1];
	    	} catch(NoSuchElementException nsee) {
	    		// The list is empty => the save slot file is empty. Generated by myll.getFirst()
	    		nsee.printStackTrace();
	    		newLabelText += "Save slot empty.";
	    	} catch(FileNotFoundException fnfe) {
	    		fnfe.printStackTrace();
	    		newLabelText += "File not found.";
	    	} catch (IOException ioe) {
				ioe.printStackTrace();
				newLabelText += "I/O Error.";
			} finally {
				labels[i - 1].setText(newLabelText);
			}
    	}
    }
   

	@Override
	public void closeNewGameWindow() {		
	}
	
	
	@Override
	public void openSettingsWindow() {
		MainWindow.getInstance().setVisible(false);
		
		SettingsWindow.getInstance();
		SettingsWindow.getInstance().setVisible(true);
	}

	@Override
	public void closeSettingsWindow() {		
	}


	@Override
	public void rightArrowPressed() {
		Level.getInstance().setPaddleMovingRight(true);
	}

	@Override
	public void rightArrowReleased() {
		Level.getInstance().setPaddleMovingRight(false);		
	}

	@Override
	public void leftArrowPressed() {
		Level.getInstance().setPaddleMovingLeft(true);
	}

	@Override
	public void leftArrowReleased() {
		Level.getInstance().setPaddleMovingLeft(false);		
	}

	@Override
	public void pressedKeyP() {
		pauseResumeEvent();
	}

	@Override
	public void pressedSpace() {
		pauseResumeEvent();
	}
	
	@Override
	public void pauseResumeEvent() {
		if(!Level.getInstance().isBallOnPaddle()) {
			pauseResumeGame();
		} else {
			Level.getInstance().setBallIsOnPaddle(false);
			Level.getInstance().getLevelBall().setDirection(0.0, 1.0); // Pointing downwards, on the paddle.
			randomPos = randomNumberInclusive(30, (int)Level.getInstance().getLevelPaddle().getWidth() - 30);
		}
	}

	@Override
	public void nextFrame() {
		
		this.frameCounter++;
		
		ball = Level.getInstance().getLevelBall();
		
		if(Level.getInstance().isBallOnPaddle()) {
			ball.setDirection(0.0, 0.0);
			ball.setBallGeometry(20.0 + Level.getInstance().getLevelPaddle().getX() + randomPos,
								 Level.getInstance().getLevelPaddle().getY() - 30.0,
								 15.0);
			} else {
			
			nextPositionBall = ball;
			nextPositionBall.applyOwnDirection();
			
			// Ball hit a corner!
			int wallHit = CollisionChecker.checkBorderHit(nextPositionBall);
			if(wallHit != 0) {
				switch(wallHit) {
					case 1:{ // Top corner hit.
						ball.setDirection(ball.getDirection().getX(), (ball.getDirection().getY() < 0) ? -ball.getDirection().getY() : ball.getDirection().getY());
						break;
					}
					case 2:{ // Right corner hit.
						ball.setDirection((ball.getDirection().getX() < 0) ? ball.getDirection().getX() : -ball.getDirection().getX(), ball.getDirection().getY());
						break;
					}
					case 3:{ // Bottom corner hit.
						if(Level.getInstance().getLivesLeft() > 0) {
							Level.getInstance().decreaseLivesByOne();
							Ball onPaddleBall = new Ball(20.0 + Level.getInstance().getLevelPaddle().getX() + randomNumberInclusive(30, (int)Level.getInstance().getLevelPaddle().getWidth() - 30),
														 Level.getInstance().getLevelPaddle().getY() - 15.0,
														 15.0);
							Level.getInstance().getLevelBall().setBallGeometry(onPaddleBall);
							GameWindow.getInstance().getLivesLeftPanel().repaint();
							Level.getInstance().setBallIsOnPaddle(true);
						} else {
							// Game over situation.
							Level.getInstance().pauseGame();
							GameWindow.getInstance().stopTimer();
							GameOverWindow.getInstance();
							GameOverWindow.getInstance().setVisible(true);
							GameOverWindow.getInstance().getNameLabel().setText("Name: " + Level.getInstance().getPlayerName());
							GameOverWindow.getInstance().getScoreLabel().setText("Score: " + Level.getInstance().getScore());
							int score = Level.getInstance().getScore();
							String message = "";
							if(score >= ScoreBoardWindow.getInstance().lowestScore()) {
								ScoreBoardWindow.getInstance().insertNewHighScore(score, Level.getInstance().getPlayerName());
								message += "You made a new HighScore, Horray!";
							} else {
								message += "You didn't make it into the scoreboard :(";
							}
							GameOverWindow.getInstance().getScoreMessageLabel().setText(message);
							GameWindow.getInstance().dispose();
						}
						break;
					}
					case 4:{ // Left corner hit.
						ball.setDirection((ball.getDirection().getX() < 0) ? -ball.getDirection().getX() : ball.getDirection().getX(), ball.getDirection().getY());
						break;
					}
				}
				
				if(Config.getInstance().soundIsEnabled()) {
					SoundPlayer.getInstance().setAndPlayTrack("wall");
				}
			}
			
			
			// Ball hit a brick!
			int brickHitId = CollisionChecker.checkBrickHit(nextPositionBall);
			if(brickHitId != 0) {
				int sideOfTheBrickHit = CollisionChecker.checkBrickSideHit(nextPositionBall, Level.getInstance().getBrickWithId(brickHitId));
				// 1 for top hit;
				// 2 for right hit;
				// 3 for bottom hit;
				// 4 for left hit;
				// 5 for top-right edge;
				// 6 for right-bottom edge;
				// 7 for bottom-left edge;
				// 8 for left-top edge.
				switch(sideOfTheBrickHit) {
					case 0:{ // Error: check CollisionChecker.checkBrickHit(...) method declaration for more info.
						System.out.println("Collision error.");
						break;
					}
					case 1:{ // Top side hit.
						ball.setDirection(ball.getDirection().getX(), (ball.getDirection().getY() < 0) ? ball.getDirection().getY() : -ball.getDirection().getY());
						break;
					}
					case 2:{ // Right side hit.
						ball.setDirection((ball.getDirection().getX() < 0) ? -ball.getDirection().getX() : ball.getDirection().getX(), ball.getDirection().getY());
						break;
					}
					case 3:{ // Bottom side hit.
						ball.setDirection(ball.getDirection().getX(), (ball.getDirection().getY() < 0) ? -ball.getDirection().getY() : ball.getDirection().getY());
						break;
					}
					case 4:{ // Left side hit.
						ball.setDirection((ball.getDirection().getX() < 0) ? ball.getDirection().getX() : -ball.getDirection().getX(), ball.getDirection().getY());
						break;
					}
					case 5:{ // Top-right edge hit.
						ball.getDirection().setAngle(degToRad(randomNumberInclusive(20, 70)));
					}
					case 6:{ // Right-bottom edge hit.
						ball.getDirection().setAngle(degToRad(-randomNumberInclusive(20, 70)));
						break;
					}
					case 7:{ // Bottom-left edge hit.
						ball.getDirection().setAngle(degToRad(-randomNumberInclusive(110, 160)));
						break;
					}
					case 8:{ // Left-top edge hit.
						ball.getDirection().setAngle(degToRad(randomNumberInclusive(110, 160)));
						break;
					}
				}
				// We HIT the brick anyway, so:
				if(Config.getInstance().soundIsEnabled()) {
					SoundPlayer.getInstance().setAndPlayTrack("brick");
				}
				
				// The brick contained a powerup! The legend is:
				// 0 for no powerup;
				// 1 for bigger ball;
				// 2 for larger paddle;
				// 3 for faster paddle.
				switch (Level.getInstance().getBrickWithId(brickHitId).getPowerupNumber()) {
					case 0:{ break; }
					case 1:{
						Ball newBall = Level.getInstance().getLevelBall();
						newBall.setFrame(newBall.getX(), newBall.getY(), newBall.getWidth() + 20.0, newBall.getHeight() + 20.0);
						Level.getInstance().setLevelBall(newBall);
						break;
					}
					case 2:{
						Paddle newPaddle = Level.getInstance().getLevelPaddle();
						newPaddle.setFrame(newPaddle.getX() - 20.0, newPaddle.getY(), newPaddle.getWidth() + 40.0, newPaddle.getHeight());
						Level.getInstance().setLevelPaddle(newPaddle);
						break;
					}
					case 3:{
						Paddle newPaddle = Level.getInstance().getLevelPaddle();
						newPaddle.setSpeed(newPaddle.getSpeed() + 1.0);
						Level.getInstance().setLevelPaddle(newPaddle);
						break;
					}
				}
				
				Level.getInstance().hitBrickWithId(brickHitId);
				GameWindow.getInstance().setScore(Level.getInstance().getScore());
				
				// The game is won if:
				// there are no bricks left or the remaining ones are indestructible.
				if(Level.getInstance().getNumberOfBricks() == 0 ||
				   (Level.getInstance().getNumberOfBricks() - Level.getInstance().getNumberOfIndestructibleBricks()) == 0) {
					gameCompleted();
				}
			}
			
		}
		
		if(CollisionChecker.checkPaddleHit(nextPositionBall)) {
			paddleHit(CollisionChecker.howCloseToTheCenter(nextPositionBall, Level.getInstance().getLevelPaddle()));
		}
		
		if(Level.getInstance().isPaddleMovingRight() &&
		   Level.getInstance().getLevelPaddle().getX() + Level.getInstance().getLevelPaddle().getWidth() < Level.LEVEL_WIDTH - 20.0) {
			movePaddle(-1.0);
		} else if(Level.getInstance().isPaddleMovingLeft() &&
				  Level.getInstance().getLevelPaddle().getX() > 0.0 + 20.0) {
			movePaddle(1.0);
		}
		
	}
	
	@Override
	public void movePaddle(double rightOrLeft) {
		paddle = Level.getInstance().getLevelPaddle();
		paddle.setRect(paddle.getX() - paddle.getSpeed() * rightOrLeft, paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}
	

	public void paddleHit(double skew) {
		ball = Level.getInstance().getLevelBall();
		
		// The new direction is pointing upwards.
		Vector2D newDirection = new Vector2D(ball.getDirection().getX(), (ball.getDirection().getY() < 0) ? ball.getDirection().getY() : -ball.getDirection().getY());
		
		// Now let's rotate it! from -0.9 to 0.9 (all to the left to all to the right).
		// The value is clamped from -1.0, 1.0 to prevent drastic horizontal directions.
		// From -70 to 70 degrees (non-clamped value). (20 - 160 degrees)
		//double newAngle = ((skew + 1.0) / 2.0) * 140.0 + 20.0;
		//newDirection.setAngle(degToRad(newAngle));
		
		newDirection.setAngle(degToRad(((skew + 1.0) / 2.0) * 140.0 + 20.0));
		
		ball.setDirection(newDirection);
		
		if(Config.getInstance().soundIsEnabled()) {
			SoundPlayer.getInstance().setAndPlayTrack("paddle");
		}
	}
	
	@Override
	public void gameCompleted() {
		Level.getInstance().pauseGame();
		GameWindow.getInstance().stopTimer();
		GameOverWindow.getInstance();
		GameOverWindow.getInstance().setVisible(true);
		GameOverWindow.getInstance().getGameOverLabel().setText("YOU WON!");
		GameOverWindow.getInstance().getNameLabel().setText("Name: " + Level.getInstance().getPlayerName());
		GameOverWindow.getInstance().getScoreLabel().setText("Score: " + Level.getInstance().getScore());
		int score = Level.getInstance().getScore();
		String message = "";
		if(score >= ScoreBoardWindow.getInstance().lowestScore()) {
			ScoreBoardWindow.getInstance().insertNewHighScore(score, Level.getInstance().getPlayerName());
			message += "You made a new HighScore, Horray!";
		} else {
			message += "You didn't make it into the scoreboard :(";
		}
		GameOverWindow.getInstance().getScoreMessageLabel().setText(message);
		GameWindow.getInstance().dispose();
	}

	@Override
	public void closeSavedGamesWindow() {
	}

	@Override
	public void openScoreboardWindow() {
		MainWindow.getInstance().setVisible(false);
		
		ScoreBoardWindow.getInstance();
		ScoreBoardWindow.getInstance().setVisible(true);
	}




	@Override
	public void closeScoreBoardWindow() {		
	}

	@Override
	public void pauseResumeGame() {
		if(GameWindow.getInstance().isTimerRunning()) {
			GameWindow.getInstance().stopTimer();
			Level.getInstance().pauseGame();
		} else {
			GameWindow.getInstance().startTimer();
			Level.getInstance().resumeGame();
		}
	}
	
	@Override
	public Paddle getPaddle() {
		return Level.getInstance().getLevelPaddle();
	}

	@Override
	public void setPaddle(Paddle paddle) {
		Level.getInstance().setLevelPaddle(paddle);
	}
	
	@Override
	public Ball getBall() {
		return Level.getInstance().getLevelBall();
	}
	
	@Override
	public void setBall(Ball ball) {
		Level.getInstance().setLevelBall(ball);
	}
	
	@Override
	public String getPlayerName() {
		return Level.getInstance().getPlayerName();
	}

	@Override
	public void setPlayerName(String playerName) {
		Level.getInstance().setPlayerName(playerName);
	}

	@Override
	public int getScore() {
		return Level.getInstance().getScore();
	}

	@Override
	public void incrementScoreBy(int increment) {
		Level.getInstance().setScore(Level.getInstance().getScore() + increment);
	}

	@Override
	public void initGame() {		
	}
	
	//---------------------------------------------------------------
	// STATIC METHODS
	//---------------------------------------------------------------
	public static IControllerForView getInstance() {
		if (instance == null) {
			instance = new ControllerForView();
		}
		return instance;
	}

	@Override
	public long getFrameCounter() {
		return frameCounter;
	}

	@Override
	public void setFrameCounter(long frameCounter) {
		this.frameCounter = frameCounter;
	}
}
