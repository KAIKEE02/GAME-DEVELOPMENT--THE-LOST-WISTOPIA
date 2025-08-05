package objects;

import static Main.MAINGAME.TILES_SIZE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;

import Entities.Player;
import Gamestates.Play;
import Main.UtilityTool;

public class superObj {
	public UtilityTool uTool = new UtilityTool();
	public BufferedImage img[];
	public BufferedImage[] animated, animatedkey, animatedfire;
	public String name;
	public  boolean collision; 	
	public int worldx, worldy;
	public Player player;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	private int aniTick, aniIndex1 = 0, aniIndex2, aniIndex3, aniSpeed = 15;
	
	
	public superObj() {
		animated = new BufferedImage[4];
		animatedfire = new BufferedImage[10];
		animatedkey = new BufferedImage[27];
		
		img = new BufferedImage[30];
		 
		
	}
	public void updateAniTick() {
		
		aniTick++;
		if (aniTick >= aniSpeed) {
			
			aniTick = 0;
			
			
				aniIndex1++;
				
			
			aniIndex2++;
			aniIndex3++;
			if (aniIndex2 >= animated.length) {
				
				aniIndex2 = 0;
				
		
			}
						
			if (aniIndex1 >= animatedfire.length) {
				
				aniIndex1 = 0;
				
		
			}
			
			if (aniIndex3 >= animatedkey.length) {
				
				aniIndex3 = 0;
				
		
			}
		}
		
	}
	public void update() {
	    // Call to update animation tick
	    updateAniTick();
	}

	public void draw(Graphics2D g2, Play play, Player player) {
	    this.player = player;

	    int screenX = (int)Math.floor(worldx - player.getWorldX() + player.getScreenX());
	    int screenY = (int)Math.floor(worldy - player.getWorldY() + player.getScreenY());

	    if (worldx + TILES_SIZE + 78 > player.getWorldX() - player.getScreenX() &&
	        worldx - TILES_SIZE - 78 < player.getWorldX() + player.getScreenX() &&
	        worldy + TILES_SIZE + 120 > player.getWorldY() - player.getScreenY() &&
	        worldy - TILES_SIZE - 120 < player.getWorldY() + player.getScreenY()) {

//	        int keysize = TILES_SIZE / 2;

	       // g2.drawImage(img[0], screenX + 5, screenY + 10, keysize, keysize, null);

	        g2.drawImage(img[1], screenX, screenY, TILES_SIZE, TILES_SIZE, null);

	        int chestSize = TILES_SIZE - 10;
	        g2.drawImage(img[2], screenX + 5, screenY, chestSize, chestSize, null);
	        g2.drawImage(img[11], screenX + 5, screenY, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(img[12], screenX + 5, screenY, TILES_SIZE + 10, TILES_SIZE + 10, null);

	      //  int houseSize = TILES_SIZE * 4;
	        g2.drawImage(img[3], screenX , screenY, 0, 0, null);
	        g2.drawImage(img[4], screenX , screenY, 0, 0, null);
	        
	        g2.drawImage(animated[aniIndex2 ], screenX + 5, screenY, chestSize, chestSize, null);
	        g2.drawImage(animatedfire[aniIndex1], screenX -20, screenY -25, TILES_SIZE * 2, TILES_SIZE *2, null);
	        g2.drawImage(animatedkey[aniIndex3], screenX + 5, screenY, chestSize, chestSize, null);
//	        g2.setColor(Color.RED);  // Set color to easily identify the collision box
//		    g2.drawRect((int) screenX    , (int) screenY  , solidArea.width, solidArea.height);
		       
	        
	    }
	}

}
