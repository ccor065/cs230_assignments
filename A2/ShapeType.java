/*
 *  ============================================================================================
 *  enum which defines the type of shapes in A2
 *  YOUR UPI: CCOR065 - added DYNAMIC and IMAGE type to enum.
 *  ============================================================================================
 */
import java.util.*;
enum ShapeType { RECTANGLE, OVAL, TRIANGLE, DYNAMIC, IMAGE;
	private static final Random rand = new Random(30);
	private static final int SIZE = values().length;
	public static final ShapeType getShapeType(int index) { return values()[index]; }
	public ShapeType next() {
		return values()[(ordinal() + 1) % values().length];
		}
	public static ShapeType getRandomShapeType()  {
	    return values()[rand.nextInt(SIZE)];
  }
}
