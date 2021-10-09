/*
 *	===============================================================================
 *	NestedShape.java : A nested shape class.
 *  YOUR UPI: CCOR065 all implementations
 *	=============================================================================== */
import java.awt.*;
import java.util.ArrayList;
class NestedShape  extends RectangleShape {
  private  ArrayList<Shape> innerShapes = new  ArrayList<Shape>();
  private ShapeType innerShapeType = Shape.DEFAULT_SHAPETYPE;

  public NestedShape(){
    super();
    createInnerShape(innerShapeType);
  }
  public NestedShape(int x, int y, int w, int h, int mw, int mh, Color c, String m, ShapeType ist) {
    super(x ,y ,w, h ,mw ,mh, c, PathType.BOUNCE, m);
    this.innerShapeType = ist;
    createInnerShape(ist);
  }

  public void draw(Painter painter){
    painter.setPaint(Color.black);
    painter.drawRect(x, y, width, height);
    painter.translate(x,y);

    for (Shape shape : innerShapes){
      shape.draw(painter);
    }
    painter.translate(-x, -y);

  }
  @Override
  public void move(){
    path.move();
    for (Shape shape : innerShapes){
      shape.move();
    }
  }
  @Override
  public void setWidth(int w){
    this.width = w;
    for (Shape shape : innerShapes){
      shape.marginWidth = w;
    }
  }

  @Override
  public void setHeight(int h){
    this.height = h;
    for (Shape shape : innerShapes){
      shape.marginHeight = h;
    }
  }
  @Override
  public void setColor(Color c){
    this.color = c;
    for (Shape shape : innerShapes){
      shape.color = c;
    }
  }


  public ShapeType getInnerShapeType(){return this.innerShapeType;}
  public Shape createInnerShape(int x, int y, int w, int h, int mw, int mh, Color c, String text,ShapeType st, ShapeType ist){
    Shape newShape = null;
    switch(st){
      case RECTANGLE:{
      newShape = new RectangleShape(x, y, w, h, mw, mh, c, PathType.BOUNCE, text);
      break;
    }case OVAL: {
       newShape = new OvalShape(x, y, w, h, mw, mh, c, PathType.BOUNCE, text);
       break;
     }
   }
    if (ist != null && newShape == null) {
       switch(ist){
         case RECTANGLE:{
         newShape = new RectangleShape(x, y, w, h, mw, mh, c, PathType.BOUNCE, text);
         break;
       }case OVAL: {
          newShape = new OvalShape(x, y, w, h, mw, mh, c, PathType.BOUNCE, text);
          break;
        }
      }
    }

    newShape.setParent(this);
    this.innerShapes.add(newShape);
    return newShape;

  }
  public Shape createInnerShape(ShapeType st){
      parent = this;
      int pWidth = parent.width;
      int pHeight = parent.height;
      Shape newShape = createInnerShape(0, 0, (this.width/2), (this.height/2), this.width, this.height,  this.color, this.text, st, innerShapeType);
    return newShape;
  }

  public Shape getInnerShapeAt(int index){return innerShapes.get(index);}
  public int getSize(){return innerShapes.size();}
  public int indexOf(Shape s){return innerShapes.indexOf(s);}
  public void add (Shape s){
    s.setParent(this);
    innerShapes.add(s);
  }
  public void remove(Shape s){
    s.setParent(null);
    innerShapes.remove(s);
  }

}
