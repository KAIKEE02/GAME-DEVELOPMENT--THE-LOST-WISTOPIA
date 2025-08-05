package objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class obj_key extends superObj {

	public obj_key() {
		name = "key";
		
		try (InputStream is = getClass().getResourceAsStream("/objects/anikey.png")) {
		    BufferedImage runningimg = ImageIO.read(is);
		    

		    for (int j = 0; j < animatedkey.length; j++) {
		        animatedkey[j] = runningimg.getSubimage(j * 21, 0, 21, 47);
//		        System.out.println("Frame " + j + " loaded: " + (animated[j] != null));
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Error loading or processing the image.");
		}
		collision = false;
	}
}
