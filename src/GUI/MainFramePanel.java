package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import drive.*;
import System.*;
import journey.*;

import java.awt.*;
import java.util.*;
/**
 * Depict the graphic for main frame
 * @author Group 78
 *
 */
public class MainFramePanel extends JPanel {

	public Route routeToDraw;
	public ArrayList<Route> routesToDraw = new ArrayList<Route>();

	public void paintComponent(Graphics g) {
		g.setColor(new Color(240,240,240));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		g.drawOval(TimeTable.centralStation.location.x + this.getWidth() / 2
				- 7, TimeTable.centralStation.location.y + this.getHeight() / 2
				- 7, 14, 14);
		g.drawOval(TimeTable.centralStation.location.x + this.getWidth() / 2
				- 5, TimeTable.centralStation.location.y + this.getHeight() / 2
				- 5, 10, 10);
		if (routesToDraw != null) {
			for (int i = 0; i < routesToDraw.size(); i++) {
				Route tempRoute = routesToDraw.get(i);
				for (int j = 0; j < tempRoute.returnStopNums(); j++) {
					Stop tempStop = tempRoute.getStop(j);
					g.drawOval(tempStop.location.x + this.getWidth() / 2 - 5,
							tempStop.location.y + this.getHeight() / 2 - 5, 10,
							10);
					if (j > 0) {
						g.setColor(tempRoute.color);
						Stop lastStop = tempRoute.getStop(j - 1);
						g.drawLine(lastStop.location.x + this.getWidth() / 2,
								lastStop.location.y + this.getHeight() / 2,
								tempStop.location.x + this.getWidth() / 2,
								tempStop.location.y + this.getHeight() / 2);
						g.setColor(Color.BLACK);
					}
				}
			}
			/* Draw Train */
			for (int i = 0; i < SystemManager.driveList.size(); i++) {
				if (i == SystemManager.managerFrame.selection) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLACK);
				}
				Train trainToDraw = SystemManager.driveList.get(i).train;
				g.drawRect(trainToDraw.getLocation().x + this.getWidth() / 2
						- 5, trainToDraw.getLocation().y - 5 + this.getHeight()
						/ 2, 10, 10);
			}
		}
	}

	public MainFramePanel() {
		routesToDraw = SystemManager.timeTable.routeList;
		// System.out.println(routesToDraw.size());
	}

}
