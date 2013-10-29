package flashlite;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;



public class WebcamPhoto {
private static JMyron myron;
		
	public WebcamPhoto() {
		
	}
	
	static void takeWebCamPhoto(){
		myron = new JMyron();
		myron.start(320, 240);
		int [] img = myron.cameraImage();
		myron.stop();
		
		for(int x=0;x<img.length;x++){
			System.out.print(img[x]);
			
		}//RGBA
		
		System.out.print("\n"+img.length);
		//myron.findGlobs(0);
		
		//myron.update();
		//int[] img = myron.image();
		
		//BufferedImage bi = new BufferedImage();


	        ByteBuffer byteBuffer = ByteBuffer.allocate(img.length * 4);        
	        IntBuffer intBuffer = byteBuffer.asIntBuffer();
	        intBuffer.put(img);

	        byte[] bytes = byteBuffer.array();

	        
	        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	        Iterator<?> readers = ImageIO.getImageReadersByFormatName("png");
	 
	        //ImageIO is a class containing static methods for locating ImageReaders
	        //and ImageWriters, and performing simple encoding and decoding. 
	 
	        ImageReader reader = (ImageReader) readers.next();
	        Object source = bis; 
	        ImageInputStream iis=null;
			try {
				iis = ImageIO.createImageInputStream(source);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			reader.setInput(iis, true);
	        ImageReadParam param = reader.getDefaultReadParam();
	 
	        Image image =null;
			try {
				image = reader.read(0, param);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //got an image file
	 
	        BufferedImage bufferedImage = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
	        //bufferedImage is the RenderedImage to be written
	 
	        Graphics2D g2 = bufferedImage.createGraphics();
	        g2.drawImage(image, null, null);
	 
	        File imageFile = new File("C:\\newrose2.jpg");
	        try {
				ImageIO.write(bufferedImage, "png", imageFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	        System.out.println(imageFile.getPath());
	    }
	
	        
	        
	        
		
		
	}


