/*
 * ==========================================================================================
 * AnimationViewer.java : Moves shapes around on the screen according to different paths.
 * It is the main drawing area where shapes are added and manipulated.
 * YOUR UPI: CCOR065, added setter and getter of currentText, updated paint, and create new shape.
 * ==========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

class AnimationViewer extends JComponent implements Runnable {
	private Thread animationThread = null;    // the thread for animation
    private static int DELAY = 30;         // the current animation speed
    ArrayList<Shape> shapes = new ArrayList<Shape>(); //create the ArrayList to store shapes
    private Painter painter = new GraphicsPainter();;
    private ShapeType currentShapeType=Shape.DEFAULT_SHAPETYPE; // the current shape type,
    private PathType currentPathType=Shape.DEFAULT_PATHTYPE;  // the current path type
    private Color currentColor=Shape.DEFAULT_COLOR; // the current fill colour of a shape
    private int marginWidth=Shape.DEFAULT_MARGIN_WIDTH, marginHeight = Shape.DEFAULT_MARGIN_HEIGHT, currentWidth=Shape.DEFAULT_WIDTH, currentHeight=Shape.DEFAULT_HEIGHT;
		private String currentText = Shape.DEFAULT_TEXT;
		private ShapeType currentInnerShapeType = Shape.DEFAULT_SHAPETYPE;

		public ShapeType getCurrentInnerShapeType(){return this.currentInnerShapeType;}
		public void setCurrentInnerShapeType(int shapeTypeValue){currentInnerShapeType = ShapeType.values()[shapeTypeValue];}

		public void setCurrentText(String text){
			this.currentText = text;
			for (Shape shape :shapes){
			    if (shape.isSelected()){
				shape.setText(text);
			    }
			}
		}
		public String getCurrentText(){return this.currentText;}
     /** Constructor of the AnimationViewer */
    public AnimationViewer(boolean isGraphicsVersion) {
			if (isGraphicsVersion) {
				start();
				addMouseListener(new MyMouseAdapter());
			}
    }

		protected void createNewShape(int x, int y) {
    /** create a new shape
     * @param x     the x-coordinate of the mouse position
     * @param y    the y-coordinate of the mouse position */
		 switch (currentShapeType) {
      case RECTANGLE: {
      	shapes.add( new RectangleShape(x, y,currentWidth,currentHeight,marginWidth,marginHeight,currentColor,currentPathType, currentText));
          break;
      } case OVAL: {
      	shapes.add(new OvalShape(x,y,currentWidth,currentHeight,marginWidth,marginHeight,currentColor,currentPathType, currentText));
      	break;
      }case NESTED: {
      	shapes.add(new NestedShape(x,y,currentWidth,currentHeight,marginWidth,marginHeight,currentColor, currentText,  currentInnerShapeType));
      	break;
      }
     }
	 }

    /**    move and paint all shapes within the animation area
     * @param g    the Graphics control */
		 public void paintComponent(Graphics g) {
		 	painter.setGraphics(g);
		 	super.paintComponent(g);
		     for (Shape shape: shapes) {
		         shape.move();
		 	    shape.draw(painter);
		         shape.drawHandles(painter);
		 		shape.drawString(painter);
		 	}
		 }
    public ShapeType getCurrentShapeType() { return currentShapeType; }
    public void setCurrentShapeType(int st) {
		currentShapeType = ShapeType.getShapeType(st);
	}
	public PathType getCurrentPathType() { return currentPathType; }
	public void setCurrentPathType(int pt) {
		currentPathType = PathType.getPathType(pt);
	}
	/** get the current height
	 * @return currentHeight - the height value */
	public int getCurrentHeight() { return currentHeight; }
	/** set the current height and the height for all currently selected shapes
	 * @param h	the new height value */
	public void setCurrentHeight(int h) {
		currentHeight = h;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setHeight(currentHeight);
	}
	/** get the current width
	 * @return currentWidth - the width value */
	public int getCurrentWidth() { return currentWidth; }
	/** set the current width and the width for all currently selected shapes
	 * @param w	the new width value */
	public void setCurrentWidth(int w) {
		currentWidth = w;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setWidth(currentWidth);
	}
	/** get the current fill colour
	 * @return currentColor - the fill colour value */
	public Color getCurrentColor() { return currentColor; }
	public void setCurrentColor(Color bc) {
		currentColor = bc;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setColor(currentColor);
	}

    // you don't need to make any changes after this line ______________
    /**    update the painting area
     * @param g    the graphics control */
    public void update(Graphics g){ paint(g); }
    /** reset the margin size of all shapes from our ArrayList */
    public void resetMarginSize() {
        marginWidth = getWidth();
        marginHeight = getHeight() ;
        for (Shape currentShape: shapes)
			currentShape.setMarginSize(marginWidth,marginHeight );
    }
    class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked( MouseEvent e ) {
			boolean found = false;
			for (Shape currentShape: shapes)
				if ( currentShape.contains( e.getPoint()) ) { // if the mousepoint is within a shape, then set the shape to be selected/deselected
					currentShape.setSelected( ! currentShape.isSelected() );
					found = true;
				}
			if (!found) createNewShape(e.getX(), e.getY());
		}
	}
	public void start() {
        animationThread = new Thread(this);
        animationThread.start();
    }
    public void stop() {
        if (animationThread != null) {
            animationThread = null;
        }
    }
    public void run() {
        Thread myThread = Thread.currentThread();
        while(animationThread==myThread) {
            repaint();
            pause(DELAY);
        }
    }
    private void pause(int milliseconds) {
        try {
            Thread.sleep((long)milliseconds);
        } catch(InterruptedException ie) {}
    }
}
