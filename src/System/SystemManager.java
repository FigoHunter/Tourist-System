package System;

import java.awt.*;
import java.io.*;
import java.util.*;

import exception.*;
import journey.*;
import GUI.*;
import basic.*;
import drive.*;
/**
 * The delegate and the main manager of the system
 * @author Group 78
 *
 */
public class SystemManager {
	public static TimeTable timeTable = new TimeTable();
	public static RouteEditor routeEditor;
	public static StopEditor stopEditor;
	public static DriverEditor driverEditor;
	public static TrainEditor trainEditor;
	public static TimeTableObserver timeTableOb;
	public static ManagerFrame managerFrame;
	protected static Time realTime;
	/** the time simulated in the system*/
	public static ArrayList<Driver> driverList = new ArrayList<Driver>();
	public static ArrayList<Driver> busyDriverList = new ArrayList<Driver>();
	public static ArrayList<Train> trainList = new ArrayList<Train>();
	public static ArrayList<Train> busyTrainList = new ArrayList<Train>();
	public static ArrayList<Drive> driveList = new ArrayList<Drive>();
	public static int timeStep = 1;
	/** time to go each loop*/
	public static StopInfOb stopInfOb;
	public static TrainInfOb trainInfOb;
	protected RunTimeThread t;
/**
 * Constructor and Initialization
 */
	public SystemManager() {
		try {
			realTime = new Time(8, 0);
		} catch (TimeSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readFromFile();
		readDriverList();
		readTrainList();
		initializeJourney();
		managerFrame = new ManagerFrame();
		// routeEditor= new RouteEditor();
		// driverEditor = new DriverEditor(true);
		// trainEditor = new TrainEditor(true);
		t = new RunTimeThread();
		t.start();
	}
/**
 * Save the timetable and the route
 */
	public static void saveToFile() {
		// System.out.println(timeTable.routeList.size());
		ObjectOutputStream obj = null;
		try {
			obj = new ObjectOutputStream(new FileOutputStream("TimeTable.ser"));
			obj.writeObject(timeTable);
			obj.flush();
			System.out.println("Save succeed");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				obj.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
/**
 * read the table and the route
 */
	public static void readFromFile() {
		ObjectInputStream obj = null;
		try {
			obj = new ObjectInputStream(new FileInputStream("TimeTable.ser"));
			timeTable = (TimeTable) obj.readObject();
			System.out.println("Read succeed");
			System.out.println("Journeys read from file:"
					+ timeTable.routeList.size());
		} catch (Exception e) {
			System.out.println("no file to read");
		} finally {
			try {
				obj.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
/**
 * Clear the frame reference variables
 */
	public static void disposeRouteEditor() {
		routeEditor = null;
	}

	public static void reloadRouteEditor() {
		routeEditor = new RouteEditor();
	}

	public static void disposeStopEditor() {
		stopEditor = null;
	}

	public static void disposeTimeTableOb() {
		timeTableOb = null;
	}

	public static void disposeDrvierEditor() {
		driverEditor = null;
	}

	public static void disposeTrainEditor() {
		trainEditor = null;
	}

	public static void disposeManagerFrame() {
		if(trainInfOb!=null)
		{
			trainInfOb.dispose();
		}
		if(stopInfOb!=null)
		{
			stopInfOb.dispose();
		}
		managerFrame = null;
		trainInfOb=null;
		stopInfOb=null;
	}
/**
 * Check the verification of the timetable and return it
 * @param checkJourney The journey to check and save
 * @return The arraylist of time. null when timelist has error
 * @throws TimeSetException TimeSeqenceException
 */
	public static ArrayList<Time> saveTimeTable(String[] checkJourney) throws TimeSequenceException, TimeSetException{
		// boolean verification = true;
		String[] timeList = checkJourney;
		int stopNum = checkJourney.length;
		Time time;
		ArrayList<Time> tempTimeList = new ArrayList<Time>();
		try {
			time = new Time(0, 0);
		} catch (TimeSetException e1) {
			e1.printStackTrace();
		}
			for (int i = 0; i < stopNum; i++) {
				Time preTime;
				System.out.println(timeList[i]);
				if(i>0)
					preTime=Time.toTime(timeList[i-1]);
				else 
					preTime=new Time();
				time = Time.toTime(timeList[i]);
				if(time.compareTo(preTime)>0)
					tempTimeList.add(time);
				else
					throw new TimeSequenceException("Time sequence contains error");
			}
		return tempTimeList;
	}
/**
 * Read drivers from file
 */
	public static void readDriverList() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"Drivers.csv"));
			String line;
			while ((line = reader.readLine()) != null) {
				String content[] = line.split(",");
				if (content.length == 2) {
					String driNum = content[0];
					String driName = content[1];
					driverList.add(new Driver(driNum, driName));
				} else {
					System.out.println("file data format error");
				}
			}
			System.out.println("driver list read");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save drivers to file
	 */
	public static void writeDriverList() {
		try {
			File csv = new File("Drivers.csv");

			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));
			for (int i = 0; i < driverList.size(); i++) {
				bw.write(driverList.get(i).getNum() + ","
						+ driverList.get(i).getName());
				bw.newLine();
			}
			bw.close();
			System.out.println("driver list written");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * read trains from file
 */
	public static void readTrainList() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"Trains.csv"));
			String line;
			while ((line = reader.readLine()) != null) {
				String content[] = line.split(",");
				if (content.length == 2) {
					String trainNum = content[0];
					int trainCapacity = Integer.parseInt(content[1]);
					trainList.add(new Train(trainNum, trainCapacity));
				} else {
					System.out.println("file data format error");
				}
			}
			System.out.println("train list read");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * save train to files
 */
	public static void writeTrainList() {
		try {
			File csv = new File("Trains.csv");

			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));
			for (int i = 0; i < trainList.size(); i++) {
				bw.write(trainList.get(i).getTrainNum() + ","
						+ trainList.get(i).getCapacity());
				bw.newLine();
			}
			bw.close();
			System.out.println("train list written");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * update the system information as train driving
 */
	public static void Update() {
		for (int i = 0; i < timeTable.routeList.size(); i++) {
			//SystemManager.timeTable.routeList.get(i).color=(new Color((int) (Math.random() * 256),
			//		(int) (Math.random() * 256), (int) (Math.random() * 256)));
			//saveToFile();
			for (int j = 0; j < timeTable.routeList.get(i).journeylist.size(); j++) {
				if (timeTable.routeList.get(i).journeylist.get(j).time.get(0)
						.compareTo(getRealTime()) <= 0
						&& timeTable.routeList.get(i).journeylist.get(j).time
								.get(timeTable.routeList.get(i).journeylist
										.get(j).time.size() - 1).compareTo(
										getRealTime()) > 0) {
					if (!(timeTable.routeList.get(i).journeylist.get(j).onDrive)) {
						Driver driver = driverList.get(0);
						busyDriverList.add(driver);
						driverList.remove(0);
						Train train = trainList.get(0);
						busyTrainList.add(train);
						trainList.remove(0);
						Drive drive = new Drive(driver, train,
								timeTable.routeList.get(i),
								timeTable.routeList.get(i).journeylist.get(j));
						driveList.add(drive);
						if (SystemManager.managerFrame != null) {
							SystemManager.managerFrame.reloadTable();
						}
					}
				}
			}
		}
		for (int i = 0; i < driveList.size(); i++) {
			driveList.get(i).startDrive();
		}
		if (managerFrame != null) {
			managerFrame.refresh();
		}
		if (routeEditor != null) {
			routeEditor.refresh();
		}
		if (stopInfOb != null) {
			stopInfOb.refresh();
		}
		if (stopEditor != null) {
			stopEditor.refresh();
		}
		if (trainInfOb != null) {
			trainInfOb.refresh();
		}
		// System.out.println(driveList.size());
	}
/**
 * read the real time
 * @return the real time simulated
 */
	public static Time getRealTime() {
		return realTime;
	}
/**
 * set journey as not driven on when system launched
 */
	public static void initializeJourney() {
		for (int i = 0; i < timeTable.routeList.size(); i++) {
			for (int j = 0; j < timeTable.routeList.get(i).journeylist.size(); j++) {
				timeTable.routeList.get(i).journeylist.get(j).onDrive = false;
			}
		}
	}
}