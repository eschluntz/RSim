package world;

public class Position {
	public double x;
	public double y;
	public double theta;

	public Position() {
		x = 0;
		y = 0;
		theta = 0;
	}

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
		this.theta = 0;
	}

	public Position(double x, double y, double theta) {
		this(x,y);
		this.theta = theta;
	}

	public void reset() {
		x = 0;
		y = 0;
		theta = 0;
	}

	public Position sub(Position p) {
		return this.subtract(p);
	}
	public Position subtract(Position p) {
		return new Position(x - p.x, y - p.y, theta - p.theta);
	}
	public Position add(Position p) {
		return new Position(x + p.x, y + p.y, theta + p.theta);
	}
	public void db() {
		System.out.println(x + " " + y + " " + theta);
	}
	public double cross(Position p) {
		return (x * p.y - y * p.x);
	}
	public double length(Position p) {
		return Math.sqrt((x - p.x)*(x - p.x) + (y - p.y)*(y - p.y));
	}
	public double length() {
		return this.length(new Position());
	}
}
