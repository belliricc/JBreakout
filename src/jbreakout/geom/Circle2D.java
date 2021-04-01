package jbreakout.geom;

import java.awt.geom.Ellipse2D;

public class Circle2D extends Ellipse2D.Double {
	
	protected Circle2D(double x, double y, double r) {
		super(x - r, y - r, 2 * r, 2 * r);
	}
	
	public double getRadius() {
		return this.width / 2.0;
	}
	
}
