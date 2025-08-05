package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class obj_Wistg extends superObj {

	public obj_Wistg() {
		name = "wistg";
		collision = false;
		
		try {
			img[4] = ImageIO.read(getClass().getResource("/objects/house.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
