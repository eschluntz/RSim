package components.actuators;

import components.Robot;

import world.Position;
import world.Rsim;

public class Thruster extends Actuator {

	//-----------------------[members]-----------------------------------------------
	double max_force;
	double accuracy;

	//-----------------------[Constructors]-----------------------------------------------

	@Override
	protected void setup() {
		max_force = 2;
		accuracy = 100;
	}
	public Thruster(Robot r, Rsim w) {
		super(r, w);
	}
	public Thruster(Robot r, Rsim w, Position p) {
		super(r, w, p);
	}
	public Thruster(Robot r, Rsim w, Position p, String n) {
		super(r, w, p, n);
	}


	//-----------------------[abstract functions]-----------------------------------------------
	@Override
	public void act(double p) {
		double f = p * max_force;
		if (f > max_force) f = max_force;
		else if (f < - max_force) f = - max_force;

		// applying f to robot
		Position pos = get_true();
		double fx = -f * Math.sin(pos.theta);
		double fy = f * Math.cos(pos.theta);

		// torque is the cross product of the lever arm and the force vectors
		Position fcom = new Position(-f*Math.sin(relative.theta), f*Math.cos(relative.theta));
		double torque = cross(relative, fcom);


		r.force(new Position(fx,fy,torque));
	}

	@Override
	public void render() {
		go_true();
		world.noStroke();
		world.fill(255,0,0);
		world.rectMode(world.CENTER);
		world.rect(0, 0, 10, 20);
		world.resetMatrix();
	}

}
