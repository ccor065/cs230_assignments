/*
 *	===============================================================================
 *	ImageRectangleShape.java : A shape that is an Image.
 *  YOUR UPI: CCOR065
 *	=============================================================================== */
import java.awt.*;
class ImageRectangleShape  extends RectangleShape {
    private Image image = null;
    private String imageFilename = "java.gif";
    public ImageRectangleShape() {}
	public ImageRectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt, String name) {
		super(x ,y ,w, h ,mw ,mh, c, pt);
		this.imageFilename = name;
	}

	public void loadImage(){
	    // implement
	}
	@Override

    public void draw(Painter g){
        g.drawImage(image, x, y, width, height);
    }
	@Override
	public boolean contains(Point mousePt) {
		return (x <= mousePt.x && mousePt.x <= (x + width + 1)	&&	y <= mousePt.y && mousePt.y <= (y + height + 1));
	}
}