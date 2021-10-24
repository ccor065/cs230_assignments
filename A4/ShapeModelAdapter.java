/*
 *	===============================================================================
 *	ShapeModelAdapter.java : Generates a table reporesenting the innerShapes
 *  YOUR UPI: CCOR065, all Implementation
 *	=============================================================================== */

// import java.util.*;
class ShapeModelAdapter extends AbstractTableModel {
  private String[] columnNames = new String[] {"Type", "Width", "Height"};
  public ShapeModelAdapter(){}
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
