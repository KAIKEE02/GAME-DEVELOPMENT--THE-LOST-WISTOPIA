package NPCs;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Entities.Entity;
import Gamestates.Play;

public class npc_ManGods  extends superNpc{
	 public npc_ManGods() {
		 name = "godiks";
		 collision = false;
		
	       
	        
	        
	        loadAnimations();  
	    }

	    private void loadAnimations() {
	        // Adjust the size based on your needs
	        
	        try (InputStream is = getClass().getResourceAsStream("/npc/npc_god.png")) {
			    BufferedImage runningimg = ImageIO.read(is);
			    

			    for (int j = 0; j < animated.length; j++) {
			        animated[j] = runningimg.getSubimage(j * 1024, 0, 1024, 720);
//			        System.out.println("Frame " + j + " loaded: " + (animated[j] != null));
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			    System.out.println("Error loading or processing the image.");
			    
			    
			}
	        
	      
	        
	    }

}
