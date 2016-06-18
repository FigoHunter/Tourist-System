package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import drive.*;
import System.*;
/**
 * Depict the driver edit frame
 * @author Group 78
 *
 */
public class DriverEditor implements TableModelListener, ActionListener {
	JFrame editFrame = new JFrame();
	JTable driverTable;
	JButton addBtn = new JButton("+");
	JButton delBtn = new JButton("-");
	JButton backBtn = new JButton("Back");
	DefaultTableModel tableModel;

	public DriverEditor(boolean editable) {
		editFrame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		editFrame.setLocation(screenSize.width / 2 - 700 / 2,
				screenSize.height / 2 - 600 / 2);
		editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editFrame.setSize(700, 600);
		initializeTable();
		JPanel topPanel = new JPanel();
		editFrame.add(BorderLayout.CENTER, new JScrollPane(driverTable));
		if (editable = true) {
			driverTable.setEnabled(true);
			editFrame.add(BorderLayout.NORTH, topPanel);
			topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
			topPanel.add(addBtn);
			topPanel.add(delBtn);
			topPanel.add(backBtn);
			addBtn.addActionListener(this);
			delBtn.addActionListener(this);
			backBtn.addActionListener(this);
			driverTable.getModel().addTableModelListener(this);
		}
		editFrame.setVisible(true);
	}

	public void initializeTable() {
		String[] tableHeader = { "Driver No.", "Name" };
		int driverNum = SystemManager.driverList.size();
		String[][] driverData = new String[driverNum][2];
		for (int i = 0; i < driverNum; i++) {
			driverData[i][0] = SystemManager.driverList.get(i).getNum();
			driverData[i][1] = SystemManager.driverList.get(i).getName();
		}
		tableModel = new DefaultTableModel(driverData, tableHeader);
		driverTable = new JTable(tableModel);
		driverTable.setEnabled(false);
		driverTable.setShowVerticalLines(true);
		driverTable.setShowHorizontalLines(false);
		driverTable.setBackground(new Color(230, 230, 230));
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		saveDriverList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addBtn)) {
			tableModel.addRow(new String[2]);
		} else if (e.getSource().equals(delBtn)) {
			int selection = driverTable.getSelectedRow();
			int rowCount = tableModel.getRowCount();
			tableModel.removeRow(selection);
			tableModel.setRowCount(rowCount - 1);
			saveDriverList();
		} else if (e.getSource().equals(backBtn)) {
			editFrame.dispose();
			SystemManager.disposeDrvierEditor();
			SystemManager.managerFrame = new ManagerFrame();
		}
		driverTable.revalidate();
	}

	public void saveDriverList() {
		int rowCount = tableModel.getRowCount();
		ArrayList<Driver> driverList = new ArrayList<Driver>();
		for (int i = 0; i < rowCount; i++) {
			String driveNum = (String) (tableModel.getValueAt(i, 0));
			String driveName = (String) (tableModel.getValueAt(i, 1));
			Driver tempDriver = new Driver(driveNum, driveName);
			driverList.add(tempDriver);
		}
		SystemManager.driverList = driverList;
		SystemManager.writeDriverList();
		SystemManager.readDriverList();
	}
}
