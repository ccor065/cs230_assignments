/*
 *  ============================================================================================
 *  Text version of a painter
 *  YOUR UPI: CCOR065 - added fillRect, fillOval, fillPolygon methods.
 *  ============================================================================================
 */
import java.awt.*;
import java.util.Arrays;
class TextPainter implements Painter {
    public TextPainter() {}
	public void setPaint(Color color) {
		System.out.printf("Color: %s\n", color.toString());
	}
	public void setGraphics(Graphics g) {}
	public void drawHandles(boolean isSelected,  int x, int y, int width, int height) {
		System.out.printf("Selected: %b\n", isSelected);
	}
  public void fillRect(int x, int y, int width, int height){
   System.out.printf("Rectangle[x=%d,y=%d,width=%d,height=%d]\n", x, y, width, height);}
  public void fillOval(int x1, int y1, int width, int height){
    System.out.printf("Oval[x=%d,y=%d,width=%d,height=%d]\n", x1, y1, width, height);}
  public void fillPolygon(Polygon p){
    System.out.printf("Polygon[x[]=%s,y[]=%s]\n", Arrays.toString(p.xpoints), Arrays.toString(p.ypoints));}
}
