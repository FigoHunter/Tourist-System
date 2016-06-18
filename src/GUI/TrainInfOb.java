package GUI;

import java.awt.BorderLayout;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import drive.*;
import System.*;
/**
 * Depict information on train
 * @author Group 78
 *
 */
public class TrainInfOb {
	JFrame frame;
	Drive drive;
	StopEditPanel routePanel;
	int index=-1;
	DefaultTableModel tableModel;
	JTable timeTable;
	public TrainInfOb()
	{
		pickDrive();
		initializeTimeTable();
		frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - 800 / 2,
				screenSize.height / 2 - 600 / 2);
		frame.setSize(800, 600);
		
		routePanel=new StopEditPanel(drive.route);
		routePanel.driveIndex=index;
		
		frame.add(BorderLayout.CENTER,routePanel);
		frame.add(BorderLayout.EAST,new JScrollPane(timeTable));
		frame.setVisible(true);
	}
	private void pickDrive()
	{
		index = (int)(Math.random()*SystemManager.driveList.size());
		drive = SystemManager.driveList.get(index);
	}
	private void initializeTimeTable() {
		int column = 2;
		int row = drive.route.returnStopNums() * 2 - 1;
		//System.out.println(column);
		//System.out.println(row);

		String[][] table = new String[row][column];
		String[] tableHeader = {"Stop","Time"};
		for (int i = 0; i < (row - 1) / 2; i++) {
			table[i][0] = drive.route.getStop(i).toString();
			table[row - i - 1][0] = drive.route.getStop(i).toString();
			System.out.println(table[i][0]);
		}
		table[(row - 1) / 2][0] = drive.route.getStop((row - 1) / 2).toString();

			for (int j = 0; j < row; j++) {
				table[j][1] = drive.journey.time.get(j)
						.toString();
			}
		timeTable = new JTable(table,tableHeader);
		/*
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				timeTable.setValueAt(table[i][j], i, j);
				System.out.println(timeTable.getValueAt(i, j));
			}
		}
		*/
		//tableModel = (DefaultTableModel) timeTable.getModel();
		timeTable.setShowGrid(true);
		timeTable.setShowHorizontalLines(true);
		timeTable.setGridColor(Color.BLACK);
	}
	public void dispose(){
		frame.dispose();
	}
	public void refresh()
	{
		routePanel.repaint();
	}
}
