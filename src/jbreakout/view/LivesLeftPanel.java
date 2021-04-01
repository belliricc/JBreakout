package jbreakout.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import jbreakout.model.Ball;
import jbreakout.model.Level;

public class LivesLeftPanel extends JPanel {
	
	private final static Color DEFAULT_OUTLINE_COLOR = Color.BLACK;
	
	public LivesLeftPanel() {
		super();
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		// This panel's dimension: 260x68.
		Ball ball = new Ball(Level.getInstance().getLevelBall());
		for(int i = 0; i < Level.getInstance().getLivesLeft(); i++) {
			ball.setFrame(20 + i * 80, 9, 50.0, 50.0);
			g2d.setColor(ball.getColor());
			g2d.fill(ball);
			g2d.setColor(DEFAULT_OUTLINE_COLOR);
			g2d.draw(ball);
		}
		
		
	}
}
