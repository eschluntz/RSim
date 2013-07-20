package behaviors;

import components.Robot;
import components.actuators.Thruster;
import components.sensors.GPS;
import components.sensors.Gyroscope;

import world.Position;
import world.Rsim;

public class QuadFollow extends Behavior {
	/*
	 * A simple behavior that makes the robot hover at a certain height
	 * sensor(0) should be a GPS or range finder
	 * motor(0) should be the left thruster
	 * motor(1) should be the right thruster
	 */

	Position target;
	Position pos;
	Position speed;
	Position sum;
	Position error;

	Position p_coef;
	Position i_coef;
	Position d_coef;

	double max_theta;

	//-----------------------[Constructors]----------------------
	public QuadFollow(Robot r, Rsim parent) {
		super(r, parent);
		target = new Position();
		pos = new Position();
		speed = new Position();
		sum = new Position();
		error = new Position();

		max_theta = parent.PI/2;

		p_coef = new Position(-.001,.02,.5);
		i_coef = new Position(-.00000,.000,0);
		d_coef = new Position(.15,-1,-10);

	}
	public QuadFollow(Robot r, Rsim parent, Position p) {
		this(r, parent);
		this.target = p;
	}

	//-----------------------[Behavior]----------------------
	@Override
	public void behave(double t) {

		/*
		 * priorities:
		 * 	> maintain angle
		 *  > maintain altitude
		 *  > go to correct place
		 */

		// getting sensor values
		Position temp = r.get_sensors().get(0).sense(); //gps
		temp.theta = r.get_sensors().get(1).sense().theta; //gyro

		//r.get_sensors().get(2).sense();

		speed = temp.subtract(pos);
		pos = temp;
		error = target.subtract(pos);
		sum = sum.add(error);

		double upwards = error.y * p_coef.y + speed.y * d_coef.y + sum.y * i_coef.y;
		double xdiff = error.x * p_coef.x + speed.x * d_coef.x + sum.x * i_coef.x;
		double diff = error.theta * p_coef.theta + speed.theta * d_coef.theta + sum.theta * i_coef.theta;

		if (xdiff > max_theta) {
			target.theta = max_theta;
		}
		else if (xdiff < - max_theta) {
			target.theta = - max_theta;
		}
		else {
			target.theta = xdiff;
		}
		if (upwards > 1) upwards = .9;
		if (upwards < 0) upwards = 0;

		r.get_motors().get(0).act(upwards - diff);
		r.get_motors().get(1).act(upwards + diff);


	}




	@Override
	public void debug() {
		//no much
	}

	@Override
	public void command(Position p) {
		this.target = p;
	}

}


