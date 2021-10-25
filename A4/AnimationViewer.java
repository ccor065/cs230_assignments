/*
 * ==========================================================================================
 * AnimationViewer.java : Moves shapes around on the screen according to different paths.
 * It is the main drawing area where shapes are added and manipulated.
 * YOUR UPI:
 * ==========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;

class AnimationViewer extends JComponent implements Runnable {
	private Thread animationThread = null;    // the thread for animation
    private static int DELAY = 30;         // the current animation speed
    private Painter painter = new GraphicsPainter();;
    private ShapeType currentShapeType=Shape.DEFAULT_SHAPETYPE; // the current shape type,
    private PathType currentPathType=Shape.DEFAULT_PATHTYPE;  // the current path type
    private Color currentColor=Shape.DEFAULT_COLOR; // the current fill colour of a shape
    private int marginWidth=Shape.DEFAULT_MARGIN_WIDTH, marginHeight = Shape.DEFAULT_MARGIN_HEIGHT, currentWidth=Shape.DEFAULT_WIDTH, currentHeight=Shape.DEFAULT_HEIGHT;
	private String currentText=Shape.DEFAULT_TEXT;
	private ShapeType currentInnerShapeType=Shape.DEFAULT_SHAPETYPE;
    private NestedShape root;
    private NestedShape selectedNestedShape;
	private ShapeModelAdapter shapeModelAdapter;

	 public AnimationViewer(boolean isGraphicsVersion) {
 			if (isGraphicsVersion) {
 				start();
 				addMouseListener(new MyMouseAdapter());
 			}
 		this.root = new NestedShape(Shape.DEFAULT_MARGIN_WIDTH, Shape.DEFAULT_MARGIN_HEIGHT);
 		this.selectedNestedShape = root;
 		this.shapeModelAdapter = new ShapeModelAdapter();
     }

     protected void createNewShape(int x, int y) {
             Shape shape = null;
 			 shape  = root.createInnerShape(x, y, currentWidth, currentHeight , marginWidth ,marginHeight,currentColor, currentText, currentShapeType,currentInnerShapeType);
 			this.shapeModelAdapter.insertNodeInto(shape, this.root);

     }
 		public boolean isRoot(Shape selectedNode){return selectedNode == this.root;}
 		public NestedShape getRoot(){return this.root;}
 		public ShapeModelAdapter getShapeModelAdapter(){return this.shapeModelAdapter;}
		public void addShapeNode(NestedShape selectedNode){
	    if(selectedNode == root){
	        createNewShape(0,0);
	        return;
	    } else{
		Shape shape  = selectedNode.createInnerShape ( currentShapeType, currentInnerShapeType);
		this.shapeModelAdapter.insertNodeInto(shape, selectedNode);
	    }
		}
		public void setSelectedNestedShape(NestedShape ns){
    this.selectedNestedShape = ns;
    shapeModelAdapter.fireTableStructureChanged();

}
	public void removeShapeNode(Shape selectedNode){
	    shapeModelAdapter.removeNodeFromParent(selectedNode);
	}

		class ShapeModelAdapter extends AbstractTableModel implements TreeModel  {
		  private String[] columnNames = new String[] {"Type", "Width", "Height"};
		  private ArrayList<TreeModelListener> treeModelListeners  = new ArrayList<TreeModelListener>();

		  public ShapeModelAdapter(){}

		  public void valueForPathChanged(TreePath path, Object newValue){}
		  public void  addTreeModelListener(final TreeModelListener tml) {treeModelListeners.add(tml);}
		  public void  removeTreeModelListener(final TreeModelListener tml) {treeModelListeners.remove(tml);}
		  public int getColumnCount(){return this.columnNames.length;}
		  public int getRowCount(){return selectedNestedShape.getSize();}
		  public String getColumnName(int column){return this.columnNames[column];}
		  public Shape getRoot(){return root;}


		  public boolean isLeaf(Object node){
		      if (node instanceof NestedShape){
		          return false;
		      }
		      ArrayList<Shape> innerShapes = selectedNestedShape.getAllInnerShapes();
		      for (int i = 0; i < innerShapes.size(); i++){
		          if(innerShapes.get(i) == node){
		              return true;
		          }
		      }
		    return false;

		  }
		  public Object getChild(Object parent, int index){
		      if(parent instanceof NestedShape){
		          ArrayList<Shape> innerShapes = ((NestedShape)parent).getAllInnerShapes();
		          if (index < ((NestedShape)parent).getSize() && index>= 0){
		              return innerShapes.get(index);
		          }
		      }
		    return null;

		  }

		  public int getChildCount(Object parent){
		      if(parent instanceof NestedShape){
		         return ((NestedShape)parent).getSize();
		      }
		      return 0;
		  }
		  public int getIndexOfChild(Object parent, Object child) {
		      if(parent instanceof NestedShape && child instanceof Shape){
		          return ((NestedShape)parent).indexOf((Shape)child);
		      }
		      return -1;

		  }

		  public Object getValueAt(int rowIndex, int columnIndex){
		    String atrib = columnNames[columnIndex];
		    switch(atrib){
		      case "Type":
		        return selectedNestedShape.getInnerShapeAt(rowIndex).getClass().getName();

		      case "Width":
		        return selectedNestedShape.getInnerShapeAt(rowIndex).getWidth();

		      case "Height":
		        return selectedNestedShape.getInnerShapeAt(rowIndex).getHeight();
		    }
		    return 0;
		  }
		    public void insertNodeInto(Shape newChild, NestedShape parent){

		      fireTreeNodesInserted(parent, new Object[]{parent.getPath()}, new int[] {parent.indexOf(newChild)}, new Object[]{((Object)newChild)});
		      fireTableRowsInserted(parent.getSize(), parent.getSize()) ;
		  }

		  public void removeNodeFromParent(Shape selectedNode){
		       NestedShape parent = selectedNode.getParent();
		       int index = parent.indexOf(selectedNode);
		       parent.remove(selectedNode);
		       fireTreeNodesRemoved(parent, new Object[]{parent.getPath()}, new int[]{index}, new Object[]{((Object)selectedNode)});

		  }
		  	public void fireTreeNodesChanged(TreeModelEvent e) {}
			protected void fireTreeNodesRemoved(Object source, Object[] path,int[] childIndices,Object[] children) {
				final TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
				for (final TreeModelListener l : treeModelListeners)
					l.treeNodesRemoved(event);
		    }
		    protected void fireTreeNodesInserted(Object source, Object[] path,int[] childIndices,Object[] children) {
				final TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
				for (final TreeModelListener l : treeModelListeners)
					l.treeNodesInserted(event);
		    }
		    protected void fireTreeStructureChanged(final Object source, final Object[] path, final int[] childIndices, final Object[] children) {
		        final TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
		        for (final TreeModelListener l : treeModelListeners) {
		            l.treeStructureChanged(event);
		        }
			}

		}

	/** get the current width
	 * @return currentWidth - the width value */
	public int getCurrentWidth() { return currentWidth; }
	/** get the current height
	 * @return currentHeight - the height value */
	public int getCurrentHeight() { return currentHeight; }
	/** get the current fill colour
	 * @return currentColor - the fill colour value */
	public Color getCurrentColor() { return currentColor; }
    /**    move and paint all shapes within the animation area
     * @param g    the Graphics control */
    public void paintComponent(Graphics g) {
		painter.setGraphics(g);
		super.paintComponent(g);
        for (Shape currentShape: root.getAllInnerShapes()) {
            currentShape.move();
		    currentShape.draw(painter);
            currentShape.drawHandles(painter);
            currentShape.drawString(painter);
		}
    }
    public ShapeType getCurrentShapeType() { return currentShapeType; }
    public void setCurrentShapeType(int st) {
		currentShapeType = ShapeType.getShapeType(st);
	}
    public ShapeType getCurrentInnerShapeType() { return currentInnerShapeType; }
    public void setCurrentInnerShapeType(int st) {
		currentInnerShapeType = ShapeType.getShapeType(st);
		System.out.println(currentInnerShapeType);
	}
	public PathType getCurrentPathType() { return currentPathType; }
	public void setCurrentPathType(int pt) {
		currentPathType = PathType.getPathType(pt);
	}
	public void setCurrentHeight(int h) {
		currentHeight = h;
		for (Shape currentShape: root.getAllInnerShapes())
			if ( currentShape.isSelected())
				currentShape.setHeight(currentHeight);
	}
	public void setCurrentWidth(int w) {
		currentWidth = w;
		for (Shape currentShape: root.getAllInnerShapes())
			if ( currentShape.isSelected())
				currentShape.setWidth(currentWidth);
	}
	public void setCurrentColor(Color bc) {
		currentColor = bc;
		for (Shape currentShape: root.getAllInnerShapes())
			if ( currentShape.isSelected())
				currentShape.setColor(currentColor);
	}
	public String getCurrentText() { return currentText; }
	public void setCurrentText(String text) {
		currentText = text;
		for (Shape currentShape: root.getAllInnerShapes())
			if ( currentShape.isSelected())
				currentShape.setText(currentText);
	}
    // you don't need to make any changes after this line ______________
    /**    update the painting area
     * @param g    the graphics control */
    public void update(Graphics g){ paint(g); }
    /** reset the margin size of all shapes from our ArrayList */
    public void resetMarginSize() {
        marginWidth = getWidth();
        marginHeight = getHeight() ;
        for (Shape currentShape: root.getAllInnerShapes())
			currentShape.setMarginSize(marginWidth,marginHeight );
    }
    class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked( MouseEvent e ) {
			boolean found = false;
			for (Shape currentShape: root.getAllInnerShapes())
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
