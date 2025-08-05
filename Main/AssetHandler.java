package Main;

import Gamestates.Play;
import NPCs.npc_Guard;
import NPCs.npc_Kylian;
import NPCs.npc_ManGods;
import NPCs.shadow;
import objects.obj_BlueHeart;
import objects.obj_Door;
import objects.obj_Wistg;
import objects.obj_campfire;
import objects.obj_chest;

import objects.obj_key;
import objects.obj_maliya;
import objects.obj_soulOrb;

import static Main.MAINGAME.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Enemies.Superior_orc;
import Enemies.enemy_orc;




public class AssetHandler {

	Play play;
	public AssetHandler(Play play) {
		this.play = play;
	}
	
	public void setObject()
	{
		play.obj[0] = new obj_key();
		play.obj[0].worldx = 111 * TILES_SIZE;
		play.obj[0].worldy = 29 * TILES_SIZE;
		
		play.obj[1] = new obj_key();
		play.obj[1].worldx = 31 * TILES_SIZE;
		play.obj[1].worldy = 45 * TILES_SIZE;
		
		play.obj[4] = new obj_Door(); 
		play.obj[4].worldx = 19 * TILES_SIZE;
		play.obj[4].worldy = 12 * TILES_SIZE;
		
		
		play.obj[6] = new obj_Door(); 
		play.obj[6].worldx = 31 * TILES_SIZE;
		play.obj[6].worldy = 42 * TILES_SIZE;
		
		
		play.obj[7] = new obj_soulOrb(); 
		play.obj[7].worldx = 37 * TILES_SIZE;
		play.obj[7].worldy = 29 * TILES_SIZE;
		
		
		play.obj[3] = new obj_chest(); 
		play.obj[3].worldx = 19 * TILES_SIZE;
		play.obj[3].worldy = 8 * TILES_SIZE;
		
		play.obj[8] = new obj_soulOrb();
		play.obj[8].worldx = 29 * TILES_SIZE;
		play.obj[8].worldy = 45 * TILES_SIZE;
		
		play.obj[9] = new obj_soulOrb(); 
		play.obj[9].worldx = 101 * TILES_SIZE;
		play.obj[9].worldy = 38 * TILES_SIZE;
		
		play.obj[10] = new obj_soulOrb(); 
		play.obj[10].worldx = 103 * TILES_SIZE;
		play.obj[10].worldy = 49 * TILES_SIZE;
		
		play.obj[11] = new obj_soulOrb(); 
		play.obj[11].worldx = 88 * TILES_SIZE;
		play.obj[11].worldy = 38 * TILES_SIZE;
		
		play.obj[12] = new obj_soulOrb(); 
		play.obj[12].worldx = 97 * TILES_SIZE;
		play.obj[12].worldy = 25 * TILES_SIZE;
		
		play.obj[13] = new obj_soulOrb(); 
		play.obj[13].worldx = 96 * TILES_SIZE;
		play.obj[13].worldy = 18 * TILES_SIZE;
		
		play.obj[14] = new obj_soulOrb(); 
		play.obj[14].worldx = 98 * TILES_SIZE;
		play.obj[14].worldy = 11 * TILES_SIZE;
		
		play.obj[15] = new obj_soulOrb(); 
		play.obj[15].worldx = 115 * TILES_SIZE;
		play.obj[15].worldy = 23 * TILES_SIZE;
		
		play.obj[16] = new obj_soulOrb(); 
		play.obj[16].worldx = 125 * TILES_SIZE;
		play.obj[16].worldy = 39 * TILES_SIZE;
		
		play.obj[17] = new  obj_maliya();
		play.obj[17].worldx = 71 * TILES_SIZE;
		play.obj[17].worldy = 20 * TILES_SIZE;
		
		play.obj[18] = new  obj_maliya();
		play.obj[18].worldx = 71 * TILES_SIZE;
		play.obj[18].worldy = 21 * TILES_SIZE;
		
		play.obj[19] = new  obj_maliya(); 
		play.obj[19].worldx = 71 * TILES_SIZE;
		play.obj[19].worldy = 22 * TILES_SIZE;
		
		play.obj[20] = new  obj_Wistg();
		play.obj[20].worldx = 48 * TILES_SIZE;
		play.obj[20].worldy = 20 * TILES_SIZE;
		
		play.obj[21] = new  obj_Wistg();
		play.obj[21].worldx = 48 * TILES_SIZE;
		play.obj[21].worldy = 21 * TILES_SIZE;
		
		play.obj[22] = new  obj_Wistg(); 
		play.obj[22].worldx = 48 * TILES_SIZE;
		play.obj[22].worldy = 22 * TILES_SIZE;

		play.obj[23] = new  obj_BlueHeart(); 
		play.obj[23].worldx = 30 * TILES_SIZE;
		play.obj[23].worldy = 45 * TILES_SIZE;
		
		play.obj[25] = new  obj_BlueHeart(); 
		play.obj[25].worldx = 30 * TILES_SIZE;
		play.obj[25].worldy = 39 * TILES_SIZE;
		
		play.obj[24] = new  obj_campfire(); 
		play.obj[24].worldx = 34 * TILES_SIZE;
		play.obj[24].worldy = 16 * TILES_SIZE;
		
		
		play.npc[2] = new  npc_ManGods(); 
		play.npc[2].worldx = 44 * TILES_SIZE;
		play.npc[2].worldy = 8 * TILES_SIZE;
		play.npc[3] = new  shadow(); 
		play.npc[3].worldx = 44 * TILES_SIZE;
		play.npc[3].worldy = 8 * TILES_SIZE;
		
		
		play.npc[4] = new  npc_Guard(); 
		play.npc[4].worldx = 108 * TILES_SIZE;
		play.npc[4].worldy = 86 * TILES_SIZE;
		
		play.npc[5] = new  npc_Guard(); 
		play.npc[5].worldx = 109 * TILES_SIZE;
		play.npc[5].worldy = 86 * TILES_SIZE;
		
		play.npc[6] = new  npc_Kylian(); 
		play.npc[6].worldx = 104 * TILES_SIZE;
		play.npc[6].worldy = 26 * TILES_SIZE;
		
		
		addEnemies();
	
	}
	 // Add enemies to the play class
    public void addEnemies() {
        BufferedImage orcImage = loadOrcImage(); // Load the orc sprite sheet
        BufferedImage orcSuperior = loadOrcImage2(); // Load the orc sprite sheet

        if (orcImage != null) {
            // Create a CollisionCheck instance for enemy collision detection
            CollisionCheck collisionCheck = new CollisionCheck(play, play.tileManager);
            enemy_orc[] enemies = play.enemies;
            // Add enemies to the array
            enemies[0] = new enemy_orc(91 * TILES_SIZE, 21 * TILES_SIZE, orcImage, collisionCheck, play);
            enemies[1] = new enemy_orc(101 * TILES_SIZE, 75 * TILES_SIZE, orcImage, collisionCheck, play);
            enemies[2] = new enemy_orc(107 * TILES_SIZE, 81 * TILES_SIZE, orcImage, collisionCheck, play);
            enemies[3] = new enemy_orc(111 * TILES_SIZE, 78 * TILES_SIZE, orcImage, collisionCheck, play);
            enemies[4] = new enemy_orc(113 * TILES_SIZE, 38 * TILES_SIZE, orcImage, collisionCheck, play);
            enemies[5] = new enemy_orc(113 * TILES_SIZE, 23 * TILES_SIZE, orcImage, collisionCheck, play);
            //enemies[6] = new enemy_orc(29 * TILES_SIZE, 23 * TILES_SIZE, orcImage, collisionCheck, play);
            // Add more enemies if needed...
        } else {
            System.out.println("Failed to load orc image.");
        }
        if (orcSuperior != null) {
            // Create a CollisionCheck instance for enemy collision detection
            CollisionCheck collisionCheck = new CollisionCheck(play, play.tileManager);
            Superior_orc[] sOrcs = play.superior;
            // Add enemies to the array
            sOrcs[0] = new Superior_orc(91 * TILES_SIZE, 22 * TILES_SIZE, orcSuperior, collisionCheck, play);
            sOrcs[1] = new Superior_orc(102 * TILES_SIZE, 75 * TILES_SIZE, orcSuperior, collisionCheck, play);
            sOrcs[2] = new Superior_orc(108* TILES_SIZE, 81 * TILES_SIZE, orcSuperior, collisionCheck, play);
            sOrcs[3] = new Superior_orc(112 * TILES_SIZE, 78 * TILES_SIZE, orcSuperior, collisionCheck, play);
            sOrcs[4] = new Superior_orc(114 * TILES_SIZE, 38 * TILES_SIZE, orcSuperior, collisionCheck, play);
            sOrcs[5] = new Superior_orc(95 * TILES_SIZE, 44 * TILES_SIZE, orcSuperior, collisionCheck, play);
            // Add more enemies if needed...
        } else {
            System.out.println("Failed to load orc image.");
        }
    }

    // Load the orc enemy sprite sheet
    private BufferedImage loadOrcImage() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("/orc2_walk_full.png")); // Path to your sprite sheet
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private BufferedImage loadOrcImage2() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("/orc3_walk_full.png")); // Path to your sprite sheet
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}