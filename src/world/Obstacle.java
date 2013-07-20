package world;

import java.awt.Point;

public class Obstacle {
	Position a;
	Position b;

	Rsim parent;

	public Obstacle(Rsim p, Position a, Position b) {
		this.a = a;
		this.b = b;
		this.parent = p;
	}

	public Obstacle(Rsim p, double x1, double y1, double x2, double y2) {
		this.a = new Position(x1,y1);
		this.b = new Position(x2,y2);
		this.parent = p;
	}

	public void render() {
		parent.stroke(255);
		parent.strokeWeight(4);
		parent.line((float)a.x, parent.height - (float)a.y, (float)b.x, parent.height -(float)b.y);

	}
	/*
	 * checks for an intersection, and returns the position
	 * of intersection if there is one, or null if not.
	 *
	 * is passed a starting point and an end point (max range termination).
	 */
	public Position intersect(Position x, Position y) {
		// first see if there is an intersection
		// for each line check if end points are on opposite sides of the other line
		// http://stackoverflow.com/a/7069702/1349876
		// using cross products, make sure signs are different

		Position ba = b.sub(a);
		Position bx = b.sub(x);
		Position by = b.sub(y);

		if (ba.cross(bx) * ba.cross(by) > 0 ) return null;

		Position xy = x.sub(y);
		Position xa = x.sub(a);
		Position xb = x.sub(b);

		if (xy.cross(xa) * xy.cross(xb) > 0) return null;

		// there is an intersection. find it.
		// convert each pair of points into lines in two point form, then solve two eqs two unknowns
		// http://en.wikipedia.org/wiki/Linear_equation#Two-point_form


		// solve for x and y (algebra done by mathematica)
		double intx = -(-y.x*a.x*x.y + y.x*b.x*x.y + x.x*a.x*y.y - x.x*b.x*y.y + x.x*b.x*a.y - y.x*b.x*a.y - x.x*a.x*b.y + y.x*a.x*b.y)/(a.x*x.y - b.x*x.y - a.x*y.y + b.x*y.y - x.x*a.y + y.x*a.y + x.x*b.y - y.x*b.y);
		double inty = -(y.x*x.y*a.y - b.x*x.y*a.y - x.x*y.y*a.y + b.x*y.y*a.y - y.x*x.y*b.y + a.x*x.y*b.y + x.x*y.y*b.y - a.x*y.y*b.y)/(-a.x*x.y + b.x*x.y + a.x*y.y - b.x*y.y + x.x*a.y - y.x*a.y - x.x*b.y + y.x*b.y);

		return new Position(intx, inty);
	}
	/*
	 * checks whether the position p is near the line
	 * theta value is the range
	 */
	public Position near(Position p) {
		// calculate distance from p to line with cross product
		// D = (AB)X(AP) / len(AB)

		Position ab = a.sub(b);
		Position ap = a.sub(p);

		double d = ab.cross(ap) / ab.length();

		if (d > p.theta) return null;

		// ok, its near, now return closest point
		//TODO
		return new Position();


	}
}
