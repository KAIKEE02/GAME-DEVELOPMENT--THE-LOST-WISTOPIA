package NPCs;

import java.io.IOException;

import javax.imageio.ImageIO;

public class shadow extends superNpc {

	public shadow() {
		name = "shadow";
		collision = true;
		
		try {
			img[1] = ImageIO.read(getClass().getResource("/shadow.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
