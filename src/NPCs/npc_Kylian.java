package NPCs;

import static Main.MAINGAME.TILES_SIZE;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Entities.Entity;
import Gamestates.Play;

public class npc_Kylian  extends superNpc{
	 public npc_Kylian() {
		 name = "Kylian";
		 collision = true;
		 
			solidArea.x = 0;
			solidArea.y = 0;
			
			solidArea.width = 50;
			solidArea.height = 50;

	        
	        
	        loadAnimations();  
	    }

	    private void loadAnimations() {
	        // Adjust the size based on your needs
	        
	        try (InputStream is = getClass().getResourceAsStream("/npc/Kylian.png")) {
			    BufferedImage runningimg = ImageIO.read(is);
			    

			    for (int j = 0; j < animated3.length; j++) {
			        animated3[j] = runningimg.getSubimage(j * 44, 0, 44, 45);
//			        System.out.println("Frame " + j + " loaded: " + (animated[j] != null));
			        //animatedguard[j] = uTool.scaleImage(animatedguard[j],TILES_SIZE, TILES_SIZE);
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			    System.out.println("Error loading or processing the image.");
			    
			    
			}
	        
	      
	        
	    }

}
