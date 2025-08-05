package objects;

import java.io.IOException;

import static Main.MAINGAME.TILES_SIZE;

import javax.imageio.ImageIO;

import Gamestates.Play;

public class obj_Heart extends superObj {
	
	public obj_Heart( ) {
		
		name = "heart";
		
		
		try {
			img[5] = ImageIO.read(getClass().getResource("/objects/heart_fulls.png"));
			img[6] = ImageIO.read(getClass().getResource("/objects/heart_halfs.png"));
			img[7] = ImageIO.read(getClass().getResource("/objects/heart_emptys.png"));  
			img[12] = ImageIO.read(getClass().getResource("/objects/heart_fulls.png"));
			
		
			
			
			img[5] = uTool.scaleImage(img[5],TILES_SIZE*2, TILES_SIZE*2);
			img[6] = uTool.scaleImage(img[6], TILES_SIZE*2, TILES_SIZE*2);
			img[7] = uTool.scaleImage(img[7], TILES_SIZE*2, TILES_SIZE*2);
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
