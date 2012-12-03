package badstudent.service;

import badstudent.clean.ScheduledClean;

public class CleanThreadService extends Thread {
    // This method is called when the thread runs
    public void run() {
		ScheduledClean scheduledClean = new ScheduledClean(23, 33, 0);
		scheduledClean.start();
		System.out.println("ScheduledClean thread starting, cleaning at 4:30AM every day");
    }
}