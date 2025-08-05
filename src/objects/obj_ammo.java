package objects;

import java.io.IOException;

import static Main.MAINGAME.TILES_SIZE;

import javax.imageio.ImageIO;

import Gamestates.Play;

public class obj_ammo extends superObj {
	
	public obj_ammo( ) {
		
		name = "ammo";
		
		
		try {
			img[13] = ImageIO.read(getClass().getResource("/objects/RIFFLEAMMO.png"));
			img[14] = ImageIO.read(getClass().getResource("/ammoBar.png"));
			  
			
			img[13] = uTool.scaleImage(img[13],TILES_SIZE, TILES_SIZE);
			img[14] = uTool.scaleImage(img[14],TILES_SIZE, TILES_SIZE);
		
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
