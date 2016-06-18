package GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import journey.*;
import System.*;
import drive.*;
import exception.TimeSetException;
import basic.*;
/**
 * Depict Information frame at stop
 * @author Group 78
 *
 */
public class StopInfOb {

	JFrame mainFrame;
	JTable timeTable;
	Route route;
	Stop stop;
	StopEditPanel stopPanel;
	// JPanel rightPanel = new JPanel();
	JPanel leftbottomPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JLabel routeLabel = new JLabel();
	JLabel stopLabel = new JLabel();
	JLabel fromCenter = new JLabel();
	JLabel toCenter = new JLabel();
	DefaultTableModel tableModel;
	int selection;

	public StopInfOb() {
		pickStop();
		initializeTimeTable();
		setInformation();
		mainFrame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(screenSize.width / 2 - 1000 / 2,
				screenSize.height / 2 - 600 / 2);
		mainFrame.setSize(1000, 600);

		stopPanel = new StopEditPanel(route);

		mainFrame.add(BorderLayout.CENTER, stopPanel);
		mainFrame.add(BorderLayout.EAST, leftPanel);

		leftPanel.setLayout(new GridLayout(2, 1, 1, 1));
		leftPanel.add(new JScrollPane(timeTable));
		leftPanel.add(leftbottomPanel);

		leftbottomPanel.setLayout(new BoxLayout(leftbottomPanel,
				BoxLayout.Y_AXIS));
		leftbottomPanel.add(routeLabel);
		leftbottomPanel.add(stopLabel);
		leftbottomPanel.add(fromCenter);
		leftbottomPanel.add(toCenter);

		mainFrame.setVisible(true);
	}

	public void pickStop() {
		int routeIndex = (int) (Math.random() * SystemManager.timeTable.routeList
				.size());
		route = SystemManager.timeTable.routeList.get(routeIndex);
		int stopIndex = (int) (Math.random() * route.returnStopNums());
		stop = route.getStop(stopIndex);
		selection = stopIndex;
	}

	public void initializeTimeTable() {
		int column = route.journeylist.size() + 1;
		int row = route.returnStopNums() * 2 - 1;
		//System.out.println(column);
		//System.out.println(row);

		String[][] table = new String[row][column];

		for (int i = 0; i < (row - 1) / 2; i++) {
			table[i][0] = route.getStop(i).toString();
			table[row - i - 1][0] = route.getStop(i).toString();
			System.out.println(table[i][0]);
		}
		table[(row - 1) / 2][0] = route.getStop((row - 1) / 2).toString();

		for (int i = 1; i < column; i++) {
			for (int j = 0; j < row; j++) {
				table[j][i] = route.journeylist.get(i - 1).time.get(j)
						.toString();
			}
		}
		timeTable = new JTable(row, column);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				timeTable.setValueAt(table[i][j], i, j);
				System.out.println(timeTable.getValueAt(i, j));
			}
		}
		tableModel = (DefaultTableModel) timeTable.getModel();
		timeTable.setShowGrid(true);
		timeTable.setShowHorizontalLines(true);
		timeTable.setGridColor(Color.BLACK);
	}

	public void setInformation() {
		String[] information = getNextTrain();
		routeLabel.setText("Line "
				+ (SystemManager.timeTable.routeList.indexOf(route) + 1));
		stopLabel.setText("Stop: " + stop.toString());
		fromCenter.setText("Next Arrival(From Centr):" + information[0]);
		toCenter.setText("Next Arrival(To Centr):" + information[1]);
	}

	public void refresh() {
		setInformation();
		stopPanel.repaint();
	}

	public String[] getNextTrain() {
		Train fromCenter;
		Train toCenter;
		String[] information = new String[2];
		int minDuration = 99999, minDuration2 = 99999;
		int tempDuration, tempDuration2;
		int index = 1, index2 = 1;
		for (int i = 1; i < timeTable.getColumnCount(); i++) {
			try {
				tempDuration = Time.duration(SystemManager.getRealTime(), Time
						.toTime(timeTable.getValueAt(selection, i).toString()));
				System.out.println(tempDuration);
				if (tempDuration >= 0) {
					if (tempDuration < minDuration) {
						minDuration = tempDuration;
						index = i;
					}
				}
			} catch (TimeSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 1; i < timeTable.getColumnCount(); i++) {
			try {
				tempDuration2 = Time.duration(SystemManager.getRealTime(), Time
						.toTime(timeTable.getValueAt(
								timeTable.getRowCount() - selection - 1, i)
								.toString()));
				System.out.println(tempDuration2);
				if (tempDuration2 >= 0) {
					if (tempDuration2 < minDuration2) {
						minDuration2 = tempDuration2;
						index2 = i;
					}
				}
			} catch (TimeSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (minDuration != 99999) {
			information[0] = timeTable.getValueAt(selection, index).toString();
		} else {
			information[0] = "Unknown";
		}
		if (minDuration2 != 99999) {
			information[1] = timeTable.getValueAt(
					timeTable.getRowCount() - selection - 1, index2).toString();
		} else {
			information[1] = "Unknown";
		}
		return information;
	}
	public void dispose()
	{
		mainFrame.dispose();
	}
}
