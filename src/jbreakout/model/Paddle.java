package jbreakout.model;

import static jbreakout.util.MathTools.toHexString;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import jbreakout.util.MathTools;

public class Paddle extends Rectangle2D.Double {
	
	private static final Color DEFAULT_PADDLE_COLOR = Color.RED;
	private static final double DEFAULT_PADDLE_SPEED = 1.0;
			
	// Position of the top left vertex of the paddle,
	// relative to the X and Y axis originating there.
	// the X axis is oriented left to right.
	// the Y axis is oriented top to bottom.
	
	// The paddle's color.
	private Color color;
	
	// The paddle speed movement.
	private double speed;
	
	
	public static Paddle parsePaddle(String[] paddleProprieties) {
		
		int[] rgb = MathTools.hexStringToRgbConverter(paddleProprieties[6]);
		
		Paddle newPaddle = new Paddle(java.lang.Double.parseDouble(paddleProprieties[1]),
										 java.lang.Double.parseDouble(paddleProprieties[2]),
										 java.lang.Double.parseDouble(paddleProprieties[3]),
										 java.lang.Double.parseDouble(paddleProprieties[4]),
										 java.lang.Double.parseDouble(paddleProprieties[5]),
										 new Color(rgb[0],rgb[1],rgb[2]));
		
		return newPaddle;
	}
	
	
	//---------------------------------------
	// CONSTRUCTORS
	//---------------------------------------
		
	public Paddle(double x, double y, double w, double h, double fast, Color col) {	
		super(x,y,w,h);
		this.speed = fast;
		this.color = col;
	}
	
	public Paddle(double x, double y, double w, double h) {
		this(x,y,w,h,DEFAULT_PADDLE_SPEED,DEFAULT_PADDLE_COLOR);
	}
	
	public Paddle(Paddle paddle) {
		super(paddle.x,paddle.y,paddle.width,paddle.height);
		this.speed = paddle.speed;
		this.color = paddle.getColor();
	}
	
	
	public String toString() {
		String s = "paddle;";
		s += this.x + ";" + this.y + ";" + this.width + ";" + this.height + ";";
		s += this.speed + ";" + toHexString(this.color);
		return s;
	}
	
	
	public void setGeometry(Paddle paddle) {
		this.x = paddle.x;
		this.y = paddle.y;
		this.width = paddle.width;
		this.height = paddle.height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
		
	
}
