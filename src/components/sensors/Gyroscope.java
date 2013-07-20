package components.sensors;

import components.Robot;

import world.Position;
import world.Rsim;

public class Gyroscope extends Sensor {
	/*
	 * returns a noisy version of the sensors current position
	 */
	//-----------------------[members]-----------------------------------------------
	double stddev;
	double sensitivity;

	//-----------------------[Constructors]-----------------------------------------------

	@Override
	protected void setup() {
		//this is called in every constructor from super
		stddev = 1;
		sensitivity = 10;
	}

	public Gyroscope(Robot r, Rsim w) {
		super(r, w);
	}
	public Gyroscope(Robot r, Rsim w, Position p) {
		super(r, w, p);
	}
	public Gyroscope(Robot r, Rsim w, Position p, String n) {
		super(r, w, p, n);
	}

	//-----------------------[abstract functions]-----------------------------------------------
	@Override
	protected Position add_noise(Position a) {
		a.x = 0;
		a.y = 0;
		return a;
	}

	@Override
	public Position sense() {
		return add_noise(get_true());
	}

	@Override
	public void render() {
		go_true();

		world.noStroke();
		world.fill(0,0,255);
		world.rectMode(world.CENTER);
		world.rect(0, 0, 10, 20);
		world.resetMatrix();
	}

}
