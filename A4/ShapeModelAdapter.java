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
  public void valueForPathChanged(TreePath path, Object newValue){}
  public void  addTreeModelListener(final TreeModelListener tml) {treeModelListeners.add(tml);}
  public void  removeTreeModelListener(final TreeModelListener tml) {treeModelListeners.remove(tml);}
  public int getColumnCount(){return this.columnNames.length;}
  public int getRowCount(){return selectedNestedShape.getSize();}
  public String getColumnName(int column){return this.columnNames[column];}
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

}
