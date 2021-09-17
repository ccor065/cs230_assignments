/*
 *    ===============================================================================
 *    Shape.java : The superclass of all shapes.
 *    A shape defines various properties, including selected, colour, width and height.
 *    YOUR UPI: CCOR065 added DOWNRIGHT case in setPath method, added DownRightPath
 *				inner class that extends MovingPath, added OvalShape, RectangleShape
*				and TriangleShape sub classes.
 *    ===============================================================================
 */
import java.awt.*;
abstract class Shape {
    public static final PathType DEFAULT_PATHTYPE = PathType.BOUNCE;
    public static final ShapeType DEFAULT_SHAPETYPE = ShapeType.RECTANGLE;
    public static final int DEFAULT_X = 0, DEFAULT_Y = 0, DEFAULT_WIDTH=100, DEFAULT_HEIGHT=50, DEFAULT_MARGIN_WIDTH=600, DEFAULT_MARGIN_HEIGHT=800;
    public static final Color DEFAULT_COLOR=Color.blue;
    public int x, y, width=DEFAULT_WIDTH, height=DEFAULT_HEIGHT, marginWidth=DEFAULT_MARGIN_WIDTH, marginHeight=DEFAULT_MARGIN_HEIGHT; // the margin of the animation panel area
    protected MovingPath path = new BouncingPath(1, 2);            // the moving path
    protected Color color = DEFAULT_COLOR; // the border colour
    protected boolean selected = false;    // draw handles if selected

