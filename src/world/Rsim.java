package world;
import java.util.ArrayList;

import components.Robot;
import components.actuators.Thruster;
import components.sensors.GPS;
import components.sensors.Gyroscope;
import components.sensors.RangeFinder;

import behaviors.PIDHover;
import behaviors.QuadFollow;
import processing.core.*;

public class Rsim extends PApplet {

	int t;
	Position target;

	public ArrayList<Robot> robots;
	public ArrayList<Obstacle> env;// short for environment

	public void setup() {
	    size(1200,800);
	    background(0);
	    target = new Position(width/2, height/2);
	    t = 0;

	    robots = new ArrayList<Robot>();
	    env = new ArrayList<Obstacle>();

	    robots.add(make_quadcopter(this, width/2, height/2));
	    env.add(new Obstacle(this, 50,50, width - 50, 50));
	    env.add(new Obstacle(this, 50,50, 50, height - 50));
	}

	public void draw() {
		background(0);
		fill(255);
		ellipse(Math.round(target.x),Math.round(height - target.y), 10,10);

		for (Robot r: robots) {
			r.render();
			r.accelerate(0, -1);
			r.update(.3);
		}
		for (Obstacle o: env) {
			o.render();
		}

		t += 1;
	}

	// ----------------------[ robot types ] --------------------


	public Robot make_hoverbot(Rsim p, double x, double y) {
		Robot r = new Robot(p, 200,200);

	    r.add_sensor(new GPS(r, this, new Position(0,0,0), "gps"));
	    r.add_motor(new Thruster(r,this, new Position(0,0,0), "center"));
	    r.add_behavior(new PIDHover(r,this, 600));

	    return r;
	}

	public Robot make_quadcopter(Rsim p, double x, double y) {
		Robot r = new Robot(p, width/2, height/2);
		r.set_rotation(PI/4, 0);

	    r.add_sensor(new GPS(r, this, new Position(), "gps"));
	    r.add_sensor(new Gyroscope(r, this, new Position(), "gyro"));
	    r.add_sensor(new RangeFinder(r, this, new Position(0,0, PI), "range"));
	    r.add_motor(new Thruster(r,this, new Position(-20,0,0), "left"));
	    r.add_motor(new Thruster(r,this, new Position(20,0,0), "right"));
	    r.add_behavior(new QuadFollow(r,this, new Position(600,400)));

	    return r;
	}

	public void mouseClicked() {
		target.x= mouseX;
		target.y = height - mouseY;
		robots.get(0).get_behavior().command(target);
	}
}












