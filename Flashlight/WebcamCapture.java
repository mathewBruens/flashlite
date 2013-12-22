import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;

public class WebcamCapture {
	private final Webcam activeWebcam;

	public WebcamCapture(Webcam activeWebcam) {
		this.activeWebcam = activeWebcam;

	}

	//
	//
	// webcamsImageSizes = new HashMap<Integer, Dimension[]>();
	//
	// Integer index = 0;
	// for (Webcam webcam : webcamList) {
	// Dimension[] webcamSizes = getWebcamSizes(webcam);
	// webcamsImageSizes.put(index, webcamSizes);
	// index++;
	// }
	//
	// }

	// public HashMap<Integer, Dimension[]> getWebcamsImageSizes() {
	// return webcamsImageSizes;
	// }
	//
	// public Dimension[] getWebcamSizes(Webcam webcam) {
	// return webcam.getViewSizes();
	// }
	//
	// public synchronized void setWebcam(int webcamIndex) {
	// this.webcamIndex = webcamIndex;
	//
	// currentWebcam = webcamList.get(webcamIndex);
	// }
	//
	// public synchronized void setWebcamSize(int webcamSizeIndex) {
	//
	// Dimension[] resolutionArray = webcamsImageSizes.get(new Integer(
	// webcamIndex));
	// currentWebcam.setViewSize(resolutionArray[webcamSizeIndex]);
	// }

	public void takeWebcamPhoto() throws IOException, WebcamException {
		BufferedImage bi = null;

		if (activeWebcam.isOpen()) {
			bi = activeWebcam.getImage();
		}

		else {
			activeWebcam.open(true);
			bi = activeWebcam.getImage();
			activeWebcam.close();
		}

		if (bi == null) {
			throw new WebcamException("Unable to capture webcam image");
		}

		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"MM_dd_yyyy@hh_mm_ssa_z");
		String fileSeparator = System.getProperty("file.separator");
		String userHomeFolder = System.getProperty("user.home");
		String desktopPath = userHomeFolder.concat(fileSeparator).concat(
				"Desktop");

		String screenShotFileName = formatter.format(now.getTime()).concat(
				".jpg");
		String prefix = "Webcam_";
		screenShotFileName = prefix + screenShotFileName;

		ImageIO.write(bi, "JPG", new File(desktopPath, screenShotFileName));

	}

}
