/*
 *  ============================================================================================
 *  enum which defines the type of paths in A1
 *  YOUR UPI: CCOR065, no changes
 *  ============================================================================================
 */
import java.util.*;
enum PathType { BOUNCE;
	private static final Random rand = new Random(30);
	private static final int SIZE = values().length;
	public static final PathType getPathType(int index) { return values()[index]; }
	public PathType next() {
		return values()[(ordinal() + 1) % values().length];
	}
	public static PathType getRandomPathType()  {
	    return values()[rand.nextInt(SIZE)];
  	}
}
