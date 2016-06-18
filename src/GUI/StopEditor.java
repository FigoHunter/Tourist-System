package GUI;

import java.awt.*;
import java.awt.event.*;

import journey.*;
import System.SystemManager;
import basic.*;

import javax.swing.*;
import javax.swing.event.*;

import exception.*;
/**
 * Stop edit frame
 * @author Group 78
 *
 */
public class StopEditor implements ActionListener, ListSelectionListener,
		MouseMotionListener, MouseListener {
	JFrame stopEditFrame;
	JPanel leftPanel = new JPanel(new GridLayout(2, 1, 1, 1));
	JPanel lefttopPanel = new JPanel();
	JList stopList;
	JButton addStop = new JButton("Add");
	JButton deleteStop = new JButton("Delete");
	JButton cancelButton = new JButton("Cancel");
	JButton backButton = new JButton("Back");
	JButton modifyButton = new JButton("Modify");
	JButton timeTableButton = new JButton("TimeTable");
	StopEditPanel stopEditPanel;
	int selection;
	int routeIndex;
	Route routeToDraw;

	public StopEditor(Route _routeToDraw) {
		routeToDraw = _routeToDraw;
		routeIndex = SystemManager.timeTable.routeList.indexOf(routeToDraw);
		stopEditFrame = new JFrame();
		stopEditFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		stopEditFrame.setLocation(screenSize.width / 2 - 700 / 2,
				screenSize.height / 2 - 600 / 2);
		stopEditFrame.setSize(700, 600);
		stopList = new JList(routeToDraw.getList().toArray());
		stopList.setBorder(BorderFactory.createTitledBorder("Stations"));
		stopList.setBackground(new Color(240, 240, 240));
		stopList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stopList.setSelectedIndex(0);
		stopEditPanel = new StopEditPanel(routeToDraw);
		stopEditFrame.getContentPane().add(BorderLayout.CENTER, stopEditPanel);
		stopEditFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
		leftPanel.add(lefttopPanel);
		leftPanel.add(stopList);
		lefttopPanel.setLayout(new BoxLayout(lefttopPanel, BoxLayout.Y_AXIS));
		lefttopPanel.add(backButton);
		lefttopPanel.add(addStop);
		lefttopPanel.add(deleteStop);
		lefttopPanel.add(cancelButton);
		lefttopPanel.add(timeTableButton);
		addStop.addActionListener(this);
		deleteStop.addActionListener(this);
		backButton.addActionListener(this);
		cancelButton.addActionListener(this);
		cancelButton.setEnabled(false);
		stopList.addListSelectionListener(this);
		stopEditFrame.setVisible(true);
		timeTableButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addStop)) {

			stopEditPanel.unSetStop = new Stop();
			try {
				stopEditPanel.routeToDraw.addStation(stopEditPanel.unSetStop,
						selection + 1);
			} catch (IndexOutSizeException e1) {
				e1.printStackTrace();
			}
			stopEditPanel.addMouseMotionListener(this);
			stopEditPanel.addMouseListener(this);
			cancelButton.setEnabled(true);
			addStop.setEnabled(false);
			deleteStop.setEnabled(false);
			stopList.setEnabled(false);
			backButton.setEnabled(false);
		} else if (e.getSource().equals(deleteStop)) {
			try {
				if (selection != 0) {
					stopEditPanel.routeToDraw.deleteStation(selection);
					refreshList();
					stopEditPanel.repaint();
					try {
						SystemManager.saveToFile();
					} catch (Exception e1) {
						System.out.println("data saving fail");
					}
				}
			} catch (IndexOutSizeException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(cancelButton)) {
			try {
				stopEditPanel.removeMouseMotionListener(this);
				stopEditPanel.removeMouseListener(this);
				stopEditPanel.routeToDraw.deleteStation(selection + 1);
				stopEditPanel.repaint();
				cancelButton.setEnabled(false);
				addStop.setEnabled(true);
				deleteStop.setEnabled(true);
				stopList.setEnabled(true);
				refreshList();
				backButton.setEnabled(true);
			} catch (IndexOutSizeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource().equals(backButton)) {
			SystemManager.reloadRouteEditor();
			stopEditFrame.dispose();
			SystemManager.disposeStopEditor();
		} else if (e.getSource().equals(timeTableButton)) {
			SystemManager.timeTableOb = new TimeTableObserver(true, routeIndex);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		selection = stopList.getSelectedIndex();
		stopEditPanel.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		stopEditPanel.unSetStop.setStop(new Vector2(e.getX()
				- stopEditPanel.getWidth() / 2, e.getY()
				- stopEditPanel.getHeight() / 2));
		stopEditPanel.repaint();
	}

	public void mouseClicked(MouseEvent e) {
		try {
			try {
				SystemManager.saveToFile();
			} catch (Exception e1) {
				System.out.println("data saving fail");
			}
			stopEditPanel.removeMouseMotionListener(this);
			stopEditPanel.removeMouseListener(this);
			stopEditPanel.repaint();
			cancelButton.setEnabled(false);
			addStop.setEnabled(true);
			deleteStop.setEnabled(true);
			stopList.setEnabled(true);
			backButton.setEnabled(true);
			refreshList();
			SystemManager.timeTableOb = new TimeTableObserver(true,
					SystemManager.timeTable.routeList.indexOf(routeToDraw));
			stopEditFrame.dispose();
			SystemManager.disposeStopEditor();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private void refreshList() {
		leftPanel.remove(stopList);
		stopList = new JList(routeToDraw.getList().toArray());
		stopList.setBorder(BorderFactory.createTitledBorder("Stop"));
		stopList.setBackground(new Color(240, 240, 240));
		stopList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stopList.setSelectedIndex(0);
		stopList.addListSelectionListener(this);
		selection = 0;
		leftPanel.add(stopList);
		stopEditFrame.setVisible(true);
	}

	public void refresh() {
		stopEditPanel.repaint();
	}

	public void mouseDragged(MouseEvent e) {
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
