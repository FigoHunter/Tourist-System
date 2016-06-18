package journey;

import basic.*;

import java.io.*;
/**
 * Define the stop
 * @author Group 78
 *
 */
public class Stop implements Serializable {

	public Vector2 location;
	/** Location of the stop*/
	public String name = "Stop";
	/** Name of stop*/
	public Stop(Vector2 _location) {
		location = _location;
	}

	public Stop() {
	}

	public void setStop(Vector2 _location) {
		location = _location;
	}

	public String toString() {
		return name;
	}
}