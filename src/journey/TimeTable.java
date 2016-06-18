package journey;

import java.util.*;
import basic.*;
import java.io.*;
/**
 * Contain all the route and timetable info
 * @author Group 78
 *
 */
public class TimeTable implements Serializable {
	public ArrayList<Route> routeList = new ArrayList<Route>();
	public transient static Stop centralStation = new Stop(new Vector2(0, 0));

	public TimeTable() {
		centralStation.name = "Central Station";
	}
}
