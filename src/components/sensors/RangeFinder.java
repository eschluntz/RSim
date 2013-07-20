package components.sensors;

import components.Robot;

import world.Obstacle;
import world.Position;
import world.Rsim;

public class RangeFinder extends Sensor {
	/*
	 * returns
	 */
	//-----------------------[members]-----------------------------------------------
	double stddev;
	double sensitivity;
	double range;

	//-----------------------[Constructors]-----------------------------------------------

	@Override
	protected void setup() {
		//this is called in every constructor from super
		stddev = 1;
		sensitivity = 10;
		range = 400;
	}

	public RangeFinder(Robot r, Rsim w) {
		super(r, w);
	}
	public RangeFinder(Robot r, Rsim w, Position p) {
		super(r, w, p);
	}
	public RangeFinder(Robot r, Rsim w, Position p, String n) {
		super(r, w, p, n);
	}

	//-----------------------[abstract functions]-----------------------------------------------


	@Override
	protected Position add_noise(Position a) {
		return a;
	}

	@Override
	public Position sense() {
		Position tru = get_true();
		Position target = tru.add(new Position( -this.range * Math.sin(tru.theta), this.range * Math.cos(tru.theta)));
		Position closest = target; // using theta as distance
		closest.theta = tru.length(closest);

		// going through each obstacle in the world and looking for intersections
		for (Obstacle o: world.env) {
			Position temp = o.intersect(tru, target);

			// there could be no intersection
			if (temp != null) {

				// if distance to intersection < closest distance to intersection, replace
				if (temp.length(tru) < closest.theta) {
					temp.theta = temp.length(tru);
					closest = temp;
				}
			}
		}

		world.ellipse((float)closest.x, world.height - (float)closest.y, 20, 20);


		return add_noise(closest); // x and y position of the nearest sensed obstacle, and theta is the distance
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
