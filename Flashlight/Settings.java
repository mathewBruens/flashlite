import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.github.sarxos.webcam.*;


public class Settings {

	GraphicsEnvironment ge = null;

	GraphicsDevice[] screens = null;

	GraphicsDevice activeScreen = null;
	Dimension activeScreenDimension = null;

	Dimension[] screenDimensions = null;

	Webcam activeWebcam = null;

	ArrayList<Webcam> webcamList = null;

	Dimension[] activeWebcamDimensions = null;

	Dimension activeWebcamDimension = null;

	Map<String, Dimension> screenSizeMap = new HashMap<>();

	String[] screenSizeQuality = { "High", "Medium", "Low" };

	final Dimension QQVGA = new Dimension(176, 144);

	final Dimension QVGA = new Dimension(320, 240);

	final Dimension VGA = new Dimension(640, 480);

	final Dimension XGA = new Dimension(1024, 768);

	final Dimension HD720 = new Dimension(1280, 720);

	public Settings() throws WebcamException, TimeoutException {
		initScreenSizeMap();
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		screens = ge.getScreenDevices();
		activeScreen = ge.getDefaultScreenDevice();
		activeScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		activeWebcam = Webcam.getDefault();
		// activeWebcam.setViewSize(WebcamResolution.VGA.getSize());
		webcamList = new ArrayList<Webcam>(Webcam.getWebcams(5000));
		activeWebcamDimensions = activeWebcam.getViewSizes();
		// activeWebcamDimension = WebcamResolution.VGA.getSize();
		// setActiveWebcamDimension(activeWebcamDimension);
	}

	public GraphicsDevice getActiveScreen() {
		return activeScreen;
	}

	public Dimension getActiveScreenDimension() {
		return activeScreenDimension;
	}

	public Webcam getActiveWebcam() {
		return activeWebcam;
	}

	public Dimension getActiveWebcamDimension() {
		return activeWebcamDimension;
	}

	public Dimension[] getActiveWebcamDimensions() {
		return activeWebcamDimensions;
	}

	public GraphicsEnvironment getGe() {
		return ge;
	}

	public Dimension[] getscreenDimensions() {
		return screenDimensions;
	}

	public GraphicsDevice[] getScreens() {
		return screens;
	}

	public String[] getScreenSizeQuality() {
		return screenSizeQuality;
	}

	public ArrayList<Webcam> getWebcamList() {
		return webcamList;
	}

	public void initScreenSizeMap() {
		Dimension maxDimension = Toolkit.getDefaultToolkit().getScreenSize();
		double maxHeight = maxDimension.getHeight();
		@SuppressWarnings("unused")
		double maxWidth = maxDimension.getWidth();
		screenSizeMap.put("High", maxDimension);

		if (screenSizeMap.get("High").width > 640) {
			screenSizeMap.put("Medium", VGA);
			screenSizeMap.put("Low", QVGA);
		}
	}

	public void setActiveScreen(GraphicsDevice activeScreen) {
		this.activeScreen = activeScreen;
	}

	public void setActiveScreenDimension(String quality) {
		Dimension dimension = screenSizeMap.get(quality);
		if (dimension == null) {
			activeScreenDimension = (Toolkit.getDefaultToolkit()
					.getScreenSize());
		} else {
			activeScreenDimension = dimension;
		}
	}

	public void setActiveWebcam(Webcam activeWebcam) {
		this.activeWebcam = activeWebcam;
	}

	public void setActiveWebcamDimension(final Dimension activeWebcamDimension) {
		this.activeWebcamDimension = activeWebcamDimension;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				while (activeWebcam.isOpen()) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				activeWebcam.setViewSize(activeWebcamDimension);

			}
		}, "Change Webcam Dimension Thread");

		t.start();
	}

	public void setActiveWebcamDimensions(Dimension[] activeWebcamDimensions) {
		this.activeWebcamDimensions = activeWebcamDimensions;
	}

	public void setGe(GraphicsEnvironment ge) {
		this.ge = ge;
	}

	public void setScreenDimensions(Dimension[] screenDimension) {
		this.screenDimensions = screenDimensions;
	}

	public void setScreens(GraphicsDevice[] screens) {
		this.screens = screens;
	}

	public void setScreenSizeQuality(String[] screenSizeQuality) {
		this.screenSizeQuality = screenSizeQuality;
	}

	public void setWebcamList(ArrayList<Webcam> webcamList) {
		this.webcamList = webcamList;
	}

}
