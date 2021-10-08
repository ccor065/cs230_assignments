/*
 *  ============================================================================================
 *  Painter.java : Painter interface
 *  YOUR UPI: CCOR065, added drawSring() method,
 *  ============================================================================================
 */
import java.awt.*;
interface Painter {
	public void drawImage(Image img, int x, int y, int width, int height);
    public void fillRect(int x, int y, int width, int height);
    public void fillOval(int x, int y, int width, int height);
    public void fillPolygon(Polygon p);
	public void setPaint(Color color);
	public void setGraphics(Graphics g);
	public void drawHandles(boolean isSelected, int x, int y, int width, int height);
	public void drawString(String text, int x, int y, int width, int height);
}
