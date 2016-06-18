package drive;

import basic.*;
import journey.*;
import System.*;
/**
 * Define a driving object with necessary information
 * @author Group 78
 *
 */
public class Drive {

	public Driver driver;
	/** Define the driver */
	public Train train;
	/** Define the train driven */
	public Route route;
	/** Define the route drive on */
	public Journey journey;
	/** Define the timelist should be obeyed */
	private int lastIndex = 0;
	public Stop fromStop;
	/** Define the stop drive from*/
	public Stop toStop;
	/** Define the stop drive to*/
	public boolean paused = false;
	/** The pause status of drive*/

	/**
	 * Constructor
	 * @param _driver Define the driver
	 * @param _train Define the train driven
	 * @param _route Define the route drive on
	 * @param _journey Define the timelist should be obeyed
	 */
	public Drive(Driver _driver, Train _train, Route _route, Journey _journey) {
		driver = _driver;
		train = _train;
		route = _route;
		journey = _journey;
		fromStop = route.getStop(0);
		toStop = route.getStop(1);
	}

	/**
	 * Renew the train status
	 */
	public void startDrive() {
		if (paused == false) {
			journey.onDrive = true;
			if (SystemManager.getRealTime().compareTo(
					journey.time.get(lastIndex + 1)) > 0) {
				System.out.println("time To arrive"
						+ journey.time.get(lastIndex + 1));
				lastIndex++;
				if (lastIndex >= (journey.time.size() - 1)) {
					System.out.println("route finished");
					SystemManager.driverList.add(driver);
					SystemManager.trainList.add(train);
					SystemManager.driveList.remove(this);
					journey.onDrive = false;
					if (SystemManager.managerFrame != null) {
						SystemManager.managerFrame.reloadTable();
					}
				} else {
					goTo(lastIndex, lastIndex + 1);
					if (SystemManager.managerFrame != null) {
						SystemManager.managerFrame.reloadTable();
					}
				}
			} else {
				goTo(lastIndex, lastIndex + 1);
			}
		} else {
			for (int i = 0; i < journey.time.size(); i++) {
				journey.time.get(i).timeForward(SystemManager.timeStep);
			}
		}
	}
	/**
	 * Renew the train location
	 * @param index1 The index of the stop drive from
	 * @param index2 The index of the stop drive to
	 */
	public void goTo(int index1, int index2) {
		if (index2 < route.returnStopNums()) {
			fromStop = route.getStop(index1);
			toStop = route.getStop(index2);
		} else {
			fromStop = route.getStop(2 * route.returnStopNums() - 2 - index1);
			toStop = route.getStop(2 * route.returnStopNums() - 2 - index2);
		}
		Time fromTime = journey.time.get(index1);
		Time toTime = journey.time.get(index2).timeBackward(2);
		if (SystemManager.getRealTime().compareTo(toTime) < 0) {
			float timeInterval = (float) Time.duration(fromTime, toTime);
			float timeTraveled = (float) Time.duration(fromTime,
					SystemManager.getRealTime());
			float xInterval = (float) toStop.location.x - fromStop.location.x;
			float yInterval = (float) toStop.location.y - fromStop.location.y;
			int xTraveled = (int) ((timeTraveled / timeInterval) * xInterval);
			int yTraveled = (int) ((timeTraveled / timeInterval) * yInterval);
			train.setLocation(new Vector2(fromStop.location.x + xTraveled,
					fromStop.location.y + yTraveled));
			// System.out.println(train.getLocation().x+","+train.getLocation().y);
		} else {
			train.setLocation(new Vector2(toStop.location.x, toStop.location.y));
		}
	}
/**
 * Get the stop train running from
 * @return the index of the stop running from
 */
	public int getIndex() {
		return lastIndex;
	}
}
