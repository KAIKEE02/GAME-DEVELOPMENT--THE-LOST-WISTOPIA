package ui;

import java.awt.Color;


import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.classfile.AnnotationValue.OfAnnotation;

import javax.imageio.ImageIO;

import Gamestates.Play;
import Main.UtilityTool;
import objects.obj_BlueHeart;
import objects.obj_Heart;
import objects.obj_ammo;
import objects.obj_key;
import objects.obj_soulOrb;
import objects.superObj;

import static Main.MAINGAME.TILES_SIZE;

public class UI {
	BufferedImage heart_full, heart_half, heart_empty;
	BufferedImage heart_full_blue, heart_half_blue, heart_empty_blue;
	Play screen;
	Font mincraft;
	UtilityTool uTool;
	BufferedImage keyimg,orbimg,ammoimg,ammobar,panel, divider, panel2,OBTAIN, iconwep;
	public String message, messagemap;
	public boolean messageOn = false , OBT = false, messagemapOn = false;
	private int messtime = 0;
	Graphics2D g2;
	
	
	public UI(Play screen) {
		uTool = new UtilityTool();
		this.screen = screen;
		
		obj_key key = new obj_key();
		obj_soulOrb orb = new obj_soulOrb();
		obj_ammo ammo = new obj_ammo();
		keyimg = key.animatedkey[5];
		orbimg = orb.animated[0];
		ammoimg = ammo.img[13];
		ammobar = ammo.img[14];
		
		superObj blueheart = new obj_BlueHeart();
		heart_full_blue = blueheart.img[8];
		heart_half_blue = blueheart.img[9];
		heart_empty_blue = blueheart.img[10];
		superObj heart = new obj_Heart();
		heart_full = heart.img[5];
		heart_half = heart.img[6];
		heart_empty = heart.img[7];
		
		//Importing a customize font to match game design
		InputStream is = getClass().getResourceAsStream("/Font/Minecraft.ttf");
		try {
			mincraft = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		importui();
		// create hud object
		
		
	}
// this function handles if the messeges is about to be popped
	public void showMessage(String text) {
		message = text;
		messageOn = true;
		

	}
	public void showMapName(String text) {
		messagemap= text;
		messagemapOn = true;
		
		
	}
	public void obtains() {
	
		OBT = true;
		
	}

	public void draw(Graphics2D g2) {
		//rendering the ui, key indicator, orb indicator, and the messegaes
		this.g2 = g2; 
		 	g2.setFont(mincraft);
		 	g2.setFont(g2.getFont().deriveFont(40f));
		 	g2.setColor(Color.WHITE);
	        g2.drawImage(keyimg, TILES_SIZE/2 , TILES_SIZE/2 + 60, TILES_SIZE, TILES_SIZE +10, null);
		 	g2.drawString(" x " + screen.player.hashKey , 70, 140);
		 	g2.drawImage(orbimg, TILES_SIZE/2, TILES_SIZE/2 + 120, TILES_SIZE, TILES_SIZE  , null);
		 	g2.drawString(" x " + screen.player.hashorb , 70, 180);
		 	
		if(screen.player.gun && screen.player.obtain) {
		 	drawAmmoUI();
		} 
		 	drawplayerLife();
		 	drawplayerdef();
		 	
		 	
		 	
		 	if (messageOn  && screen.player.diaoulgeentered == false) {
				g2.setFont(g2.getFont().deriveFont(20f));
				g2.drawImage(panel, 525 ,570 , 300,60, null);
				g2.drawString(message, 555, 600);
				messtime++;
				if(messtime == 160 ) {
					messageOn = false;
					messtime = 0;
				}
				
				
			}
		 	if (messagemapOn ) {
				g2.setFont(g2.getFont().deriveFont(18f));
				g2.setColor(Color.BLACK);
				g2.drawImage(panel2, 500 ,20 , 300, TILES_SIZE * 2, null);
				g2.drawString(messagemap, 560, 80);
				messtime++;
				if(messtime == 420 ) {
					messagemapOn = false;
					messtime = 0;
				}
		 	}
		 	if(OBT) {
		 		g2.drawImage(OBTAIN, 400, 150 , 400, 400, null);
		 		messtime++;
		 		if(messtime == 360 ) {
					OBT = false;
					messtime = 0;
				}
		 	}
		 	
		
	}
	private void drawAmmoUI() {
	    int x = TILES_SIZE / 2 + 1000;  // X position for ammo icons (same as key and orb)
	    int y = TILES_SIZE / 2 ;  // Adjust Y position for ammo below the orb

	    // Based on bulletcounter, display the correct amount of ammo images
	    g2.drawImage(iconwep, x - 70, y -5, TILES_SIZE + 20, TILES_SIZE + 20 , null);
	    g2.drawImage(ammobar, x -5, y - 2, TILES_SIZE * 4 + 8, TILES_SIZE + 10 , null);
	    if (screen.player.bulletcounter == 40) {
	        g2.drawImage(ammoimg, x, y, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(ammoimg, x + TILES_SIZE, y, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(ammoimg, x + TILES_SIZE * 2, y, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(ammoimg, x + TILES_SIZE * 3, y, TILES_SIZE, TILES_SIZE, null);
	    }
	    else if (screen.player.bulletcounter == 30) {
	        g2.drawImage(ammoimg, x, y, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(ammoimg, x + TILES_SIZE, y, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(ammoimg, x + TILES_SIZE * 2, y, TILES_SIZE, TILES_SIZE, null);
	    } else if (screen.player.bulletcounter == 20) {
	        g2.drawImage(ammoimg, x, y, TILES_SIZE, TILES_SIZE, null);
	        g2.drawImage(ammoimg, x + TILES_SIZE, y, TILES_SIZE, TILES_SIZE, null);
	    } else if (screen.player.bulletcounter == 10) {
	        g2.drawImage(ammoimg, x, y, TILES_SIZE, TILES_SIZE, null);
	    } else {
	        // No ammo, don't draw any ammo image
	    }
	}

	public void drawplayerLife(){
		int x = TILES_SIZE/2 - 20;
		int y = TILES_SIZE/2 - 40;
		int i = 0;
		
		while (i < screen.player.maxLife/2) {
			g2.drawImage(heart_empty, x, y, null);
			i++;
			x += TILES_SIZE;
			
		}
		// reset
		x = TILES_SIZE/2 - 20;
		y = TILES_SIZE/2 - 40;
		i = 0;
		// draw current life 
		while(i < screen.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if (i < screen.player.life){
				g2.drawImage(heart_full, x, y,null);
			}
			i++;
			x += TILES_SIZE;
			
		}
	}
		
	public void drawplayerdef() {
	    int x = TILES_SIZE / 2 ; // Starting x position for defense hearts
	    int y = TILES_SIZE / 2 + 30; // Same y position as the life hearts
	    int i = 0;

	    // Draw empty defense hearts
	    for (int j = 0; j < screen.player.maxDef / 2; j++) {
	        g2.drawImage(heart_empty_blue, x, y, null);
	        x += TILES_SIZE; // Move x for the next heart
	    }

	    // Reset x position for the defense hearts
	    x = TILES_SIZE / 2 ; // Reset x to the starting position
	    i = 0;

	    // Draw current defense hearts
	    while (i < screen.player.defense) {
	        g2.drawImage(heart_half_blue, x, y, null);
	        i++;
	        if (i < screen.player.defense) {
	            g2.drawImage(heart_full_blue, x, y, null);
	        }
	        i++;
	        x += TILES_SIZE; // Move x for the next defense heart
	    }
	}


	
	public void importui() {
		InputStream pnl = getClass().getResourceAsStream("/RectangleBox_96x96.png");
		InputStream brdr = getClass().getResourceAsStream("/YOUOBTAIN.png");
		InputStream dvdr = getClass().getResourceAsStream("/divd.png");
		InputStream  pln=  getClass().getResourceAsStream("/panel3.png");
		InputStream  WPN=  getClass().getResourceAsStream("/WEAPON_ICON.png");
				
		
		 try {
				panel = ImageIO.read(pnl);
				uTool.scaleImage(panel, TILES_SIZE, TILES_SIZE);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		 try {
				OBTAIN = ImageIO.read(brdr);
				uTool.scaleImage(OBTAIN, TILES_SIZE, TILES_SIZE);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		 try {
				divider = ImageIO.read(dvdr);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		 try {
				panel2 = ImageIO.read(pln);
				uTool.scaleImage(panel2, TILES_SIZE, TILES_SIZE);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		 try {
				iconwep = ImageIO.read(WPN);
				uTool.scaleImage(iconwep, TILES_SIZE, TILES_SIZE);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
		
	}

}
