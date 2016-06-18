package System;

import exception.TimeSetException;
import basic.*;
/**
 * Thread will be called repeatedly
 * @author Group 78
 *
 */
public class RunTimeThread extends Thread {
	public RunTimeThread() {
		super();
	}

	public void run() {
		while (true) {
			SystemManager.realTime.timeForward(SystemManager.timeStep);
			SystemManager.Update();
			// System.out.println(SystemManager.realTime);
			// System.out.println("thread");
			try {
				sleep(500);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (SystemManager.realTime.compareTo(new Time(22, 30)) >= 0) {
					this.stop();
				}
			} catch (TimeSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
