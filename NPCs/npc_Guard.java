package NPCs;

import static Main.MAINGAME.TILES_SIZE;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Entities.Entity;
import Gamestates.Play;

public class npc_Guard  extends superNpc{
	 public npc_Guard() {
		 name = "guards";
		 collision = true;
		

	        
	        
	        loadAnimations();  
	    }

	    private void loadAnimations() {
	        // Adjust the size based on your needs
	        
	        try (InputStream is = getClass().getResourceAsStream("/npc/idle_knights.png")) {
			    BufferedImage runningimg = ImageIO.read(is);
			    

			    for (int j = 0; j < animatedguard.length; j++) {
			        animatedguard[j] = runningimg.getSubimage(j * 32, 0, 32, 32);
//			        System.out.println("Frame " + j + " loaded: " + (animated[j] != null));
			        //animatedguard[j] = uTool.scaleImage(animatedguard[j],TILES_SIZE, TILES_SIZE);
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			    System.out.println("Error loading or processing the image.");
			    
			    
			}
	        
	      
	        
	    }

}
