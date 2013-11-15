//package flashlite;
import java.sql.Timestamp;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class InputTracker implements NativeKeyListener, NativeMouseListener {

	private int numOfMousePress;
	private int numOfKeyPress;
	private final long timeToTrack;

	private final Timestamp start;
	private Timestamp end;
	private long maxKeyPress;
	private long maxMousePress;
	private Timestamp lastKeyPress;
	private Timestamp lastMousePress;

	public InputTracker(long timeToTrack) {
		numOfMousePress = 0;
		numOfKeyPress = 0;
		this.timeToTrack = timeToTrack;
		start = new Timestamp(System.currentTimeMillis());
		lastKeyPress = start;
		lastMousePress = start;
		maxKeyPress = 0;
		maxMousePress = 0;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// System.out.println("Key Pressed: " +
		// NativeKeyEvent.getKeyText(e.getKeyCode()));

		Timestamp now = new Timestamp(System.currentTimeMillis());

		long timeBetweenKeysPressed = now.getTime() - lastKeyPress.getTime();

		if (timeBetweenKeysPressed >= maxKeyPress) {
			maxKeyPress = timeBetweenKeysPressed;
		}
		lastKeyPress = now;
		numOfKeyPress++;

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		long timeBetweenMouseMov = now.getTime() - lastMousePress.getTime();

		if (timeBetweenMouseMov >= maxMousePress) {
			maxMousePress = timeBetweenMouseMov;
		}

		lastMousePress = now;
		numOfMousePress++;

	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InputTracker [numOfMousePress=");
		builder.append(numOfMousePress);
		builder.append(", numOfKeyPress=");
		builder.append(numOfKeyPress);
		builder.append(", timeToTrack=");
		builder.append(timeToTrack);
		builder.append(", startTime=");
		builder.append(start);
		builder.append(", end=");
		builder.append(end);
		builder.append(", maxKeyPress=");
		builder.append(maxKeyPress);
		builder.append(", maxMousePress=");
		builder.append(maxMousePress);
		builder.append(", lastKeyPress=");
		builder.append(lastKeyPress);
		builder.append(", lastMousePress=");
		builder.append(lastMousePress);
		builder.append("]");
		return builder.toString();
	}

	// @Override
	// public void run() {
	// Timer timer = new Timer();
	// timer.schedule(new TimerTask(){
	//
	// @Override
	// public void run() {
	// System.out.println(toString());
	//
	// }
	//
	// }, timeToTrack);
	//
	// }

}
