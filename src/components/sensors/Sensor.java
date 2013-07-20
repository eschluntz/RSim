package components.sensors;

import components.Robot;

import world.Position;
import world.Rsim;

public abstract class Sensor {

	//-----------------------[members]-----------------------------------------------
	Robot r;
	Rsim world;
	Position relative;
	String name;

	//-----------------------[Constructors]-----------------------------------------------
	public Sensor(Robot r, Rsim w) {
		this.r = r;
		this.world = w;
		this.relative = new Position();
		this.name = "";
		this.setup();
	}

	public Sensor(Robot r, Rsim w, Position p) {
		this(r,w);
		this.relative = p;
	}

	public Sensor(Robot r, Rsim w, Position p, String n) {
		this(r,w,p);
		this.name = n;
	}

	//-----------------------[functions]-----------------------------------------------
	// get correct position of sensor relative to world
	protected Position get_true() {
		double x = r.pos.x + relative.x * Math.cos(r.pos.theta) + relative.y * Math.sin(r.pos.theta);
		double y = r.pos.y + relative.y * Math.cos(r.pos.theta) + relative.x * Math.sin(r.pos.theta);
		double theta = r.pos.theta + relative.theta;

		return new Position(x,y,theta);
	}
	protected void go_true() {
		world.translate((float)r.pos.x, world.height - (float)r.pos.y);
		world.rotate(-(float)r.pos.theta);
		world.translate((float)relative.x, -(float)relative.y);
		world.rotate(-(float)relative.theta);
	}

	public void name(String s) {
		this.name = s;
	}
	public String get_name() {
		return this.name;
	}
	public void set_pos(Position p) {
		this.relative = p;
	}
	public void set_pos(double x, double y) {
		this.relative.x = x;
		this.relative.y = y;
	}
	public void set_pos(double x, double y, double theta) {
		this.set_pos(x, y);
		this.relative.theta = theta;
	}



	//-----------------------[abstracts]-----------------------------------------------
	// for each type of sensor to implement
	protected abstract void setup();
	protected abstract Position add_noise(Position a);
	public abstract Position sense();
	public abstract void render();
}
