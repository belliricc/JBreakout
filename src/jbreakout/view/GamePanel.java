package jbreakout.view;

import static jbreakout.util.MathTools.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import jbreakout.controller.ControllerForView;
import jbreakout.geom.Vector2D;
import jbreakout.model.Ball;
import jbreakout.model.Brick;
import jbreakout.model.Level;
import jbreakout.model.Paddle;
import jbreakout.util.Config;

public class GamePanel extends JPanel implements KeyListener {
	
	private final static Color DEFAULT_OUTLINE_COLOR = Config.getInstance().getDefaultOutlineColor();
	
	private Ball ball;
	
	private Paddle paddle;
			
	public GamePanel() {
		super();
		
		this.ball = new Ball(Level.getInstance().getLevelBall());
		this.paddle = new Paddle(Level.getInstance().getLevelPaddle());
		
		// The background color; where "nothing is drawn".
		this.setBackground(Config.getInstance().getBackgroundColor());
				
		this.addKeyListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
				
		// Scale accordingly to the panel's size.
		g2d.scale(GameWindow.getInstance().getSx(), GameWindow.getInstance().getSy());
				
		// Draw the bricks:		
		for (Brick brick : Level.getInstance().getBricksAsHashMap().values()) {
			
			if(!brick.isHittable()) {
				g2d.setColor(brick.getColor().brighter());
			} else if(brick.getPowerupNumber() != 0 && ControllerForView.getInstance().getFrameCounter() % randomNumberInclusive(10, 15) == 0) { // The brick contains a special power!
				brick.setColor(randomColor().brighter());
			} else {
				if(brick.getHitsLeft() == 2) {
					g2d.setColor(brick.getColor().darker());
				} else if(brick.getHitsLeft() > 2){
					g2d.setColor(brick.getColor().darker().darker());
				} else {
					g2d.setColor(brick.getColor());
				}
			}
			
			g2d.fill(brick);
			g2d.setColor(DEFAULT_OUTLINE_COLOR);
			g2d.draw(brick);
		}
		
		
		// Then draw the ball:
		this.ball.setBallGeometry(Level.getInstance().getLevelBall());
		g2d.setColor(this.ball.getColor());
		g2d.fill(this.ball);
		g2d.setColor(DEFAULT_OUTLINE_COLOR);
		g2d.draw(this.ball);
		
		
		// Then draw the paddle:
		this.paddle.setGeometry(Level.getInstance().getLevelPaddle());
		g2d.setColor(this.paddle.getColor());
		g2d.fill(this.paddle);
		g2d.setColor(DEFAULT_OUTLINE_COLOR);
		g2d.draw(this.paddle);
	}
		
	@Override
	public void keyTyped(KeyEvent e) {	
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:{
			ControllerForView.getInstance().rightArrowPressed();
			break;
		}
		case KeyEvent.VK_LEFT:{
			ControllerForView.getInstance().leftArrowPressed();
			break;
		}
		case KeyEvent.VK_P:{
			ControllerForView.getInstance().pressedKeyP();
			break;
		}
		case KeyEvent.VK_SPACE:{
			ControllerForView.getInstance().pressedSpace();
			break;
		}
		default:
			break;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:{
			ControllerForView.getInstance().rightArrowReleased();
			break;
		}
		case KeyEvent.VK_LEFT:{
			ControllerForView.getInstance().leftArrowReleased();
			break;
		}
		
		
		default:
			break;
		}
	}

}
