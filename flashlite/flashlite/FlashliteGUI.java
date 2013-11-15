import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.sarxos.webcam.Webcam;

public class FlashliteGUI {

	private class webcamMenuItemActionListener implements ItemListener {
		private final int index;

		public webcamMenuItemActionListener(int index) {

			this.index = index;
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			webcam.setWebcam(index);

		}
	}

	private class WebcamResolutionMenuItemListener implements ItemListener {
		int index;

		public WebcamResolutionMenuItemListener(int index) {
			super();
			this.index = index;
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			webcam.setWebcamSize(index);

		}

	}

	SystemTray tray;
	int NEW_WIDTH = 16;
	int NEW_HEIGHT = 16;

	GraphicsEnvironment ge;
	GraphicsDevice[] screens;

	static GraphicsDevice currentState = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	WebcamCapture webcam = WebcamCapture.getSingleton();

	public FlashliteGUI() {

		if (!SystemTray.isSupported()) {
			return;
		}

		Image icon = Toolkit.getDefaultToolkit().getImage(
				FlashliteGUI.class.getResource("/Flashlight-32x32.png"));
		Image scaledIcon = icon.getScaledInstance(NEW_WIDTH, NEW_HEIGHT,
				java.awt.Image.SCALE_SMOOTH);

		tray = SystemTray.getSystemTray();
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		screens = ge.getScreenDevices();

		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(scaledIcon, null, popup);
		final SystemTray systemTray = SystemTray.getSystemTray();

		MenuItem exitItem = new MenuItem("Exit");
		Menu monitorMenu = new Menu("Capture monitor:");
		Menu webcamMenu = new Menu("Webcam:");
		Menu webcamResolutionMenu = new Menu("Resolutions:");

		initWebcamMenu(webcamMenu);
		initWebcamResolutionMenu(webcamResolutionMenu);
		initMonitorsMenu(monitorMenu);

		popup.add(monitorMenu);
		popup.addSeparator();
		popup.add(webcamMenu);
		popup.add(webcamResolutionMenu);
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
		}

	}

	private void initMonitorsMenu(Menu monitorMenu) {

		for (final GraphicsDevice screen : screens) {

			CheckboxMenuItem item = new CheckboxMenuItem(screen.toString());

			monitorMenu.add(item);
			item.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					currentState = screen;

				}

			});
		}

	}

	private void initWebcamMenu(Menu webcamCapture) {
		ArrayList<Webcam> list = new ArrayList<Webcam>(webcam.getWebcamList());

		int index = 0;
		for (Webcam webcam : list) {

			CheckboxMenuItem item = new CheckboxMenuItem(webcam.toString());
			item.addItemListener(new webcamMenuItemActionListener(index));
			webcamCapture.add(item);
			index++;
		}

	}

	private void initWebcamResolutionMenu(Menu webcamCaptureSizes) {
		HashMap<Integer, Dimension[]> sizesHash = webcam.getWebcamsImageSizes();

		System.out.println(sizesHash.size());

		for (int x = 0; x < sizesHash.size(); x++) {

			Dimension[] temp = sizesHash.get(new Integer(x));

			for (int y = 0; y < temp.length; y++) {

				CheckboxMenuItem item = new CheckboxMenuItem(temp[y].toString());
				// System.out.println(temp.length);

				item.addItemListener(new WebcamResolutionMenuItemListener(y));

				webcamCaptureSizes.add(item);
			}

		}

	}

}// end appclass

