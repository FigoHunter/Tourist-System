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
 * Route edit frame
 * @author Group 78
 *
 */
public class RouteEditor implements ActionListener, MouseMotionListener,
		MouseListener, ListSelectionListener {

	JFrame editFrame;
	RouteEditPanel routeEditPanel;
	JButton createButton = new JButton("Create");
	JButton cancelButton = new JButton("Cancel");
	JButton editButton = new JButton("Edit");
	JButton backBtn = new JButton("Back");
	static boolean setState = false;
	JPanel leftPanel = new JPanel(new GridLayout(2, 1, 1, 1));
	JPanel lefttopPanel = new JPanel();
	// JPanel leftbottomPanel = new JPanel();
	JList journeyList;
	int selection;

	public RouteEditor() {
		editFrame = new JFrame();
		editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		editFrame.setLocation(screenSize.width / 2 - 700 / 2,
				screenSize.height / 2 - 600 / 2);
		editFrame.setSize(700, 600);
		routeEditPanel = new RouteEditPanel();
		CustomCellRenderer cellRenderer = new CustomCellRenderer();
		journeyList = new JList(SystemManager.timeTable.routeList.toArray());
		journeyList.setBorder(BorderFactory.createTitledBorder("Journey"));
		journeyList.setBackground(new Color(240, 240, 240));
		journeyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		journeyList.setCellRenderer(cellRenderer);
		editFrame.getContentPane().add(BorderLayout.CENTER, routeEditPanel);
		editFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
		lefttopPanel.setLayout(new BoxLayout(lefttopPanel, BoxLayout.Y_AXIS));
		lefttopPanel.add(createButton);
		lefttopPanel.add(editButton);
		lefttopPanel.add(cancelButton);
		lefttopPanel.add(backBtn);
		leftPanel.add(lefttopPanel);
		leftPanel.add(journeyList);
		cancelButton.setEnabled(false);
		journeyList.setSelectedIndex(0);
		createButton.addActionListener(this);
		journeyList.addListSelectionListener(this);
		editButton.addActionListener(this);
		cancelButton.addActionListener(this);
		backBtn.addActionListener(this);
		editFrame.setVisible(true);
	}

	public void refresh() {
		routeEditPanel.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(createButton)) {
			if (!setState) {
				lefttopPanel.add(cancelButton);
				cancelButton.setEnabled(true);
				editButton.setEnabled(false);
				routeEditPanel.addMouseMotionListener(this);
				routeEditPanel.addMouseListener(this);
				routeEditPanel.createRoute = new Route();
				routeEditPanel.routesToDraw.add(routeEditPanel.createRoute);
				routeEditPanel.unSetStop = new Stop(new Vector2(0, 0));
				routeEditPanel.repaint();
				setState = true;
			} else {
				cancelButton.setEnabled(false);
				editButton.setEnabled(true);
				routeEditPanel.removeMouseMotionListener(this);
				routeEditPanel.removeMouseListener(this);
				SystemManager.timeTable.routeList = routeEditPanel.routesToDraw;
				SystemManager.saveToFile();
				routeEditPanel.createRoute = null;
				routeEditPanel.unSetStop = null;
				routeEditPanel.repaint();
				setState = false;
				leftPanel.remove(journeyList);
				journeyList = new JList(
						SystemManager.timeTable.routeList.toArray());
				journeyList.setBorder(BorderFactory
						.createTitledBorder("Journey"));
				journeyList.setBackground(new Color(240, 240, 240));
				journeyList
						.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				leftPanel.add(journeyList);
				editFrame.setVisible(true);
				SystemManager.timeTableOb = new TimeTableObserver(true,
						routeEditPanel.routesToDraw.size() - 1);
			}
		} else if (e.getSource().equals(cancelButton)) {
			cancelButton.setEnabled(false);
			editButton.setEnabled(true);
			routeEditPanel.removeMouseMotionListener(this);
			routeEditPanel.removeMouseListener(this);
			routeEditPanel.routesToDraw.remove(routeEditPanel.createRoute);
			routeEditPanel.createRoute = null;
			routeEditPanel.unSetStop = null;
			routeEditPanel.repaint();
			setState = false;
		} else if (e.getSource().equals(editButton)) {
			Route routeToEdit = SystemManager.timeTable.routeList
					.get(selection);
			boolean onDrive = false;
			for (int i = 0; i < routeToEdit.journeylist.size(); i++) {
				onDrive = onDrive | routeToEdit.journeylist.get(i).onDrive;
			}
			if (onDrive) {
				JOptionPane.showMessageDialog(editFrame.getContentPane(),
						"Route is currently on drive", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				SystemManager.stopEditor = new StopEditor(routeToEdit);
				editFrame.dispose();
				SystemManager.disposeRouteEditor();
			}
		} else if (e.getSource().equals(backBtn)) {
			SystemManager.managerFrame = new ManagerFrame();
			editFrame.dispose();
			SystemManager.disposeRouteEditor();
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		try {
			routeEditPanel.unSetStop.setStop(new Vector2(e.getX()
					- routeEditPanel.getWidth() / 2, e.getY()
					- routeEditPanel.getHeight() / 2));
			routeEditPanel.repaint();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(routeEditPanel)) {
			try {
				routeEditPanel.createRoute.addStation(routeEditPanel.unSetStop,
						routeEditPanel.createRoute.returnStopNums());
				routeEditPanel.unSetStop = new Stop();
				routeEditPanel.repaint();
			} catch (IndexOutSizeException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		selection = journeyList.getSelectedIndex();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
