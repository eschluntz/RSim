package components;

import java.util.ArrayList;

import components.actuators.Actuator;
import components.sensors.Sensor;

import world.Position;
import world.Rsim;


import behaviors.Behavior;

public class Robot {

	//---------------------------[physical characteristics]--------------------------------------
	// position
	public Position pos;
	public Position speed;
	public Position force;

	double m; // mass of the robot
	double moment; // rotational inertia of the robot

	//---------------------------[components]--------------------------------------
	ArrayList<Sensor> sensors;
	ArrayList<Actuator> motors;
	Behavior behavior;
	Rsim parent;


	//---------------------------[update/render]--------------------------------------

	// update position and velocity and stuff from accumulated torques and forces
	public void update(double t) {

		// drive the robot
		behavior.behave(t);

		// do physics and stuff
		pos.x += speed.x * t;
		pos.y += speed.y * t;

		speed.x += force.x / m * t;
		speed.y += force.y / m * t;

		// rotation
		pos.theta += speed.theta * t;
		pos.theta = pos.theta%(2*Math.PI);
		if (pos.theta > Math.PI) {
			pos.theta = pos.theta - 2*Math.PI;
		}
		else if (pos.theta < - Math.PI) {
			pos.theta = pos.theta + 2*Math.PI;
		}
		speed.theta += force.theta / moment * t;

		// resetting for next tick
		force.reset();
	}

	// draw the robot on the screen, also draw all sensors and motors and such
	public void render() {
		draw_robot();

		for (Sensor s: sensors) {
			s.render();
		}
		for (Actuator m: motors) {
			m.render();
		}
	}

	//---------------------------[constructors]--------------------------------------
	public Robot(Rsim parent) {
		this.parent = parent;
		this.behavior = null;
		this.sensors = new ArrayList<Sensor>();
		this.motors = new ArrayList<Actuator>();

		pos = new Position();
		speed = new Position();
		force = new Position();

		m = 1;
		moment = 1000;
	}

	public Robot(Rsim parent, double x, double y) {
		this(parent);

		pos = new Position(x,y);
	}

	public Robot(Rsim parent, double x, double y, double theta) {
		this(parent);

		pos = new Position(x,y,theta);
	}

	public Robot(Rsim parent, Position pos) {
		this(parent);

		this.pos = pos;
	}

	public Robot(Rsim parent, Position pos, Position speed) {
		this(parent, pos);
		this.speed = speed;
	}

	//---------------------------[utility functions]--------------------------------------
	public ArrayList<Sensor> get_sensors() {
		return this.sensors;
	}
	public ArrayList<Actuator> get_motors() {
		return this.motors;
	}
	public void set_pos(double x, double y) {
		pos.x = x;
		pos.y = y;
	}
	public void set_pos(Position p) {
		pos = p;
	}
	public void set_speed(double vx, double vy) {
		speed.x = vx;
		speed.y = vy;
	}
	public void set_speed(Position p) {
		speed = p;
	}
	public void set_rotation(double theta, double omega) {
		pos.theta = theta;
		speed.theta = omega;
	}
	public void set_inertia(double m, double moment) {
		this.m = m;
		this.moment = moment;
	}
	public void force(double x, double y) {
		force.x += x;
		force.y += y;
	}
	public void force(Position p) {
		force.x += p.x;
		force.y += p.y;
		force.theta += p.theta;
	}
	public void accelerate(Position p) {
		force.x += p.x * m;
		force.y += p.y * m;
		force.theta += p.theta * moment;
	}
	public void accelerate(double x, double y) {
		force.x += x * m;
		force.y += y * m;
	}
	private void draw_robot() {
		parent.noStroke();
		parent.fill(0,255,0);
		parent.translate((float)pos.x, parent.height - (float)pos.y);
		parent.rotate(-(float)pos.theta);
		parent.triangle(0, -20, 10, 10, -10, 10);
		parent.resetMatrix();
		parent.translate(50,50);
		parent.rotate(-(float)pos.theta);
		parent.triangle(0, -20, 10, 10, -10, 10);
		parent.stroke(255);
		parent.strokeWeight(2);
		parent.resetMatrix();
		parent.line(50, 50, 50 + (float)speed.x, 50 -(float)speed.y);
	}
	public void add_behavior(Behavior b) {
		this.behavior = b;
	}
	public Behavior get_behavior() {
		return this.behavior;
	}

	//---------------------------[building functions]--------------------------------------
	public void add_sensor(Sensor s) {
		this.sensors.add(s);
	}
	public void add_motor(Actuator a) {
		this.motors.add(a);
	}
}
