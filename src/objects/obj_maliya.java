package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class obj_maliya extends superObj {

	public obj_maliya() {
		name = "maliya";
		collision = false;
		
		try {
			img[3] = ImageIO.read(getClass().getResource("/objects/house.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
