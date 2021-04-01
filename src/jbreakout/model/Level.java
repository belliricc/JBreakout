package jbreakout.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import jbreakout.util.Config;
import jbreakout.util.ReadCSV;

public class Level implements ILevel {
	
	private static Level instance = null;
	
	// The range of the random ID generator.
	// Some of the first positions will be reserved.
	private final static int MAX_ID_NUM = Config.getInstance().getMaxIdNumber();
	private final static int BASE_ID_NUM = Config.getInstance().getBaseIdNumber();
		
	public final static double LEVEL_WIDTH = Config.getInstance().getLevelLogicalWidth();
	public final static double LEVEL_HEIGHT = Config.getInstance().getLevelLogicalHeight();
	
	//---------------------------------------
	// INSTANCE ATTRIBUTES
	//---------------------------------------
	
	private String playerName;
	private int score;
	private HashMap<Integer,Brick> level_bricks;
	private Ball level_ball;
	private Paddle level_paddle;
	private boolean ballIsOnPaddle;
	private int lives_left;
	
	//---------------------------------------
	// INSTANCE ATTRIBUTES THAT ARE NOT SAVED
	//---------------------------------------
	
	private boolean isPaused;
	private boolean paddleMovingRight = false;
	private boolean paddleMovingLeft = false;
	
	//---------------------------------------
	// PUBLIC INSTANCE METHODS
	//---------------------------------------
	
	public Level() {
		this.level_bricks = new HashMap<Integer,Brick>();
	}
	
	@Override
	public HashMap<Integer,Brick> getBricksAsHashMap(){ // experimental
		return this.level_bricks;
	}
	
	// Returns a new brick ID which is not already present in level_bricks.
	private int getNewUniqueId() {
		int newId;
		do {
			newId = (int)(Math.random() * MAX_ID_NUM) + BASE_ID_NUM; // number between BASE_ID_NUM and MAX_ID_NUM
		} while (this.isThisIdAlreadyUsed(newId));
		
		return newId;
	}
	
	
	@Override
	public boolean isThisIdAlreadyUsed(int id) {
		// The containsKey method returns true if the map contains a mapping for the specified key.
		return this.level_bricks.containsKey(new Integer(id));
	}
	
	// Adds a new brick into the structure. 
	@Override
	public int addNewBrick(Brick new_brick) {
		int addedId = getNewUniqueId();
		new_brick.setId(addedId);
		this.level_bricks.put(addedId, new_brick);
		return addedId;
	}
	
	// This method removes from the data structure the brick with the specified ID.
	// It eliminates the brick whether the brick can be hit or not.
	// If the brick is found and removed returns true, and false otherwise(ID not found).
	@Override
	public boolean removeBrickWithId(int id) {
		boolean removed = false;
		if(this.isThisIdAlreadyUsed(id)) {
			this.level_bricks.remove(new Integer(id));
			removed = true;
		}
		return removed;
	}
	
	// Returns the brick with the specified ID; if it's not found it returns null.
	@Override
	public Brick getBrickWithId(int id) {
		Brick brick_found = null;
		if(this.isThisIdAlreadyUsed(id)) {
			brick_found = this.level_bricks.get(new Integer(id));
		}
		return brick_found;
	}
	
	// "Hits" the brick with the specified id.
	// Returns true if the block is hit with success, and false otherwise. 
	@Override
	public boolean hitBrickWithId(int id) {
		boolean successful_hit = false;
		if(this.isThisIdAlreadyUsed(id)) {
			if(this.level_bricks.get(new Integer(id)).getHitsLeft() > 0 && this.level_bricks.get(new Integer(id)).isHittable()) {
				Level.getInstance().increaseScoreBy(Level.getInstance().getBrickWithId(id).getPoints());
				this.level_bricks.get(new Integer(id)).hit();
				if(this.level_bricks.get(new Integer(id)).getHitsLeft() == 0) {
					this.removeBrickWithId(id);
				}
				successful_hit = true;
			}
		}
		return successful_hit;
	}
	
	// This method removes all the bricks from the collection.
	@Override
	public void removeAllBricks() {
		this.level_bricks.clear();
	}
	
	// Returns the number of bricks present in the collection.
	@Override
	public int getNumberOfBricks() {
		return this.level_bricks.size();
	}
	
