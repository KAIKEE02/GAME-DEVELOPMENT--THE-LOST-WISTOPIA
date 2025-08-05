package NPCs;

import static Main.MAINGAME.TILES_SIZE;
import static utilz.Constants.PlayerConstants.GetNpcSpriteAmount;


import static utilz.Constants.PlayerConstants.npc1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;

import Entities.Player;
import Gamestates.Play;
import Main.UtilityTool;

public class superNpc {
	public UtilityTool uTool = new UtilityTool();
	public BufferedImage img[];
	public BufferedImage[] animated,animated3, animatedguard;
	public String name;
	public  boolean collision; 	
	public int worldx, worldy;
	public Player player;
	public Rectangle solidArea;
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	private int aniTick, aniIndex, aniIndex1,aniIndex2, aniSpeed = 14;
	
	
	public superNpc() {
		animated = new BufferedImage[5];
		animated3 = new BufferedImage[3];
		animatedguard = new BufferedImage[4];
		img = new BufferedImage[45];
		solidArea = new Rectangle(0,20,100,100);
		
		
		
		 
		
	}
	public void updateAniTick() {
		
		aniTick++;
		if (aniTick >= aniSpeed) {
			
			aniTick = 0;
			aniIndex++;
			aniIndex1++;
			aniIndex2++;
			if (aniIndex >= animated.length) {
				
				aniIndex = 0;

		
			}

			if (aniIndex1 >= animatedguard.length) {
				
				aniIndex1 = 0;
	
		
			}
            if (aniIndex2 >= animated3.length) {
				
				aniIndex2 = 0;
	
		
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
	  
//	    g2.setColor(Color.RED);  // Set color to easily identify the collision box
//	    g2.drawRect((int) screenX, (int) screenY, solidArea.width, solidArea.height);
	       
	    g2.setColor(Color.RED);  // Set color to easily identify the collision box
      //  g2.drawRect((int) screenX   , (int) screenY  , solidArea.width, solidArea.height);

	    if (worldx + TILES_SIZE + 78 > player.getWorldX() - player.getScreenX() &&
	        worldx - TILES_SIZE - 78 < player.getWorldX() + player.getScreenX() &&
	        worldy + TILES_SIZE + 120 > player.getWorldY() - player.getScreenY() &&
	        worldy - TILES_SIZE - 120 < player.getWorldY() + player.getScreenY()) {

	       
	    	g2.drawImage(animated3[aniIndex2], screenX - 20, screenY - 15, TILES_SIZE *2, TILES_SIZE + 26  , null);
	    	g2.drawImage(animatedguard[aniIndex1], screenX , screenY - 15, TILES_SIZE, TILES_SIZE  , null);
	        g2.drawImage(animated[aniIndex], screenX , screenY - 10, TILES_SIZE * 2, TILES_SIZE * 2, null);
	        g2.drawImage(img[1], screenX - 10  , screenY + 8, TILES_SIZE * 2 + 20, TILES_SIZE * 2 + 20, null);
	      
	         
	    }
	}

}
