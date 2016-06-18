package drive;

import basic.*;
/**
 * Define the train
 * @author Group 78
 *
 */
public class Train {
	private Vector2 location = new Vector2(0, 0);
	private String trainNum;
	private int capacity;
/**
 * Constructor
 * @param num Train id number
 * @param capacity Train passenger capacity
 */
	public Train(String num, int capacity) {
		this.trainNum = num;
		this.capacity = capacity;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public String getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String toString() {
		return trainNum;
	}
}
