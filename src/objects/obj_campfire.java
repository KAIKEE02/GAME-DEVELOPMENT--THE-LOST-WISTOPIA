package objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class obj_campfire extends superObj {

	public obj_campfire() {
		name = "camopfire";
		collision = false;
		
		try (InputStream is = getClass().getResourceAsStream("/objects/campfire.png")) {
		    BufferedImage runningimg = ImageIO.read(is);
		    

		    for (int j = 0; j < animatedfire.length; j++) {
		        animatedfire[j] = runningimg.getSubimage(j * 640, 0, 640, 640);
//		        System.out.println("Frame " + j + " loaded: " + (animated[j] != null));
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Error loading or processing the image.");
		}
	}
}

