/*
 *  ============================================================================================
 *  Painter.java : Painter interface
 *  YOUR UPI: ANSWER
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
}