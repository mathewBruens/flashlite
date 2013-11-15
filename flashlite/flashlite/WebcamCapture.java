import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;

public class WebcamCapture {

	public static WebcamCapture getSingleton() {
		if (SingletonWebcamPhotoInstance == null) {
			SingletonWebcamPhotoInstance = new WebcamCapture();
		}
		return SingletonWebcamPhotoInstance;
	}

	private Webcam currentWebcam;
	private int webcamIndex = 0;
	private final int webcamSizeIndex = 0;
	private ArrayList<Webcam> webcamList;

	private static WebcamCapture SingletonWebcamPhotoInstance = null;

	// ExecutorService myExecutor = Executors.newCachedThreadPool();

	private final HashMap<Integer, Dimension[]> webcamsImageSizes;

	private WebcamCapture() {

		currentWebcam = Webcam.getDefault();

		try {
			webcamList = new ArrayList<Webcam>(Webcam.getWebcams(5000));
		} catch (WebcamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("Unable to get webcam devices.");
			e.printStackTrace();
		}

		webcamsImageSizes = new HashMap<Integer, Dimension[]>();

		Integer index = 0;
		for (Webcam webcam : webcamList) {
			Dimension[] webcamSizes = getWebcamSizes(webcam);
			webcamsImageSizes.put(index, webcamSizes);
			index++;
		}

	}

	public ArrayList<Webcam> getWebcamList() {
		return webcamList;
	}

	public HashMap<Integer, Dimension[]> getWebcamsImageSizes() {
		return webcamsImageSizes;
	}

	public Dimension[] getWebcamSizes(Webcam webcam) {
		return webcam.getViewSizes();
	}

	public void setWebcam(int webcamIndex) {
		this.webcamIndex = webcamIndex;

		currentWebcam = webcamList.get(webcamIndex);
	}

	public void setWebcamSize(int webcamSizeIndex) {

		Dimension[] resolutionArray = webcamsImageSizes.get(new Integer(
				webcamIndex));
		currentWebcam.setViewSize(resolutionArray[webcamSizeIndex]);
	}

	public void takeWebcamPhoto() {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"MM_dd_yyyy@hh_mm_ssa_z");
		Calendar now = Calendar.getInstance();
		String fileSeparator = System.getProperty("file.separator");
		String userHomeFolder = System.getProperty("user.home");
		String desktopPath = userHomeFolder.concat(fileSeparator).concat(
				"Desktop");

		try {

			currentWebcam.open();
			BufferedImage bi = currentWebcam.getImage();
			currentWebcam.close();

			String screenShotFileName = formatter.format(now.getTime()).concat(
					".jpg");
			String prefix = "Webcam_";
			screenShotFileName = prefix + screenShotFileName;

			ImageIO.write(bi, "JPG", new File(desktopPath, screenShotFileName));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebcamException e) {
			System.out
					.println("The Webcam is in use, will try again at next interval. Current time: "
							+ now.toString());
			e.printStackTrace();
		}

	}// take webcamPhoto

}
