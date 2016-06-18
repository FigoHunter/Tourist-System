package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import drive.*;
import System.*;
/**
 * main frame
 * @author Group 78
 *
 */
public class ManagerFrame implements ActionListener, MouseListener {

	JFrame mainFrame;
	JFrame attachedFrame;
	JPanel leftPanel;
	JPanel attachedLeftPanel;
	JPanel managerPanel;
	JPanel driverPanel;
	JPanel passengerPanel;
	JButton routeBtn = new JButton("Route");
	JButton driverBtn = new JButton("Driver");
	JButton trainBtn = new JButton("Train");
	JButton driveBtn = new JButton("On Drive");
	JButton attachedPauseBtn = new JButton("Pause");
	JButton stopInfo = new JButton("Stop Info");
	JButton onTrainBtn = new JButton("On Train");
	JTable driveTable;
	JLabel timeLabel = new JLabel();
	CustomTableModel tableModel;
	MainFramePanel mainFramePanel;
	int selection;

	public ManagerFrame() {
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(screenSize.width / 2 - 700 / 2,
				screenSize.height / 2 - 600 / 2);
		mainFrame.setSize(700, 600);

		mainFramePanel = new MainFramePanel();

		timeLabel.setText(SystemManager.getRealTime().toString());
		timeLabel.setFont(new Font("TimesRoman",Font.PLAIN,30));
		
		managerPanel=new JPanel();
		managerPanel.setLayout(new BoxLayout(managerPanel,BoxLayout.Y_AXIS));
		managerPanel.add(routeBtn);
		managerPanel.add(driverBtn);
		managerPanel.add(trainBtn);
		managerPanel.setBorder(BorderFactory.createTitledBorder("Manager"));
		
		driverPanel=new JPanel();
		driverPanel.setLayout(new BoxLayout(driverPanel,BoxLayout.Y_AXIS));
		driverPanel.add(driveBtn);
		driverPanel.setBorder(BorderFactory.createTitledBorder("Driver"));
		
		passengerPanel=new JPanel();
		passengerPanel.setLayout(new BoxLayout(passengerPanel,BoxLayout.Y_AXIS));
		passengerPanel.add(stopInfo);
		passengerPanel.add(onTrainBtn);
		passengerPanel.setBorder(BorderFactory.createTitledBorder("Passenger"));

		leftPanel = new JPanel();
		leftPanel.add(timeLabel);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(managerPanel);
		leftPanel.add(driverPanel);
		leftPanel.add(passengerPanel);

		mainFrame.add(BorderLayout.CENTER, mainFramePanel);
		mainFrame.add(BorderLayout.WEST, leftPanel);
		mainFrame.setVisible(true);

		routeBtn.addActionListener(this);
		driverBtn.addActionListener(this);
		trainBtn.addActionListener(this);
		driveBtn.addActionListener(this);
		stopInfo.addActionListener(this);
		onTrainBtn.addActionListener(this);
	}

	public void refresh() {
		timeLabel.setText(SystemManager.getRealTime().toString());
		mainFramePanel.repaint();
		mainFrame.validate();
	}

	public void initializeDriveTable() {
		int rowNum = SystemManager.driveList.size();
		ArrayList<Drive> driveList = SystemManager.driveList;
		String[] tableHeader = { "Train", "Driver", "Route", "lastStop",
				"nextStop" };
		String[][] table = new String[rowNum][5];
		for (int i = 0; i < rowNum; i++) {
			table[i][0] = driveList.get(i).train.toString();
			table[i][1] = driveList.get(i).driver.toString();
			table[i][2] = "Line "
					+ (SystemManager.timeTable.routeList.indexOf(driveList
							.get(i).route) + 1);
			table[i][3] = driveList.get(i).fromStop.toString();
			table[i][4] = driveList.get(i).toStop.toString();
		}
		tableModel = new CustomTableModel(table, tableHeader);
		driveTable = new JTable(tableModel);
		driveTable.setRowSelectionAllowed(true);
		driveTable.addMouseListener(this);
		driveTable.setRowSelectionInterval(selection, selection);
		driveTable.setShowVerticalLines(true);
		driveTable.setShowHorizontalLines(false);
		driveTable.setBackground(new Color(230, 230, 230));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(routeBtn)) {
			SystemManager.routeEditor = new RouteEditor();
			mainFrame.dispose();
			if (attachedFrame != null) {
				attachedFrame.dispose();
			}
			SystemManager.disposeManagerFrame();
		} else if (e.getSource().equals(driverBtn)) {
			SystemManager.driverEditor = new DriverEditor(true);
			mainFrame.dispose();
			if (attachedFrame != null) {
				attachedFrame.dispose();
			}
			SystemManager.disposeManagerFrame();
		} else if (e.getSource().equals(trainBtn)) {
			SystemManager.trainEditor = new TrainEditor(true);
			mainFrame.dispose();
			if (attachedFrame != null) {
				attachedFrame.dispose();
			}
			SystemManager.disposeManagerFrame();
		} else if (e.getSource().equals(driveBtn)) {
			if (SystemManager.driveList.size() != 0) {
				attachedFrame = new JFrame();
				Dimension screenSize = Toolkit.getDefaultToolkit()
						.getScreenSize();
				attachedFrame.setLocation(screenSize.width / 2 + 50,
						screenSize.height / 2 - 300 / 2);
				attachedFrame.setSize(400, 300);
				initializeDriveTable();
				attachedLeftPanel = new JPanel();
				attachedLeftPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
				attachedLeftPanel.add(attachedPauseBtn);
				attachedFrame.add(BorderLayout.CENTER, new JScrollPane(
						driveTable));
				attachedFrame.add(BorderLayout.NORTH, attachedLeftPanel);
				attachedFrame.setVisible(true);
				attachedPauseBtn.addActionListener(this);
			} else {
				JOptionPane.showMessageDialog(mainFrame.getContentPane(),
						"No train is currently on drive", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource().equals(attachedPauseBtn)) {
			SystemManager.driveList.get(selection).paused = !(SystemManager.driveList
					.get(selection).paused);
		} else if (e.getSource().equals(stopInfo)) {
			SystemManager.stopInfOb = new StopInfOb();
		} else if (e.getSource().equals(onTrainBtn))
		{
			if (SystemManager.driveList.size() != 0) {
			SystemManager.trainInfOb=new TrainInfOb();
			} else {
			JOptionPane.showMessageDialog(mainFrame.getContentPane(),
					"No train is currently on drive", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void reloadTable() {
		if (driveTable != null) {
			int rowNum = SystemManager.driveList.size();
			if (selection >= rowNum) {
				selection = rowNum - 1;
			}
			System.out.println("rowNum" + rowNum);
			ArrayList<Drive> driveList = SystemManager.driveList;
			String[] tableHeader = { "Train", "Driver", "Route", "lastStop",
					"nextStop" };
			String[][] table = new String[rowNum][5];
			for (int i = 0; i < rowNum; i++) {
				table[i][0] = driveList.get(i).train.toString();
				table[i][1] = driveList.get(i).driver.toString();
				table[i][2] = "Line "
						+ (SystemManager.timeTable.routeList.indexOf(driveList
								.get(i).route) + 1);
				table[i][3] = driveList.get(i).fromStop.toString();
				table[i][4] = driveList.get(i).toStop.toString();
			}
			tableModel = new CustomTableModel(table, tableHeader);
			driveTable.setModel(tableModel);
			if (rowNum > 0) {
				driveTable.setRowSelectionInterval(selection, selection);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selection = driveTable.getSelectedRow();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
