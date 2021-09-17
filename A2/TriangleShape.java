/*
 *	===============================================================================
 *	TriangleShape.java : A shape that is a Isosceles triangle.
 *  YOUR UPI:
 *	=============================================================================== */
import java.awt.*;
import java.util.*;

class TriangleShape extends Shape {
	private int[] xCoord;
	private int[] yCoord;
	private Polygon polygon;
	/** constructor to create a rectangle with default values */
	public TriangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
		super(x ,y ,w, h ,mw ,mh, c, pt);
	}
	/** draw the rectangle with the fill colour
	 *	If it is selected, draw the handles
	 *	@param g	the Graphics control */
	@Override
	public void draw(Painter g2d) {
		xCoord = new int[]{x+width/2, x+width, x};
		yCoord = new int[]{y, y+height, y+height};
		polygon = new Polygon(xCoord, yCoord, 3);
		g2d.setPaint(color);
		g2d.fillPolygon(polygon);
	}
	/** Returns whether the point is in the rectangle or not
	 * @return true if and only if the point is in the rectangle, false otherwise. */
	@Override
	public boolean contains(Point mousePt) {
		return polygon.contains(mousePt);
	}
}