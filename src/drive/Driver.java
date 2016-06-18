package drive;

import java.io.*;
/**
 * Definition of driver
 * @author Group 78
 *
 */
public class Driver implements Serializable {
	private String name;
	/** Driver's name*/
	private String driveNum;
	/** The Id of the drive*/
	private boolean occupation = false;
	/** Whether the driver is working*/

	/**
	 * Constructor
	 * @param _driveNum
	 * @param _name
	 */
	public Driver(String _driveNum, String _name) {
		name = _name;
		driveNum = _driveNum;
	}

	public String getNum() {
		return driveNum;
	}

	public String getName() {
		return name;
	}

	public void setNum(String num) {
		driveNum = num;
	}

	public void setName(String _name) {
		name = _name;
	}

	public String toString() {
		return driveNum;
	}
}
