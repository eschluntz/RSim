package behaviors;

import components.Robot;

import world.Position;
import world.Rsim;

public abstract class Behavior {
	/*
	 * Should access only the sensors and motors of its robot
	 */

	Robot r;
	Rsim parent;

	// constructors
	public Behavior(Robot r, Rsim parent) {
		this.r = r;
		this.parent = parent;
	}

	public abstract void behave(double t);
	public abstract void debug();
	public abstract void command(Position p);
}
