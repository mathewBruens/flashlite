package flashlite;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class ScreenShot {
	
	public static void takeScreenShot() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_yyyy@hh_mm_ssa_z");
		Robot robot;
		Calendar now = Calendar.getInstance();
		String fileSeparator = System.getProperty("file.separator");
		String userHomeFolder = System.getProperty("user.home");
		String desktopPath = userHomeFolder.concat(fileSeparator).concat(
				"Desktop");
		try {
			robot = new Robot(AppTray.currentState);
			BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
					Toolkit.getDefaultToolkit().getScreenSize()));
			String screenShotFileName = formatter.format(now.getTime()).concat(
					".jpg");

			ImageIO.write(screenShot, "JPG", new File(desktopPath,
					screenShotFileName));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
