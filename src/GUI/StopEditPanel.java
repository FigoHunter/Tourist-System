package GUI;

import java.awt.Color;
import java.awt.Graphics;

import journey.Route;
import journey.Stop;

import javax.swing.*;

import drive.Train;
import System.SystemManager;
import journey.*;

import java.awt.*;
import java.util.*;

import journey.*;
/**
 * Depict graphic for stop edit frame
 * @author Group 78
 *
 */
public class StopEditPanel extends JPanel {
	public Route routeToDraw;
	public Stop unSetStop;
	int driveIndex=-1;

	public StopEditPanel(Route _routeToDraw) {
		routeToDraw = _routeToDraw;
	}

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
		for (int j = 0; j < routeToDraw.returnStopNums(); j++) {
			if ((SystemManager.stopEditor != null && j == SystemManager.stopEditor.selection)
					|| (SystemManager.stopInfOb != null && j == SystemManager.stopInfOb.selection)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.BLACK);
			}
			Stop tempStop = routeToDraw.getStop(j);
			g.drawOval(tempStop.location.x + this.getWidth() / 2 - 5,
					tempStop.location.y + this.getHeight() / 2 - 5, 10, 10);
			g.setColor(Color.BLACK);
			if (j > 0) {
				g.setColor(routeToDraw.color);
				Stop lastStop = routeToDraw.getStop(j - 1);
				g.drawLine(lastStop.location.x + this.getWidth() / 2,
						lastStop.location.y + this.getHeight() / 2,
						tempStop.location.x + this.getWidth() / 2,
						tempStop.location.y + this.getHeight() / 2);
				g.setColor(Color.BLACK);
			}
		}
		if(driveIndex ==-1)
		{
			for (int i = 0; i < SystemManager.driveList.size(); i++) {
				if (SystemManager.driveList.get(i).route.equals(routeToDraw)) {
					Train trainToDraw = SystemManager.driveList.get(i).train;

					g.drawRect(trainToDraw.getLocation().x + this.getWidth() / 2
							- 5, trainToDraw.getLocation().y - 5 + this.getHeight()
							/ 2, 10, 10);
				}
			}
		}
		else if(driveIndex !=-1)
		{
			if (SystemManager.driveList.get(driveIndex).route.equals(routeToDraw)) {
				Train trainToDraw = SystemManager.driveList.get(driveIndex).train;

				g.drawRect(trainToDraw.getLocation().x + this.getWidth() / 2
						- 5, trainToDraw.getLocation().y - 5 + this.getHeight()
						/ 2, 10, 10);
			}
		}
	}
}
