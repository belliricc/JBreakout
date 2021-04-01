package jbreakout.geom;

public class Vector2D {
	
	private final static Vector2D xAxisDirection = new Vector2D(1.0,0.0);
	private final static Vector2D yAxisDirection = new Vector2D(0.0,1.0);
	
	protected double x;
	protected double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
		this.normalize();
	}
	
	private void normalize() {
		double length = Math.sqrt((this.x * this.x) + (this.y * this.y));
		this.x /= length;
		this.y /= length;
	}
	
	public void rotateBy(double theta) {
		double oldX = this.x;
		double oldY = this.y;
		
		this.setX(oldX * Math.cos(theta) - oldY * Math.sin(theta));
		this.setY(oldX * Math.sin(theta) + oldY * Math.cos(theta));
	}
	
	public void setAngle(double theta) {
		this.rotateBy(this.getAngle() - theta);
	}
	
	public double getAngleBetween(Vector2D vec) {
		return Math.atan2( this.x*vec.y - this.y*vec.x, this.x*vec.x + this.y*vec.y );
	}
	
	public double getAngle() {
		return Math.acos(this.x / Math.sqrt(this.x * this.x + this.y * this.y));
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
