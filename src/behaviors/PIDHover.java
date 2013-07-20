package behaviors;

import components.Robot;

import world.Position;
import world.Rsim;

public class PIDHover extends Behavior {

	/*
	 * A simple behavior that makes the robot hover at a certain height
	 * sensor(0) should be a GPS or range finder
	 * motor(0) should be a downward facing thruster
	 */

	double target;
	Position pos;
	Position speed;
	double i;

	double p_coef;
	double i_coef;
	double d_coef;




	//-----------------------[Constructors]----------------------
	public PIDHover(Robot r, Rsim parent) {
		super(r, parent);
		this.target = 500;
		pos = new Position();
		speed = new Position();
		i = 0;

		p_coef = .08;
		i_coef = .001;
		d_coef = -1;

	}
	public PIDHover(Robot r, Rsim parent, double h) {
		this(r, parent);
		this.target = h;
	}

	//-----------------------[Behavior]----------------------
	@Override
	public void behave(double t) {
		// call sensors and actuators
		Position temp = r.get_sensors().get(0).sense();

		speed.x = temp.x - pos.x;
		speed.y = temp.y - pos.y;
		speed.theta = temp.theta - pos.theta;

		pos = temp;

		double p = target - pos.y;
		double d = speed.y;
		i += p;

		double f = p*p_coef + d*d_coef + i*i_coef;

		r.get_motors().get(0).act(f);
	}




	@Override
	public void debug() {
		//no much
	}

	@Override
	public void command(Position p) {
		this.target = p.y;
	}

}
