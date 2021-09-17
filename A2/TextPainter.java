/*
 *  ============================================================================================
 *  Text version of a painter
 *  YOUR UPI: ANSWER
 *  ============================================================================================
 */
import java.awt.*;
import java.util.*;
class TextPainter implements Painter {
    public TextPainter() {}
    @Override
	public void fillRect(int x, int y, int width, int height) {
		System.out.printf("Rectangle[x=%d,y=%d,width=%d,height=%d]\n", x, y, width, height);
	}
    @Override
	public void fillOval(int x, int y, int width, int height) {
		System.out.printf("Oval[x=%d,y=%d,width=%d,height=%d]\n", x, y, width, height);
	}
	public void fillPolygon(Polygon p) {
		System.out.printf("Polygon[x[]=%s,y[]=%s]\n",Arrays.toString(p.xpoints),Arrays.toString(p.ypoints) );
	}
	public void drawHandles(boolean isSelected,  int x, int y, int width, int height) {
		System.out.printf("Selected:%b\n", isSelected);
	}
	@Override
	public void setPaint(Color color) {
		System.out.printf("Color:%s\n", color.toString());
	}
	@Override
	public void setGraphics(Graphics g) {}

}