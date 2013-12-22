import java.sql.Timestamp;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class InputTracker implements NativeKeyListener, NativeMouseListener {

	private int numOfMousePress;
	private int numOfKeyPress;
	private final Timestamp start;
	private Timestamp end;
	private long maxKeyPressInterval; // millis
	private long maxMousePressInterval;
	private Timestamp lastKeyPress;
	private Timestamp lastMousePress;
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public InputTracker() {
		numOfMousePress = 0;
		numOfKeyPress = 0;
		start = new Timestamp(System.currentTimeMillis());
		lastKeyPress = start;
		lastMousePress = start;
		maxKeyPressInterval = 0;
		maxMousePressInterval = 0;
		init();
	}

	private void init() {
		try {
			if (GlobalScreen.isNativeHookRegistered()) {
				GlobalScreen.unregisterNativeHook();
			}
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.getInstance().addNativeKeyListener(this);
		GlobalScreen.getInstance().addNativeMouseListener(this);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		long timeBetweenKeysPressed = now.getTime() - lastKeyPress.getTime();

		if (timeBetweenKeysPressed >= maxKeyPressInterval) {
			maxKeyPressInterval = timeBetweenKeysPressed;
		}
		lastKeyPress = now;
		numOfKeyPress++;

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		long timeBetweenMouseMov = now.getTime() - lastMousePress.getTime();

		if (timeBetweenMouseMov >= maxMousePressInterval) {
			maxMousePressInterval = timeBetweenMouseMov;
		}

		lastMousePress = now;
		numOfMousePress++;

	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
	}

	public void setEndTime() {
		end = new Timestamp(System.currentTimeMillis());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InputTracker [numOfMousePress=");
		builder.append(numOfMousePress);
		builder.append(", numOfKeyPress=");
		builder.append(numOfKeyPress);
		builder.append(", start=");
		builder.append(start);
		builder.append(", end=");
		builder.append(end);
		builder.append(", maxKeyPressInterval=");
		builder.append(maxKeyPressInterval);
		builder.append(", maxMousePressInterval=");
		builder.append(maxMousePressInterval);
		builder.append(", lastKeyPress=");
		builder.append(lastKeyPress);
		builder.append(", lastMousePress=");
		builder.append(lastMousePress);
		builder.append("]");
		LOGGER.info(builder.toString() + "\n");
		return builder.toString();
	}

	public void unRegister() {
		GlobalScreen.unregisterNativeHook();
		GlobalScreen.getInstance().removeNativeKeyListener(this);
		GlobalScreen.getInstance().removeNativeMouseListener(this);
	}

}
