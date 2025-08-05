package objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class obj_soulOrb extends superObj {

	public obj_soulOrb() {
		name = "soulOrb";
		collision = false;
		
		try (InputStream is = getClass().getResourceAsStream("/objects/soulOrb.png")) {
		    BufferedImage runningimg = ImageIO.read(is);
		    

		    for (int j = 0; j < animated.length; j++) {
		        animated[j] = runningimg.getSubimage(j * 32, 0, 32, 32);
//		        System.out.println("Frame " + j + " loaded: " + (animated[j] != null));
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Error loading or processing the image.");
		}
	}
}

