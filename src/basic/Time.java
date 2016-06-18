package basic;

import exception.*;

import java.io.*;
import java.text.*;
import java.util.*;

import exception.*;

/**
 * Definition of time, time manipulation
 * 
 * @author Group 78
 *
 */
public class Time implements Serializable {
	private int hour;
	private int minute;

	/**
	 * Construct time 00:00
	 */
	public Time() {
	}

	/**
	 * Constructor
	 * 
	 * @param _hour
	 *            Define the hour
	 * @param _minute
	 *            Define the minute
	 * @throws TimeSetException
	 *             The values of parameters over bound
	 */
	public Time(int _hour, int _minute) throws TimeSetException {
		if (_hour < 24 && hour >= 0 && minute < 60 && minute >= 0) {
			hour = _hour;
			minute = _minute;
		} else {
			throw new TimeSetException("time format is wrong");
		}
	}

	/**
	 * compare the time
	 * 
	 * @param o
	 *            The time to compare
	 * @return 1 When time is larger than param; 0 when equal; -1 when smaller
	 */
	public int compareTo(Time o) {
		if (this.hour == o.hour) {
			if (this.minute == o.minute) {
				return 0;
			} else if (this.minute > o.minute) {
				return 1;
			} else {
				return -1;
			}
		} else if (this.hour > o.hour) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	/**
	 * Convert time to string
	 */
	public String toString() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		return String.format("%2s:%2s", nf.format(hour), nf.format(minute));
	}

	/**
	 * Convert string to time
	 * 
	 * @param _time
	 *            String of time as 00:00
	 * @return Time
	 * @throws TimeSetException
	 *             String format error or time value error
	 */
	public static Time toTime(String _time) throws TimeSetException {
		Time time = new Time(0, 0);
		StringTokenizer timeTokenizer = new StringTokenizer(_time, ":", false);
		if (timeTokenizer.countTokens() != 2) {
			throw new TimeSetException("time format exception");
		} else {
			time.hour = Integer.parseInt(timeTokenizer.nextToken());
			time.minute = Integer.parseInt(timeTokenizer.nextToken());
			if (time.hour < 0 || time.hour > 23) {
				time.hour = 0;
				time.minute = 0;
				throw new TimeSetException("hour value error");
			}
			if (time.minute < 0 || time.minute > 59) {
				time.hour = 0;
				time.minute = 0;
				throw new TimeSetException("minute value error");
			}
		}
		return time;
	}

	/**
	 * Time set forward in minutes
	 * 
	 * @param step
	 *            Numbers of minutes to set forward
	 */
	public void timeForward(int step) {
		minute = minute + step;
		if (minute > 59) {
			hour = hour + 1;
			minute = minute - 60;
		}
	}

	/**
	 * Time set backward in minutes
	 * 
	 * @param step
	 *            Numbers of minutes to set backward
	 * @return
	 */
	public Time timeBackward(int step) {
		Time time = new Time();
		time.minute = minute - step;
		time.hour = hour;
		if (time.minute < 0) {
			time.hour = time.hour - 1;
			time.minute = time.minute + 60;
		}
		return time;
	}

	/**
	 * Calculate the duration in minute
	 * 
	 * @param time1
	 *            The start time
	 * @param time2
	 *            The end time
	 * @return
	 */
	public static int duration(Time time1, Time time2) {
		int minute;
		minute = (time2.hour - time1.hour) * 60 + time2.minute - time1.minute;
		return minute;
	}
}
