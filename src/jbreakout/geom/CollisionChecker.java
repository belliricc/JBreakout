package jbreakout.geom;

import static jbreakout.util.MathTools.clamp;

import java.awt.geom.Rectangle2D;

import jbreakout.model.Ball;
import jbreakout.model.Brick;
import jbreakout.model.Level;
import jbreakout.model.Paddle;

public class CollisionChecker {
		
	// This method returns an int containing the ID of the brick that the ball hit.
	// If no bricks were hit, it returns 0.
	public static int checkBrickHit(Ball checkBall) {
		int ballHitId = 0;
		
		for (Brick brick : Level.getInstance().getBricksAsHashMap().values()) {
			if(circleBoxColliding(checkBall, brick)) {
				ballHitId = brick.getId();
				break;
			}
		}
		return ballHitId;
	}
	
	
	// This method checks if the paddle hit the ball.
	public static boolean checkPaddleHit(Ball checkBall) {
		return circleBoxColliding(checkBall, Level.getInstance().getLevelPaddle());
	}
	
	// This method returns a double from -0.9 to 0.9 depending whether the ball hit the paddle.
	// -0.9 is for a close hit to the left, 0.9 to the right and 0.0 is a hit to the center.
	public static double howCloseToTheCenter(Ball checkBall, Paddle checkPaddle) {
		return clamp((checkPaddle.getX() + checkPaddle.getWidth() / 2 - checkBall.getCenterX()) / (checkPaddle.getWidth() / 2), -0.9, 0.9);
	}
	
	
	// This method checks if a wall is hit.
	// It returns 0 if no wall is hit; otherwise:
	// 1 for top corner;
	// 2 for right corner;
	// 3 for bottom corner;
	// 4 for left corner.
	public static int checkBorderHit(Ball checkBall) {
		int hit = 0;
		
		if(checkBall.getY() < 0.0) { // Top corner check
			hit = 1;
		} else if(checkBall.getX() + 2.0 * checkBall.getRadius() > Level.LEVEL_WIDTH) { // Right corner check
			hit = 2;
		} else if(checkBall.getY() + 2.0 * checkBall.getRadius() > Level.LEVEL_HEIGHT) { // Bottom corner check
			hit = 3;
		} else if(checkBall.getX() < 0.0) { // Left corner check
			hit = 4;
		}
		
		return hit;
	}
	
	// This method checks where a brick is hit.
	// It returns:
	// 1 for top hit;
	// 2 for right hit;
	// 3 for bottom hit;
	// 4 for left hit;
	// 5 for top-right edge;
	// 6 for right-bottom edge;
	// 7 for bottom-left edge;
	// 8 for left-top edge.
	public static int checkBrickSideHit(Ball checkBall, Brick checkBrick) {
		int hit = 0;
		
		if(checkBall.getCenterY() < checkBrick.getCenterY() &&
		   checkBall.getCenterX() > checkBrick.getX() &&
		   checkBall.getCenterX() < checkBrick.getMaxX()) {
			// Top side hit
			hit = 1;
		}
		
		if(checkBall.getCenterX() > checkBrick.getMaxX() &&
		   checkBall.getCenterY() > checkBrick.getY() &&
		   checkBall.getCenterY() < checkBrick.getMaxY()) {
			// Right side hit
			hit = 2;
		}
		
		if(checkBall.getCenterY() > checkBrick.getCenterY() &&
		   checkBall.getCenterX() > checkBrick.getX() &&
		   checkBall.getCenterX() < checkBrick.getMaxX()) {
			// Bottom side hit
			hit = 3;
		}
		
		if(checkBall.getCenterX() < checkBrick.getCenterX() &&
		   checkBall.getCenterY() > checkBrick.getY() &&
		   checkBall.getCenterY() < checkBrick.getMaxY()) {
			// Left side hit
			hit = 4;
		}
		
		if(checkBall.getCenterX() > checkBrick.getMaxX() &&
		   checkBall.getCenterY() < checkBrick.getY()) {
			// Top-right edge hit
			hit = 5;
		}
		
		if(checkBall.getCenterX() > checkBrick.getMaxX() &&
		   checkBall.getCenterY() > checkBrick.getMaxY()) {
			// Right-bottom edge hit
			hit = 6;
		}
		
		if(checkBall.getCenterX() < checkBrick.getX() &&
		   checkBall.getCenterY() > checkBrick.getMaxY()) {
			// Bottom-left edge hit
			hit = 7;
		}
		
		if(checkBall.getCenterX() < checkBrick.getX() &&
		   checkBall.getCenterY() < checkBrick.getY()) {
			// Left-top edge hit
			hit = 8;
		}
		
		return hit;
	}
	
	
	// This method is just to improve readability.
	public static boolean circleBoxColliding(Circle2D circle, Rectangle2D.Double rectangle) {
		return circle.intersects(rectangle);
	}
}