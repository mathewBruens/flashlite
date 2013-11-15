import java.util.Timer;
import java.util.TimerTask;

import org.jnativehook.GlobalScreen;

public class Start {

	private static final double MIN_INTERVAL = 5000;
	private static final double MAX_INTERVAL = 15000;
	private static WebcamCapture webcam;

	public static void main(String[] args) {

		webcam = WebcamCapture.getSingleton();
		new FlashliteGUI();

		startTracker();
		startScreenshotCapture();
		startWebcamCapture();

	}

	private static void startScreenshotCapture() {

		final Timer timer = new Timer();

		double randInterval = (Math.random() * (MAX_INTERVAL - MIN_INTERVAL))
				+ MIN_INTERVAL;

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				ScreenshotCapture.takeScreenShot();
				startScreenshotCapture();
			}

		}, (long) randInterval);
	}

	private static void startTracker() {
		// add logic for different monitors
		final InputTracker tracker = new InputTracker((long) MIN_INTERVAL);
		GlobalScreen.getInstance().addNativeKeyListener(tracker);
		GlobalScreen.getInstance().addNativeMouseListener(tracker);

		final Timer inputTrackerTimer = new Timer();

		double randInterval = (Math.random() * (MAX_INTERVAL - MIN_INTERVAL))
				+ MIN_INTERVAL;

		inputTrackerTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println(tracker.toString());
				startTracker();
			}

		}, (long) randInterval);
	}

	private static void startWebcamCapture() {
		// TODO Auto-generated method stub

		final Timer timer = new Timer();

		double randInterval = (Math.random() * (MAX_INTERVAL - MIN_INTERVAL))
				+ MIN_INTERVAL;

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				webcam.takeWebcamPhoto();
				startWebcamCapture();
			}

		}, (long) randInterval);

	}
}
