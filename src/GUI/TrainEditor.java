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
 * Train edit frame
 * @author Group 78
 *
 */
public class TrainEditor implements TableModelListener, ActionListener {
	JFrame editFrame = new JFrame();
	JTable trainTable;
	JButton addBtn = new JButton("+");
	JButton delBtn = new JButton("-");
	JButton backBtn = new JButton("back");
	DefaultTableModel tableModel;

	public TrainEditor(boolean editable) {
		editFrame = new JFrame();
		editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		editFrame.setLocation(screenSize.width / 2 - 700 / 2,
				screenSize.height / 2 - 600 / 2);
		editFrame.setSize(700, 600);
		initializeTable();
		JPanel topPanel = new JPanel();
		editFrame.add(BorderLayout.CENTER, new JScrollPane(trainTable));
		if (editable = true) {
			trainTable.setEnabled(true);
			editFrame.add(BorderLayout.NORTH, topPanel);
			topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
			topPanel.add(addBtn);
			topPanel.add(delBtn);
			topPanel.add(backBtn);
			addBtn.addActionListener(this);
			delBtn.addActionListener(this);
			backBtn.addActionListener(this);
			trainTable.getModel().addTableModelListener(this);
		}
		editFrame.setVisible(true);
	}

	public void initializeTable() {
		String[] tableHeader = { "Train No.", "Capacity" };
		int trainNum = SystemManager.trainList.size();
		String[][] trainData = new String[trainNum][2];
		for (int i = 0; i < trainNum; i++) {
			trainData[i][0] = SystemManager.trainList.get(i).getTrainNum();
			trainData[i][1] = String.valueOf(SystemManager.trainList.get(i)
					.getCapacity());
		}
		tableModel = new DefaultTableModel(trainData, tableHeader);
		trainTable = new JTable(tableModel);
		trainTable.setEnabled(false);
		trainTable.setShowVerticalLines(true);
		trainTable.setShowHorizontalLines(false);
		trainTable.setBackground(new Color(230, 230, 230));
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		saveTrainList();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addBtn)) {
			tableModel.addRow(new String[2]);
		} else if (e.getSource().equals(delBtn)) {
			int selection = trainTable.getSelectedRow();
			int rowCount = tableModel.getRowCount();
			tableModel.removeRow(selection);
			tableModel.setRowCount(rowCount - 1);
			saveTrainList();
		} else if (e.getSource().equals(backBtn)) {
			editFrame.dispose();
			SystemManager.disposeTrainEditor();
			SystemManager.managerFrame = new ManagerFrame();
		}
		trainTable.revalidate();
	}

	public void saveTrainList() {
		int rowCount = tableModel.getRowCount();
		ArrayList<Train> trainList = new ArrayList<Train>();
		for (int i = 0; i < rowCount; i++) {
			String trainNum = (String) (tableModel.getValueAt(i, 0));
			int trainCapacity = Integer.parseInt((String) (tableModel
					.getValueAt(i, 1)));
			Train tempTrain = new Train(trainNum, trainCapacity);
			trainList.add(tempTrain);
		}
		SystemManager.trainList = trainList;
		SystemManager.writeTrainList();
		SystemManager.readTrainList();
	}

}
