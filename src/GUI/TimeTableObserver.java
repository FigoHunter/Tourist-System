package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import exception.*;
import journey.*;
import System.*;
import basic.*;
/**
 * Timetable edit frame
 * @author Group 78
 *
 */
public class TimeTableObserver implements ActionListener, TableModelListener {

	JTable timeTable;
	JFrame timeFrame;
	JButton addBtn = new JButton("+");
	JButton saveBtn = new JButton("Save");
	JButton backBtn = new JButton("Back");
	private Route routeToDisplay;
	private String[][] table;
	int column;
	int row;
	int routeIndex;
	DefaultTableModel tableModel;

	public TimeTableObserver(boolean editMode, int _routeIndex) {
		routeIndex = _routeIndex;
		routeToDisplay = SystemManager.timeTable.routeList.get(routeIndex);
		initialTable();
		timeFrame = new JFrame();
		timeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		timeFrame.setLocation(screenSize.width / 2 - 700 / 2,
				screenSize.height / 2 - 600 / 2);
		timeFrame.setSize(700, 600);
		timeTable.setEnabled(editMode);
		timeFrame.add(timeTable);
		timeFrame.setUndecorated(true);
		timeFrame.setAlwaysOnTop(true);
		if (editMode) {
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			timeFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
			topPanel.add(addBtn);
			topPanel.add(saveBtn);
			topPanel.add(backBtn);
			addBtn.addActionListener(this);
			saveBtn.addActionListener(this);
			backBtn.addActionListener(this);
			tableModel.addTableModelListener(this);
		}
		timeFrame.setFocusableWindowState(true);
		timeFrame.setFocusable(true);
		timeFrame.setVisible(true);
	}

	public void initialTable() {
		column = routeToDisplay.journeylist.size() + 1;
		row = routeToDisplay.returnStopNums() * 2 - 1;
		System.out.println(column);
		System.out.println(row);

		table = new String[row][column];

		for (int i = 0; i < (row - 1) / 2; i++) {
			table[i][0] = routeToDisplay.getStop(i).toString();
			table[row - i - 1][0] = routeToDisplay.getStop(i).toString();
			System.out.println(table[i][0]);
		}
		table[(row - 1) / 2][0] = routeToDisplay.getStop((row - 1) / 2)
				.toString();

		for (int i = 1; i < column; i++) {
			for (int j = 0; j < row; j++) {
				table[j][i] = routeToDisplay.journeylist.get(i - 1).time.get(j)
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] timeList = new String[timeTable.getRowCount()];
		ArrayList<Journey> journeyList = new ArrayList<Journey>();
		if (e.getSource().equals(addBtn)) {
			tableModel.addColumn("");
			addBtn.setEnabled(false);
		} else if (e.getSource().equals(saveBtn)) {
			System.out.println(timeTable.getColumnCount());
			for (int i = 1; i < (timeTable.getRowCount() + 1) / 2; i++) {
				routeToDisplay.getStop(i).name = (String) timeTable.getValueAt(
						i, 0);
			}
			for (int i = 1; i < timeTable.getColumnCount(); i++) {

				for (int j = 0; j < timeTable.getRowCount(); j++) {
					timeList[j] = (String) timeTable.getValueAt(j, i);
				}
				ArrayList<Time> tempTimeList;
				try {
					tempTimeList = SystemManager.saveTimeTable(timeList);
					Journey tempJourney = new Journey();
					// routeToDisplay.journeylist=null;
					tempJourney.time = tempTimeList;
					journeyList.add(tempJourney);
					routeToDisplay.journeylist = journeyList;
					System.out.println(routeToDisplay.journeylist.size());

					SystemManager.timeTable.routeList.set(routeIndex,
							routeToDisplay);
					SystemManager.saveToFile();
					SystemManager.readFromFile();
					JOptionPane.showMessageDialog(timeFrame.getContentPane(),
							"Save success", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					// timeTable.revalidate();
					// System.out.println(SystemManager.timeTable.routeList.get(1).journeylist.size());
				} catch (TimeException e1) {
					int columnCount = ((DefaultTableModel) (timeTable
							.getModel())).getColumnCount();
					((DefaultTableModel) (timeTable.getModel()))
							.setColumnCount(columnCount - 1);
					JOptionPane.showMessageDialog(timeFrame.getContentPane(),
							"Time information is not validate", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
				// System.out.println(tempTimeList.size());
			}
			addBtn.setEnabled(true);
		} else if (e.getSource().equals(backBtn)) {
			timeFrame.dispose();
			SystemManager.disposeTimeTableOb();
			SystemManager.stopEditor = new StopEditor(
					SystemManager.timeTable.routeList.get(routeIndex));
		}
		// timeTable.revalidate();
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if (e.getColumn() == 0) {
			tableModel.removeTableModelListener(this);
			int index = e.getFirstRow();
			String value = (String) tableModel.getValueAt(index, 0);
			tableModel.setValueAt(value, tableModel.getRowCount() - index - 1,
					0);
			tableModel.addTableModelListener(this);
		}
	}
}
