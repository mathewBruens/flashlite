import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.github.sarxos.webcam.WebcamException;

public class Controller {
	public static void main(String[] args) {
		Controller controller = new Controller();
	}

	Settings settings;
	FlashliteSystemTray tray;

	private final long MIN_INTERVAL = 5000;
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private FileHandler log;

	private final long MAX_INTERVAL = 15000;

	public Controller() {
		try {
			String fileSeparator = System.getProperty("file.separator");
			String userHomeFolder = System.getProperty("user.home");
			String desktopPath = userHomeFolder.concat(fileSeparator).concat(
					"Desktop/Log.txt");
			log = new FileHandler(desktopPath);
			LOGGER.addHandler(log);
			settings = new Settings();
		} catch (WebcamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addShutdownHook();
		startSystemTray();
		startInputTracker();
		startScreenCapture();
		startWebcamCapture();
	}

	public void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Flashlite exited.");
			}
		});
	}

	public void startInputTracker() {

		Thread thread = new Thread(new Runnable() {
			InputTracker tracker;

			@Override
			public void run() {

				while (true) {
					long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
					tracker = new InputTracker();
					try {
						Thread.sleep(randInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(tracker.toString());
					tracker.unRegister();
				}
			}
		});
		thread.start();

	}

	public void startScreenCapture() {
		Thread thread = new Thread(new Runnable() {
			ScreenshotCapture screenshotCapture;

			@Override
			public void run() {
				while (true) {
					long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
					try {
						Thread.sleep(randInterval);
						screenshotCapture = new ScreenshotCapture(settings
								.getActiveScreen(), settings);
						screenshotCapture.takeScreenshot();

					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (AWTException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public void startSystemTray() {
		try {
			tray = new FlashliteSystemTray(settings);
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public void startWebcamCapture() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				WebcamCapture webcam;
				while (true) {
					long randInterval = (long) ((Math.random() * (MAX_INTERVAL - MIN_INTERVAL)) + MIN_INTERVAL);
					try {
						Thread.sleep(randInterval);
						webcam = new WebcamCapture(settings.getActiveWebcam());
						webcam.takeWebcamPhoto();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (WebcamException e) {
						LOGGER.info("Error! Unable to webcam, will try again later."
								+ e.getMessage() + "\n");
						tray.displayErrorMessage("Error!",
								"Unable to open webcam, will try again later."
										+ e.getMessage());

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

}
