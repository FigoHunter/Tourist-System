package journey;

import basic.*;

import java.io.*;
import java.util.*;
/**
 * Define a come-return trip timetable
 * @author Group 78
 *
 */
public class Journey implements Serializable {
	public boolean onDrive = false;
	/** Whether journey is driven */
	public ArrayList<Time> time = new ArrayList<Time>();
	/** The timetable content*/
	/*
	 * public String name = "journey name"; public String toString() { return
	 * name; }
	 */
}
