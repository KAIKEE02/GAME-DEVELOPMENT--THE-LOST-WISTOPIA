package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import Gamestates.Play;
import Main.CollisionCheck;

public class obj_Door extends superObj {
	

	public obj_Door() {
	    name = "door";
	    collision = true;

	    try {
	        img[1] = ImageIO.read(getClass().getResource("/objects/door.png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // Update solidArea to match the door image size
	    solidArea.width = 48;  // Assuming door image size matches tile size
	    solidArea.height = 48; // Same as above
	}

}
