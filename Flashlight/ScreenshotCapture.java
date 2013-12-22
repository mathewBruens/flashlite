import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class ScreenshotCapture {
	GraphicsDevice activeScreen;
	Settings settings;

	public ScreenshotCapture(GraphicsDevice activeScreen, Settings settings) {
		this.activeScreen = activeScreen;
		this.settings = settings;
	}

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * 
	 * @param srcImg
	 *            - source image to scale
	 * @param w
	 *            - desired width
	 * @param h
	 *            - desired height
	 * @return - the new resized image
	 */
	private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, 1);
		// BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);

		g2.dispose();
		return resizedImg;
	}

	public void takeScreenshot() throws IOException, AWTException,
			SecurityException {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"MM_dd_yyyy@hh_mm_ssa_z");
		Robot robot;
		Calendar now = Calendar.getInstance();
		String fileSeparator = System.getProperty("file.separator");
		String userHomeFolder = System.getProperty("user.home");
		String desktopPath = userHomeFolder.concat(fileSeparator).concat(
				"Desktop");

		robot = new Robot(activeScreen);
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
				Toolkit.getDefaultToolkit().getScreenSize()));

		System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
		System.out.println(settings.getActiveScreenDimension());

		// I think equals needs to be overridded to compare two dimensions
		if (!(Toolkit.getDefaultToolkit().getScreenSize().equals(settings
				.getActiveScreenDimension()))) {
			screenShot = getScaledImage(screenShot, (int) settings
					.getActiveScreenDimension().getWidth(), (int) settings
					.getActiveScreenDimension().getHeight());
		}

		String screenShotFileName = formatter.format(now.getTime()).concat(
				".jpg");

		ImageIO.write(screenShot, "JPG", new File(desktopPath,
				screenShotFileName));

	}

}
