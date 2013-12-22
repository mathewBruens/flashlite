import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

public class SettingsGUI extends JFrame implements Runnable, WebcamListener,
		WindowListener, UncaughtExceptionHandler, ItemListener, ActionListener,
		ComponentListener {

	private static final long serialVersionUID = 1L;

	private Webcam webcam = null;
	private WebcamPanel panel = null;

	private WebcamPicker picker = null;

	private Dimension activeWebcamDimension = null;

	private final Settings settings;

	public SettingsGUI(Settings settings) {
		this.settings = settings;
		activeWebcamDimension = settings.activeWebcamDimension;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String type = e.getActionCommand();

		switch (type) {
		case "Dimension":
			settings.setActiveWebcamDimension((Dimension) ((JComboBox) e
					.getSource()).getSelectedItem());
			break;
		case "Webcam":
			settings.setActiveWebcam((Webcam) ((JComboBox) e.getSource())
					.getSelectedItem());
			break;
		case "GraphicsDevice":
			settings.setActiveScreen((GraphicsDevice) ((JComboBox) e
					.getSource()).getSelectedItem());
			break;
		case "ScreenSize":
			settings.setActiveScreenDimension((String) ((JComboBox) e
					.getSource()).getSelectedItem());
			break;

		default:
			break;
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		validate();
		repaint();

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void initWindow() {
		setTitle("Java Webcam Capture POC");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		addWindowListener(this);
		addComponentListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		// System.out.println("itemState " + e.getItem().getClass());
		if (e.getItem() != webcam) {
			if (webcam != null) {

				final WebcamPanel tmp = panel;

				remove(panel);

				webcam.removeWebcamListener(this);

				webcam = (Webcam) e.getItem();
				settings.activeWebcam = webcam;
				settings.setActiveWebcamDimension(WebcamResolution.VGA
						.getSize());
				// webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);

				System.out.println("selected " + webcam.getName());

				panel = new WebcamPanel(webcam, false);

				add(panel, BorderLayout.CENTER);

				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						tmp.stop();
						panel.start();
						panel.setVisible(true);
						// while (true) {
						// validate();
						// repaint();
						// }
					}
				});

				thread.setDaemon(true);
				thread.start();
			}
		}

		validate();
		repaint();

	}

	@Override
	public void run() throws WebcamException {
		initWindow();

		JPanel settingsPanel = new JPanel(new FlowLayout());

		settingsPanel.add(new JLabel("Select screen:"));

		JComboBox<GraphicsDevice> deviceBox = new JComboBox<GraphicsDevice>(
				settings.getScreens());

		for (Object device : settings.getScreens()) {
			System.out.println(device.getClass());
		}
		deviceBox.setActionCommand("GraphicsDevice");
		deviceBox.addActionListener(this);
		// deviceBox.setSelectedItem(settings.getActiveScreen());

		settingsPanel.add(deviceBox);

		JComboBox<String> deviceQualityBox = new JComboBox<String>(
				settings.getScreenSizeQuality());

		deviceQualityBox.setActionCommand("ScreenSize");
		deviceQualityBox.addActionListener(this);

		settingsPanel.add(deviceQualityBox);

		settingsPanel.add(new JLabel("Select webcam resolution:"));

		JComboBox<Dimension> webcamSizeBox = new JComboBox<Dimension>(
				settings.getActiveWebcamDimensions());
		webcamSizeBox.setActionCommand("Dimension");
		settingsPanel.add(webcamSizeBox);
		webcamSizeBox.addActionListener(this);
		// webcamSizeBox.setSelectedItem(settings.getActiveWebcamDimension());
		settingsPanel.add(new JLabel("Select webcam:"));

		picker = new WebcamPicker();
		// picker.addActionListener(this);
		picker.setActionCommand("Webcam");
		picker.addItemListener(this);

		webcam = picker.getSelectedWebcam();

		if (webcam == null) {
			System.out.println("No webcams found...");
			System.exit(1);
		}

		// webcam.setViewSize(WebcamResolution.QQVGA.getSize());

		// webcam.addWebcamListener(SettingsGUI.this);
		webcam.addWebcamListener(this);

		panel = new WebcamPanel(webcam, false);
		panel.setFPSDisplayed(true);
		settingsPanel.add(picker);
		add(settingsPanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);

		pack();
		setVisible(true);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				panel.start();
				while (true) {
					validate();
					repaint();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();

	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam closed");
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("webcam disposed");

	}

	@Override
	public void webcamImageObtained(WebcamEvent we) {
		// do nothing
	}

	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam open");
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		webcam.close();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("webcam viewer resumed");
		panel.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("webcam viewer paused");
		panel.pause();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
