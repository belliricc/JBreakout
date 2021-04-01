package jbreakout.model;

import static jbreakout.util.MathTools.toHexString;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import jbreakout.util.MathTools;

public class Brick extends Rectangle2D.Double {
	
	// TO NOTICE: the "brick" is in fact a rectangle.
	// I'm just gonna keep calling it brick from now on.

	private static final Color DEFAULT_BRICK_COLOR = Color.RED;
	private static final boolean DEFAULT_HITTABLE = true;
	private static final int DEFAULT_HITS_LEFT = 1;
	private static final int DEFAULT_BRICK_POINTS = 10;
	private static final int DEFAULT_POWERUP = 0; // 0 is for NO powerup.

	
	// Position of the top left vertex of the brick,
	// relative to the X and Y axis originating there.
	// the X axis is oriented left to right.
	// the Y axis is oriented top to bottom.
	
	// The brick's color.
	private Color color;
	
	// Some bricks may not take hits, and can only be destroyed via special methods.
	private boolean hittable;
	
	// Some bricks may take one or more hits to destroy.
	private int hits_left;
	
	// The brick's points.
	private int points;
	
	// The ID of a brick is just a random integer.
	// In some cases the ID might be a reserved code.
	private int id;
	
	// The powerup's number; the legend is:
	// 0 for no powerup;
	// 1 for bigger ball;
	// 2 for larger paddle;
	// 3 for faster paddle.
	private int powerupNumber;
	
	
	public static Brick parseBrick(String[] brickProprieties) {
        
        brickProprieties = Arrays.copyOfRange(brickProprieties, 1, brickProprieties.length);
        		        
        Brick newBrick = new Brick(java.lang.Double.parseDouble(brickProprieties[0]),
        						   java.lang.Double.parseDouble(brickProprieties[1]),
        						   java.lang.Double.parseDouble(brickProprieties[2]),
        						   java.lang.Double.parseDouble(brickProprieties[3]));
                		        
        if(brickProprieties.length > 4) {
        	int[] rgb = MathTools.hexStringToRgbConverter(brickProprieties[4]);
        	newBrick.setColor(new Color(rgb[0],rgb[1],rgb[2]));
        }
        
        if(brickProprieties.length >= 5) {
        	newBrick.setHittable(Boolean.parseBoolean(brickProprieties[5]));
        }
        
        if(brickProprieties.length >= 6) {
        	newBrick.setHitsLeft(Integer.parseInt(brickProprieties[6]));
        }
        
        if(brickProprieties.length >= 7) {
        	newBrick.setPoints(Integer.parseInt(brickProprieties[7]));
        }
        
        if(brickProprieties.length >= 7) {
        	newBrick.setPowerupNumber(Integer.parseInt(brickProprieties[8]));
        }
        
        return newBrick;
	}
	
	
	//---------------------------------------
	// CONSTRUCTORS
	//---------------------------------------
	
	public Brick(double x, double y, double w, double h, Color col, boolean hittable, int hitsLeft, int points, int powNum) {	
		super(x,y,w,h);
		this.color = col;
		this.hittable = hittable;
		this.hits_left = hitsLeft;
		this.points = points;
		this.powerupNumber = powNum;
	}
	
	public Brick(double x, double y, double w, double h, Color col) {
		this(x,y,w,h,col,DEFAULT_HITTABLE,DEFAULT_HITS_LEFT,DEFAULT_BRICK_POINTS,DEFAULT_POWERUP);
	}
	
	public Brick(double x, double y, double w, double h) {
		this(x,y,w,h,DEFAULT_BRICK_COLOR,DEFAULT_HITTABLE,DEFAULT_HITS_LEFT,DEFAULT_BRICK_POINTS,DEFAULT_POWERUP);
	}
	
	
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	// Sets the current brick identical to the specified values.
	public void setBrick(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public String toString() {
		String s = "brick;";
		s += this.x + ";" + this.y + ";" + this.width + ";" + this.height + ";";
		s += toHexString(this.color) + ";" + this.hittable + ";" + this.hits_left + ";" + this.points + ";" + this.powerupNumber;
		return s;
	}
	
	public void hit() {
		this.hits_left--;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isHittable() {
		return this.hittable;
	}
	
	public int getHitsLeft() {
		return this.hits_left;
	}
	
	public void setHittable(boolean hittable) {
		this.hittable = hittable;
	}
	
	public void setHitsLeft(int hits) {
		this.hits_left = hits;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}


	public int getPowerupNumber() {
		return powerupNumber;
	}

	public void setPowerupNumber(int powerupNumber) {
		this.powerupNumber = powerupNumber;
	}
	
}
