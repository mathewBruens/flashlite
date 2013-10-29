package flashlite;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

//import JNativeHook




public class AppTray {
	SystemTray tray;
	int NEW_WIDTH =16;
	int NEW_HEIGHT = 16;
	GraphicsEnvironment ge;
	GraphicsDevice[] screens;
	 static GraphicsDevice currentState = 
			 GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	//InputStream stream = AppTray.class.getResourceAsStream("Flashlight-32x32.png");
	//Image image1 = null;

	//Image image1 =  ImageIO.read(stream);


	
	
	public AppTray() {
		if(!SystemTray.isSupported())
			return;
		
		Image icon= Toolkit.getDefaultToolkit().getImage(
				AppTray.class.getResource("/Flashlight-32x32.png"));
		//URL iconURL = AppTray.class.getClass().getResource("Flashlight-32x32.png");
		//URL iconURL =null;
		//iconURL = AppTray.class.getResource("Flashlight-32x32.png");
		//Image icon= Toolkit.getDefaultToolkit().createImage(iconURL);
		Image scaledIcon = icon.getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ; 
		
		
		
		
		
		tray = SystemTray.getSystemTray();
		//add finally to send a message to server that you quit the program
		 ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 screens = ge.getScreenDevices();
		//Robot r = new Robot()
		//currentState = screens[0];
		
		final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =  new TrayIcon(scaledIcon, null, popup);
        final SystemTray tray = SystemTray.getSystemTray();
       
       // MenuItem aboutItem = new MenuItem("About");
       // CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
       // CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        //Menu displayMenu = new Menu("Display");
        MenuItem exitItem = new MenuItem("Exit");
       
        Menu monitorCapture = new Menu("Capture monitor:");
        
        
        
        ArrayList<checkboxInstance> list= new ArrayList<checkboxInstance>();
        
        for (GraphicsDevice screen : screens) {
        	
        	//System.out.println(screen.toString());
        	
        	list.add(new checkboxInstance(screen, new CheckboxMenuItem(screen.toString())));
        	
  
        //monitorCapture.add(temp);	
        
        }
        
        for(checkboxInstance i : list){
        	monitorCapture.add(i.item);
        	
        }

        popup.addSeparator();
     
        popup.add(monitorCapture);
        popup.addSeparator();
      
     
        
       //popup.add(infoItem);

        popup.add(exitItem);
       
        trayIcon.setPopupMenu(popup);
        
        
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
		
		
	}
	
	private class checkboxInstance
	{
		GraphicsDevice screen;
		CheckboxMenuItem item;
		
		checkboxInstance(GraphicsDevice screen1, CheckboxMenuItem item){
			screen = screen1;
			this.item = item;
			
			item.addItemListener(new ItemListener(){

    			public void itemStateChanged(ItemEvent e) {
    		
    				
    				currentState = screen;
    				
    				System.out.println(currentState.toString());
    			}
            	
            });
		}
		
	}//end checkboxInstance
	

}
