package journey;

import java.awt.*;
import java.io.*;
import java.util.*;

import basic.*;
import drive.*;
import exception.*;
import System.*;
/**
 * Define the route information with stop and timetable
 * @author Group 78
 *
 */
public class Route implements Serializable {
	private ArrayList<Stop> stopList = new ArrayList<Stop>();
	/** List of stop on the route*/
	//private Driver driver;
	private Stop centralStation = TimeTable.centralStation;
	/** The centralstation*/
	public ArrayList<Journey> journeylist = new ArrayList<Journey>();
	/** Contain the timetable information*/
	public Color color;
	/** The color of graphic of route*/

	/**
	 * Constructor central station added always
	 */
	public Route() {
		stopList.add(TimeTable.centralStation);
		color = new Color((int) (Math.random() * 256),
				(int) (Math.random() * 256), (int) (Math.random() * 256));
	}

	
	  //public void drawRoute() {
		// draw the route
	//}
	

	/**
	 * Add a station object at certain index
	 * @param station
	 * @param index
	 * @throws IndexOutSizeException index is larger than the size
	 */
	public void addStation(Stop station, int index)
			throws IndexOutSizeException {
		if (stopList.size() >= index) {
			stopList.add(index, station);
			for (int i = 0; i < journeylist.size(); i++) {
				int size = stopList.size() * 2;
				journeylist.get(i).time.add(index, new Time());
				journeylist.get(i).time.add(size - index - 1, new Time());
			}
		} else {
			throw new IndexOutSizeException(
					"Route doesn't have so much stations");
		}
	}
/**
 * Delete station of certain index
 * @param index
 * @throws IndexOutSizeException Index out bound
 */
	public void deleteStation(int index) throws IndexOutSizeException {
		if (stopList.remove(index) != null) {
			for (int i = 0; i < journeylist.size(); i++) {
				int size = stopList.size() * 2;
				journeylist.get(i).time.remove(index);
				journeylist.get(i).time.remove(size - 1 - index);
			}
			System.out.println("Station has been removed");
		} else {
			throw new IndexOutSizeException("Station not found");
		}
	}

	public int returnStopNums() {
		return stopList.size();
	}

	public Stop getStop(int index) {
		return stopList.get(index);
	}

	public Stop getLastStop() {
		return stopList.get(stopList.size() - 1);
	}

	public String toString() {
		return "Line " + (SystemManager.timeTable.routeList.indexOf(this) + 1);
	}

	public int getIndex(Stop stop) {
		return stopList.indexOf(stop);
	}

	public ArrayList<Stop> getList() {
		return stopList;
	}
}
