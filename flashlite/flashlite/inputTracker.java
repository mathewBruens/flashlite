package flashlite;
import org.jnativehook.*;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class inputTracker  extends Thread implements NativeKeyListener, NativeMouseInputListener{
	
	public inputTracker(){
	}

	public void run(){

		
	}
	
	 public void nativeKeyPressed(NativeKeyEvent e) {
         System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

         if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
                 GlobalScreen.unregisterNativeHook();
         }
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		 System.out.println("Mosue Moved: " + e.getX() + ", " + e.getY());
		
	}
	
}

/*

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerExample implements NativeKeyListener {
        public void nativeKeyPressed(NativeKeyEvent e) {
                System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

                if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
                        GlobalScreen.unregisterNativeHook();
                }
        }

        public void nativeKeyReleased(NativeKeyEvent e) {
                System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        }

        public void nativeKeyTyped(NativeKeyEvent e) {
                System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
        }

        public static void main(String[] args) {
                try {
                        GlobalScreen.registerNativeHook();
                }
                catch (NativeHookException ex) {
                        System.err.println("There was a problem registering the native hook.");
                        System.err.println(ex.getMessage());

                        System.exit(1);
                }

                //Construct the example object and initialze native hook.
                GlobalScreen.getInstance().addNativeKeyListener(new GlobalKeyListenerExample());
        }
}
Global Mouse Listener
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListenerExample implements NativeMouseInputListener {
        public void nativeMouseClicked(NativeMouseEvent e) {
                System.out.println("Mosue Clicked: " + e.getClickCount());
        }

        public void nativeMousePressed(NativeMouseEvent e) {
                System.out.println("Mosue Pressed: " + e.getButton());
        }

        public void nativeMouseReleased(NativeMouseEvent e) {
                System.out.println("Mosue Released: " + e.getButton());
        }

        public void nativeMouseMoved(NativeMouseEvent e) {
                System.out.println("Mosue Moved: " + e.getX() + ", " + e.getY());
        }

        public void nativeMouseDragged(NativeMouseEvent e) {
                System.out.println("Mosue Dragged: " + e.getX() + ", " + e.getY());
        }

        public static void main(String[] args) {
                try {
                        GlobalScreen.registerNativeHook();
                }
                catch (NativeHookException ex) {
                        System.err.println("There was a problem registering the native hook.");
                        System.err.println(ex.getMessage());

                        System.exit(1);
                }

                //Construct the example object.
                GlobalMouseListenerExample example = new GlobalMouseListenerExample();

                //Add the appropriate listeners for the example object.
                GlobalScreen.getInstance().addNativeMouseListener(example);
                GlobalScreen.getInstance().addNativeMouseMotionListener(example);
        }
}
Global Mouse Wheel Listener
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class GlobalMouseWheelListenerExample implements NativeMouseWheelListener {
        public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
                System.out.println("Mosue Wheel Moved: " + e.getWheelRotation());
        }

        public static void main(String[] args) {
                try {
                        GlobalScreen.registerNativeHook();
                }
                catch (NativeHookException ex) {
                        System.err.println("There was a problem registering the native hook.");
                        System.err.println(ex.getMessage());
                        ex.printStackTrace();

                        System.exit(1);
                }

                //Construct the example object and initialze native hook.
                GlobalScreen.getInstance().addNativeMouseWheelListener(new GlobalMouseWheelListenerExample());
        }
}

*/