

/*
 *	===============================================================================
 *	RectangleShape.java : A shape that is a rectangle.
 *  YOUR UPI: CCOR065, added overloaded constructor
 *	=============================================================================== */
import java.awt.*;
class RectangleShape extends Shape {

    /** default constructor to create a shape with default values */
  public RectangleShape() {}
	public RectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
		super(x ,y ,w, h ,mw ,mh, c, pt);
	}
  public RectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt, String m) {
    super(x ,y ,w, h ,mw ,mh, c, pt, m);
  }
	/** draw the rectangle with the fill colour
	 *	If it is selected, draw the handles
	 *	@param g	the Graphics control */
	@Override
	public void draw(Painter g2d) {
		g2d.setPaint(color);
		g2d.fillRect(x, y, width, height);
	}
	/** Returns whether the point is in the rectangle or not
	 * @return true if and only if the point is in the rectangle, false otherwise. */
	@Override
	public boolean contains(Point mousePt) {
		return (x <= mousePt.x && mousePt.x <= (x + width + 1)	&&	y <= mousePt.y && mousePt.y <= (y + height + 1));
	}
}
