/*
 *	===============================================================================
 *	ShapeModelAdapter.java : Generates a table representing the innerShapes
 *  YOUR UPI: CCOR065, all Implementation
 *	=============================================================================== */

// import java.util.*;
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
