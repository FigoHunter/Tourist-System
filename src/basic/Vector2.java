package basic;

import java.io.*;

/**
 * 2-Dimension vector
 * @author Group 78
 *
 */
public class Vector2 implements Serializable {
	public int x;
	public int y;
	/**
	 * Constructor
	 * @param x Set x
	 * @param y Set y
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
