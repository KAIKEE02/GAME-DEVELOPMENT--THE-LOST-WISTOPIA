package objects;

import java.io.IOException;

import static Main.MAINGAME.TILES_SIZE;

import javax.imageio.ImageIO;

import Gamestates.Play;

public class obj_BlueHeart extends superObj {
	
	public obj_BlueHeart( ) {
		
		name = "blue_heart";
		
		
		try {
			img[8] = ImageIO.read(getClass().getResource("/objects/full_shield.png"));
			img[9] = ImageIO.read(getClass().getResource("/objects/half_shield.png"));
			img[11] = ImageIO.read(getClass().getResource("/objects/full_shield.png"));
			img[10] = ImageIO.read(getClass().getResource("/objects/empty_shield.png"));  
			
			img[8] = uTool.scaleImage(img[8],TILES_SIZE, TILES_SIZE);
			img[9] = uTool.scaleImage(img[9], TILES_SIZE, TILES_SIZE);
			img[10] = uTool.scaleImage(img[10], TILES_SIZE, TILES_SIZE);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