    /** default constructor to create a shape with default values */
    public Shape() {}
    public Shape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
        this.x = x;
        this.y = y;
        marginWidth = mw;
        marginHeight = mh;
        width = w;
        height = h;
        color = c;
        setPath(pt);
    }

   /** Return a string representation of the shape, containing
     * the String representation of each element. */
    public String toString() {
        return String.format("%s:[x=%d,y=%d,width=%d,height=%d,color=%s]", this.getClass().getName(), x,y,width,height,color);
    }
	/** Set the path of the shape.
	 * @param pathID     the path  */
	public void setPath(PathType pathID) {
		switch (pathID) {
			case BOUNCE : {
				path = new BouncingPath(1, 2);
				break;
      }
      case DOWNRIGHT:{
			   path = new DownRightPath(5,5);
			   break;
			}
		}
  }
    /** Return the x-coordinate of the shape.
     * @return the x coordinate */
    public int getX() { return this.x; }
	/** Set the x-coordinate of the shape.
	 * @param x	 the x-coordinate */
	public void setX(int x) { this.x = x; }
	/** Return the y-coordinate of the shape.
     * @return the y coordinate */
    public int getY() { return this.y;}
	/** Set the y-coordinate of the shape.
	 * @param y	 the y-coordinate	 */
	public void setY(int y) { this.y = y; }
    /** Return the width of the shape.
     * @return the width     */
	public int getWidth() { return width; }
	/** Set the width of the shape.
	 * @param w	 the width */
	public void setWidth(int w) { width = w; }
    /** Return the height of the shape.
     * @return the height     */
	public int getHeight() {return height; }
    /** Set the height of the shape.
     * @param h     the height value */
    public void setHeight(int h) { height = h;}
    /** Return the selected property of the shape.
     * @return the selected property  */
    public boolean isSelected() { return selected; }
    /** Set the selected property of the shape.
     *    When the shape is selected, its handles are shown.
     * @param s     the selected value */
    public void setSelected(boolean s) { selected = s; }
    /** Return the fill colour of the shape.
     * @return the fill colour */
	public Color getColor() { return color; }
    /** Set the fill colour of the shape.
     * @param c     the fill colour */
    public void setColor(Color c) { color = c; }
    /** Reset the margin for the shape
     * @param w     the margin width
     * @param h     the margin height */
    public void setMarginSize(int w, int h) {
        marginWidth = w;
        marginHeight = h;
    }
    /** Draw the handles of the shape
     * @param g     the Graphics control */
    public void drawHandles(Painter g) {
        g.drawHandles(selected, x, y, width, height);
    }
	/** Returns whether the point is in the shape
	 * @param g	 the Graphics control */
    public abstract boolean contains(Point p);
    /** draw the shape
     * @param g     the Graphics control */
    public abstract void draw(Painter g);
    /** move the shape by the path  */
    public void move() {
        path.move();
    }
    /* Inner class ===================================================================== Inner class
     *    MovingPath : The superclass of all paths. It is an inner class.
     *    A path can change the current position of the shape.
     *    =============================================================================== */
    abstract class MovingPath {
        protected int deltaX, deltaY; // moving distance
        /** constructor  */
        public MovingPath() { }
        /** move the shape according to the path */
        public abstract void move();
    }
    /*  ===============================================================================
     *  BouncingPath : A Bouncing path.
     *  =============================================================================== */
    class BouncingPath extends MovingPath {
         /** constructor to initialise values for a bouncing path */
        public BouncingPath(int dx, int dy) {
            deltaX = dx;
            deltaY = dy;
         }
        /** move the shape */
        public void move() {
             x = x + deltaX;
             y = y + deltaY;
             if ((x < 0) && (deltaX < 0)) {
                 deltaX = -deltaX;
                 x = 0;
             }
             else if ((x + width > marginWidth) && (deltaX > 0)) {
                 deltaX = -deltaX;
                 x = marginWidth - width;
             }
             if ((y < 0) && (deltaY < 0)) {
                 deltaY = -deltaY;
                 y = 0;
             }
             else if((y + height > marginHeight) && (deltaY > 0)) {
                 deltaY = -deltaY;
                 y = marginHeight - height;
             }
        }
    }
    class DownRightPath extends MovingPath{
    private int interval = 0;
    private boolean isDownward = true;
    public DownRightPath(int dx, int dy) {
            deltaX = dx;
            deltaY = dy;}
    public void move(){

        if (interval == 10){
            isDownward = !isDownward;
            interval = 0;
        }
        if (isDownward == true){
            if(y + height + deltaY > marginHeight){
                y = 0;
            } else {
            y = y + deltaY;
            }
        }
         if (isDownward == false){
             if (x + width + deltaX > marginWidth){
                 x = 0;
             } else {
                x += deltaX;
             }
        }
        interval++;
    }
}
}


 class RectangleShape extends Shape {
    public RectangleShape(){}
    public RectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
    super(x, y, w, h, mw, mh, c, pt);}
    public void draw(Painter g){
        g.setPaint(color);
        g.fillRect(x, y, width, height);}
    public boolean contains(Point mousePt){
        return (mousePt.x >= x && mousePt.x <= x + width && mousePt.y >= y && mousePt.y <= y + height);}
}

 class OvalShape extends Shape {
    public OvalShape(){}
    public OvalShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
    super(x, y, w, h, mw, mh, c, pt);}
    public void draw(Painter g){
        g.setPaint(color);
        g.fillOval(x, y, width, height);}
    public boolean contains(Point mousePt){
        double dx, dy;
        Point EndPt = new Point(x + width, y + height);
        dx = (2 * mousePt.x - x - EndPt.x) / (double) width;
        dy = (2 * mousePt.y - y - EndPt.y) / (double) height;
        return dx * dx + dy * dy < 1.0;}

}
 class TriangleShape  extends Shape {
    public TriangleShape (){}
    public TriangleShape (int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
    super(x, y, w, h, mw, mh, c, pt);}
    public void draw(Painter g){
        g.setPaint(color);
        Polygon p = new Polygon( new int[]{x + width/2, x+width, x}, new int[]{y, y+height, y+height}, 3);
        g.fillPolygon(p);}
    public boolean contains(Point mousePt){
        Polygon p = new Polygon( new int[]{x + width/2, x+width, x}, new int[]{y, y+height, y+height}, 3);
        return p.contains(mousePt);}
}
