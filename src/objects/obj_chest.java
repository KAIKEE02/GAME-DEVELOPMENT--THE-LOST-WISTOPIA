package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class obj_chest extends superObj {

	public obj_chest() {
		name = "chest";
		collision = true;
		
		try {
			img[2] = ImageIO.read(getClass().getResource("/objects/chest.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
