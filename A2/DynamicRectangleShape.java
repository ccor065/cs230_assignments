/*
 * ==========================================================================================
 * DunamicRectangleShape.java : a rectnagel shape that chnages orientation when it meets the
 * window boarder.
 * YOUR UPI: CCOR065 - Implemented all methods. 
 ========================================================================
 */
import java.awt.*;
class  DynamicRectangleShape extends RectangleShape {

    /** default constructor to create a shape with default values */
    public DynamicRectangleShape() {}
	/** constructor to create a rectangle with default values */
	public DynamicRectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
		super(x ,y ,w, h ,mw ,mh, c, pt);
	}
	/** draw the rectangle with the fill colour
	 *	If it is selected, draw the handles
	 *	@param g	the Graphics control */
	@Override
	public void draw(Painter g) {
		g.setPaint(color);
		if (this.path.getIsBouncedBack() == true){
		    int prevWidth = this.width;
		    this.width = height;
		    this.height = prevWidth;
			path.setIsBouncedBack();
		}
		g.fillRect(x, y, width, height);
	}

}