	@Override
	public int getNumberOfIndestructibleBricks() {
		int num = 0;
		for (Brick brick : Level.getInstance().getBricksAsHashMap().values()) {
			if(!brick.isHittable()) {
				num++;
			}
		}
		return num;
	}
	
	@Override
	public void setNewLevel(String playername) {
		this.loadLevelConfigurationFrom("levels/default");
		
		this.playerName = playername;
	}
	
	@Override
	public void loadLevelConfigurationFrom(String file) {
		try {
			LinkedList<String[]> myll = ReadCSV.getRows(file + ".txt","utf-8");
			
			// First row: player name, score, lives left.
			String[] generalLevelProprieties = myll.pop();
			
			setPlayerName(generalLevelProprieties[0]);
			setScore(Integer.parseInt(generalLevelProprieties[1]));
			setLivesLeft(Integer.parseInt(generalLevelProprieties[2]));
			
			// Then, the ball: balltype,x,y,radius,direction,speed,hexcolor
			String[] ballProprieties = myll.pop();
			
			setLevelBall(Ball.parseBall(ballProprieties));
			
			// Then: the paddle
			String[] paddleProprieties = myll.pop();
			
			setLevelPaddle(Paddle.parsePaddle(paddleProprieties));
			
			// At last: the bricks.
			ListIterator<String[]> iterator = myll.listIterator(0);
			while(iterator.hasNext()){
		        String[] brickProprieties = (String[])iterator.next();
		        
		        addNewBrick(Brick.parseBrick(brickProprieties));
		    }
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		finally {}
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("#playername,score,lives left\r\n");
		str.append(this.playerName + ";" + this.score + ";" + this.lives_left +"\r\n\r\n");
		
		str.append("#balltype,x,y,radius,direction,speed,hexcolor\r\n");
		str.append(this.level_ball.toString() + "\r\n\r\n");
		
		str.append("#paddletype,x,y,width,heigth,speed,hexcolor\r\n");
		str.append(this.level_paddle.toString() + "\r\n\r\n");
		
		str.append("#bricktype,x,y,width,height,hexcolor,hittable,hitsleft,points,powerupNumber\r\n");
		
		for (Brick brick : Level.getInstance().getBricksAsHashMap().values()) {
			str.append(brick.toString() + "\r\n");
		}
		
		return str.toString();
	}
	
	@Override
	public void increaseScoreBy(int amount) {
		this.score += amount;
	}
	
	public boolean isPaddleMovingLeft() {
		return paddleMovingLeft;
	}

	public void setPaddleMovingLeft(boolean paddleMovingLeft) {
		this.paddleMovingLeft = paddleMovingLeft;
	}

	public boolean isPaddleMovingRight() {
		return paddleMovingRight;
	}

	public void setPaddleMovingRight(boolean paddleMovingRight) {
		this.paddleMovingRight = paddleMovingRight;
	}
	
	

	@Override
	public Ball getLevelBall() {
		return level_ball;
	}

	@Override
	public void setLevelBall(Ball level_ball) {
		this.level_ball = level_ball;
	}

	@Override
	public Paddle getLevelPaddle() {
		return level_paddle;
	}

	@Override
	public void setLevelPaddle(Paddle level_paddle) {
		this.level_paddle = level_paddle;
	}
	
	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public boolean gameIsPaused() {
		return this.isPaused;
	}

	@Override
	public void pauseGame() {
		this.isPaused = true;
	}
	
	@Override
	public void resumeGame() {
		this.isPaused = false;
	}
	
	@Override
	public void decreaseLivesByOne() {
		this.lives_left--;
	}
	
	@Override
	public int getLivesLeft() {
		return lives_left;
	}

	@Override
	public void setLivesLeft(int lives_left) {
		this.lives_left = lives_left;
	}

	@Override
	public boolean isBallOnPaddle() {
		return ballIsOnPaddle;
	}

	@Override
	public void setBallIsOnPaddle(boolean ballIsOnPaddle) {
		this.ballIsOnPaddle = ballIsOnPaddle;
	}

	//---------------------------------------
	// PUBLIC STATIC METHODS
	//---------------------------------------
	public static ILevel getInstance() {
		if (instance == null)
			instance = new Level();
		return instance;
	}

	
}
