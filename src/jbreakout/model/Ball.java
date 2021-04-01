package jbreakout.model;

import static jbreakout.util.MathTools.toHexString;

import java.awt.Color;

import jbreakout.geom.Circle2D;
import jbreakout.geom.Vector2D;
import jbreakout.util.MathTools;

public class Ball extends Circle2D {
	
	private static final Color DEFAULT_BALL_COLOR = Color.GRAY;
	private static final Vector2D DEFAULT_DIRECTION = new Vector2D(0.0,1.0);
	private static final double DEFAULT_BALL_SPEED = 1.0;	
	
	// The ball's color.
	private Color color;
	
	// The ball's direction.
	private Vector2D direction;
	
	// The ball's speed modifier.
	private double speed;
	
	
	public static Ball parseBall(String[] ballProprieties) {
		
		Vector2D ballDirection = new Vector2D(java.lang.Double.parseDouble(ballProprieties[4].split(",")[0]),java.lang.Double.parseDouble(ballProprieties[4].split(",")[1]));
		int[] rgb = MathTools.hexStringToRgbConverter(ballProprieties[6]);
    	Color ballColor = new Color(rgb[0],rgb[1],rgb[2]);
		Ball newBall = new Ball(java.lang.Double.parseDouble(ballProprieties[1]),
   								   java.lang.Double.parseDouble(ballProprieties[2]),
								   java.lang.Double.parseDouble(ballProprieties[3]),
								   ballColor,
								   ballDirection,
								   java.lang.Double.parseDouble(ballProprieties[5]));
		
		return newBall;
	}
	
	
	//---------------------------------------
	// CONSTRUCTORS
	//---------------------------------------
	public Ball(double x, double y, double r, Color c, Vector2D vect, double fast) {
		super(x,y,r);
		this.color = c;
		this.direction = vect;
		this.speed = fast;
	}
	
	public Ball(Ball ball) {
		super(ball.x,ball.y,ball.getRadius());
		this.speed = ball.speed;
		this.color = ball.getColor();
		this.direction = ball.getDirection();
	}
	
	public Ball(double x, double y, double r, Color c) {
		this(x,y,r,c,DEFAULT_DIRECTION,DEFAULT_BALL_SPEED);
	}
	
	public Ball(double x, double y, double r) {
		this(x, y, r, DEFAULT_BALL_COLOR);
	}
	
	public String toString() {
		String s = "ball;";
		s += this.getCenterX() + ";" + this.getCenterY() + ";" + this.getRadius() + ";";
		s += this.direction.getX() + "," + this.direction.getY() + ";" + this.speed + ";" + toHexString(this.color);
		// Notice the single comma between the direction's x and y: It's not an error.
		return s;
	}
	
	//---------------------------------------
	// PUBLIC INSTANCE METHODS
	//---------------------------------------
	
	public void applyDirection(Vector2D dir, double speedFactor) {
		this.x += (dir.getX() * speedFactor);
		this.y += (dir.getY() * speedFactor);
	}
	
	public void applyOwnDirection() {
		this.applyDirection(this.direction, this.speed);
	}
	
	
	//---------------------------------------
	// GETTERS/SETTERS
	//---------------------------------------
	
	public void setBallGeometry(Ball ball) {
		this.x = ball.x;
		this.y = ball.y;
		this.width = ball.width;
		this.height = ball.height;
	}
	
	public void setBallGeometry(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public void setBallGeometry(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.width = r * 2.0;
		this.height = r * 2.0;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}
	
	public void setDirection(double x, double y) {
		this.direction.setX(x);
		this.direction.setY(y);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
