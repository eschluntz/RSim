package components.sensors;

import components.Robot;

import world.Position;
import world.Rsim;

public class Accelerometer extends Sensor {
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

	public Accelerometer(Robot r, Rsim w) {
		super(r, w);
	}
	public Accelerometer(Robot r, Rsim w, Position p) {
		super(r, w, p);
	}
	public Accelerometer(Robot r, Rsim w, Position p, String n) {
		super(r, w, p, n);
	}

	//-----------------------[abstract functions]-----------------------------------------------

	@Override
	protected Position add_noise(Position a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position sense() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

}
