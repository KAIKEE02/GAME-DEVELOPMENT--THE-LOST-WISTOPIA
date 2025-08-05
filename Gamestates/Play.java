package Gamestates;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

import Enemies.Superior_orc;
import Enemies.enemy_orc;

import static Main.MAINGAME.TILES_SIZE;
import Entities.Entity;
import Entities.Player;
import Entities.Projectile;
import Entities.choppie;
import Main.AssetHandler;
import Main.CollisionCheck;
import Main.DatabaseManager;
import Main.EventHandler;
import Main.MAINGAME;
import Main.Sound;
import NPCs.superNpc;
import Tile.TileManager;
import objects.superObj;
import ui.DialougeContents;
import ui.GameOverOverlay;
import ui.UI;
import ui.dialouge;

public class Play extends state implements Statemethods {
    public boolean gameon = false;
    public Sound music;
    public UI ui;
    public DialougeContents contents;
    public EventHandler eHandler = new EventHandler(this);
    public Sound se;
    public AssetHandler assetr;
    public superObj obj[];
    public superNpc npc[]; 
    private GameOverOverlay gameOverOverlay;
    private dialouge Dialouge;
    public enemy_orc enemies[]; // Array for enemies
    public Superior_orc superior[]; // Array for enemies
    public TileManager tileManager;
    public Player player;
    public choppie choppie;
    public CollisionCheck cChecker;
    public String[] currentDialogue;  // Store the dialogue lines
    public int dialogueIndex = 0;     // Track the current dialogue line
    public boolean check = false;     // Track the current dialogue line
    
    private boolean gameOver;
	public boolean dead = false;
	private boolean dialougeOn;
	private boolean canProceedDialogue = true;  // Flag to control dialogue progression
	public boolean enemydead = false;  // Flag to control dialogue progression
	public int killcount = 0;
	public DatabaseManager dbManager;
	public ArrayList<Projectile> projectiles = new ArrayList<>();

    public Play(MAINGAME game) {
    	
        super(game);
        initClasses();
        setupGame();
        gameMusic();
       
    }
    public void shootProjectile(float x, float y, float directionX, float directionY, float speed) {
        projectiles.add(new Projectile(x, y, directionX, directionY, speed));
        System.out.println("Projectile fired! Starting position: (" + x + ", " + y + ")");
    }

    public void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            p.update();
            if (!p.active) {
                projectiles.remove(i); // Remove inactive projectiles
                i--;
            }
        }
    }

    public void drawProjectiles(Graphics2D g2) {
        for (Projectile p : projectiles) {
            p.draw(g2,this,player);
        }
    }


    public void initClasses() {
        player = new Player(29 * TILES_SIZE + 8, 12 * TILES_SIZE - 7, this);
        
        dbManager = new DatabaseManager(); // Initialize the DatabaseManager
      
       
        this.tileManager = new TileManager(this, player);
        cChecker = new CollisionCheck(this, tileManager);
        assetr = new AssetHandler(this);
        obj = new superObj[50];
        npc = new superNpc[50];
        
        enemies = new enemy_orc[10];
        superior = new Superior_orc[10];
        choppie = new choppie(29 * TILES_SIZE, 14 * TILES_SIZE,cChecker,this, player); 
        
     
        se = new Sound();
        music = new Sound();
        ui = new UI(this);
        contents = new DialougeContents(this);
        gameOverOverlay = new GameOverOverlay(this);
        Dialouge = new dialouge(this);
        

    }
    float directionX = 0.0f;
    float directionY = 0.0f;
    public void fireGun(Player player) {
        float x = player.worldx + 40; // Adjust projectile's starting X position
        float y = player.worldy + 30; // Adjust projectile's starting Y position

        // Define a maximum range for targeting enemies (in world units or tiles)
        float maxRange = 250.0f;  // Adjust this value as needed for your game

        // Find the nearest enemy
        Entity nearestEnemy = findNearestEnemy(player.worldx, player.worldy);
       

        if (nearestEnemy != null) {
            // Calculate the distance to the nearest enemy
            float distanceToEnemy = getDistance(x, y, nearestEnemy.worldx, nearestEnemy.worldy);

            // Check if the nearest enemy is within the maximum range
            if (distanceToEnemy <= maxRange) {
                // Calculate direction vector towards the nearest enemy
                directionX = nearestEnemy.worldx - x;
                directionY = nearestEnemy.worldy - y;

                // Normalize the direction vector
                float magnitude = (float) Math.sqrt(directionX * directionX + directionY * directionY);
                directionX /= magnitude;
                directionY /= magnitude;
            } else {
                // If the enemy is too far, default to the player's facing direction
                setDirectionBasedOnPlayerFacing(player);
            }
        } else {
            // If no enemy is found, use the player's facing direction
            setDirectionBasedOnPlayerFacing(player);
        }

        // Define the speed of the projectile
        float speed = 5.0f; // Adjust as necessary for game balance
        int projectileCount = 6;
        float spacing = 20.0f; // Increase this value for more spacing between projectiles

        for (int i = 0; i < projectileCount; i++) {
            float offsetX = 0.0f;
            float offsetY = 0.0f;

            // Adjust the offset based on the direction the player is facing
            if (Math.abs(directionX) > Math.abs(directionY)) { 
                // Horizontal shot (left or right)
                offsetX = (i - (projectileCount - 1) / 2.0f) * spacing;
            } else {
                // Vertical shot (up or down)
                offsetY = (i - (projectileCount - 1) / 2.0f) * spacing;
            }

            // Spawn the projectile with the calculated offset
            shootProjectile(x + offsetX, y + offsetY, directionX, directionY, speed);
        }
    }

    // Helper method to set direction based on the player's facing direction
    private void setDirectionBasedOnPlayerFacing(Player player) {
        if (player.isFacingRight()) {
            directionX = 1.0f;
            directionY = 0.0f;
        } else if (player.isFacingLeft()) {
            directionX = -1.0f;
            directionY = 0.0f;
        } else if (player.isFacingUp()) {
            directionX = 0.0f;
            directionY = -1.0f;
        } else if (player.isFacingDown()) {
            directionX = 0.0f;
            directionY = 1.0f;
        }

        // Ensure a direction is always set (default to right if idle)
        if (directionX == 0 && directionY == 0) {
            directionX = 1.0f; // Default direction
        }
    }

    // Helper method to calculate the distance between two points
 


    private Entity findNearestEnemy(float playerX, float playerY) {
        Entity nearestEnemy = null;
        float minDistance = Float.MAX_VALUE;

        // Check enemy_orc array
        for (enemy_orc e : enemies) {
            if (e != null && !e.isDead()) {
                float distance = getDistance(playerX, playerY, e.worldx, e.worldy);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestEnemy = e;
                }
            }
        }

        // Check Superior_orc array
        for (Superior_orc s : superior) {
            if (s != null && !s.isDead()) {
                float distance = getDistance(playerX, playerY, s.worldx, s.worldy);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestEnemy = s;
                }
            }
        }

        return nearestEnemy;
    }

    // Helper method to calculate distance between two points
    private float getDistance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }




    

    @Override
    public void update() {
    	
    	 if(player.invihash >=5) {
  		   npc[4] = null;
  		   npc[5] = null;
  	   }
        if (!gameOver ) {
        	if(!dialougeOn) {
            player.update();
            choppie.update(); 
            updateProjectiles();
            for (superObj o : obj) {
                if (o != null) {
                    o.updateAniTick();
                }
            }
            for (superNpc n : npc) {
                if (n != null) {
                    n.updateAniTick();
                }
            }
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null) {
                    enemies[i].update(); // Update each enemy
                    
                    // If enemy is dead and its death animation is complete, remove it
                    if (enemies[i].isDead() && enemies[i].animationFrame >= enemies[i].deathM[enemies[i].direction].length) {
                    	enemies[i] = null;
                    }
                }
            }
            for (int i = 0; i < superior.length; i++) {
            	if(superior[i] == null) {
            		enemydead = true;
            	}
                if (superior[i] != null) {
                    superior[i].update(); // Update each enemy
                    
                    // If enemy is dead and its death animation is complete, remove it
                    if (superior[i].isDead() && superior[i].animationFrame >= superior[i].deathM[superior[i].direction].length) {
                    	superior[i] = null;
                    	
                    }
                }
            }
        	
        }
        handleWeaponCollisions();
        
    }
        
    }
    
    private void handleWeaponCollisions() {
        if (player.isAttacking) {
            

            // Check for collisions with enemies
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null && cChecker.checkWeaponCollision(player, enemies[i], player.weaponHitbox)) {
                    enemies[i].takeDamage(player.attackDamage); // Damage the enemy
                    
                 // If enemy is dead and its death animation is complete, remove it
                    if (enemies[i].isDead() && enemies[i].animationFrame >= enemies[i].deathM[enemies[i].direction].length) {
                    	enemies[i] = null;
                    }
                }
            }
            for (int i = 0; i < superior.length; i++) {
                if (superior[i] != null && cChecker.checkWeaponCollision(player, superior[i], player.weaponHitbox)) {
                    superior[i].takeDamage(player.attackDamage); // Damage the enemy
                    
                    
                    if (superior[i].isDead() && superior[i].animationFrame >= superior[i].deathM[superior[i].direction].length) {
                    	superior[i] = null;
                    	
                    }
                }
            }
            player.resetAttack(); // Reset the attack state after checking collisions
        }
        if (choppie.isAttacking) {
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null && cChecker.checkWeaponCollision(choppie, enemies[i], choppie.weaponHitbox)) {
                    enemies[i].takeDamage(0.09f);  // Damage the enemy
                }
            }
            for (int i = 0; i < superior.length; i++) {
                if (superior[i] != null && cChecker.checkWeaponCollision(choppie, superior[i], choppie.weaponHitbox)) {
                    superior[i].takeDamage(0.1f); // Damage the enemy
                }
            }
        }
    }
    
    public void setGameOver(boolean gameOver) {
    	this.gameOver = gameOver;
    }
    public void setDialouge(boolean dialougeOn) {
    	this.dialougeOn = dialougeOn;
    }

    @Override
    public void draw(Graphics g) {
    	
        Graphics2D g2 = (Graphics2D) g;

        tileManager.renderTile(g2);
        
        for (superObj o : obj) {
            if (o != null) {
                o.draw(g2, this, player);
            }
           
        }
       

     // In your draw method
    

       
      
        //draw enemy
        for (enemy_orc e : enemies) {
            if (e != null) {
                e.draw(g2, this); // Make sure to pass necessary parameters
            }
        }
        for (Superior_orc e : superior) {
            if (e != null) {
                e.draw(g2, this); // Make sure to pass necessary parameters
            }
        }
        
        player.render(g);
        choppie.draw(g2);
        
        // Draw NPCs
        for (superNpc n : npc) {
            if (n != null) {
                n.draw(g2, this, player);
            } 
        }
        if (gameOver == true) {
        	gameOverOverlay.draw(g);
        }
        if (dialougeOn == true) { 
        	Dialouge.draw(g2);
        }
        ui.draw(g2);
        drawProjectiles(g2);
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.playMs();
        music.loopMs();
    }

    public void stopMusic() {
        music.stopMs();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.playMs();
    }

    public void playloopSE(int i) {
        se.setFile(i);
        se.playMs();
        se.loopMs();
    }

    public void stopSE() {
        se.stopMs();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	if (!gameOver)
        if (player.isSpear() || player.isGun()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAtt(true);
                
            }
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    	if (!gameOver);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	if (!gameOver);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if (!gameOver);
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAtt(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	if (!gameOver);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	  if (dialougeOn) {
    	        if (e.getKeyCode() == KeyEvent.VK_ENTER && canProceedDialogue) {
    	            proceedDialogue();
    	            canProceedDialogue = false;  // Block further dialogue progression until key is released
    	        }
    	  }
              else {
              
    	
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setU(true);
                break;
            case KeyEvent.VK_A:
                player.setL(true);
                break;
            case KeyEvent.VK_S:
                player.setD(true);
                break;
            case KeyEvent.VK_D:
                player.setR(true);
                break;
            case KeyEvent.VK_O:
            	saveGame(); 
            	break;
            case KeyEvent.VK_L:
            
                loadGame();
                break;

            
            case KeyEvent.VK_SHIFT:
                player.setRunn(true);
                break;
            case KeyEvent.VK_2:
            	player.equipWeapon(2);  
               
                break;
            case KeyEvent.VK_3:
            	player.equipWeapon(3); 
                
                break;
            case KeyEvent.VK_1:
            player.equipWeapon(1); 
                break;
            case KeyEvent.VK_T:
                
               
                    player.diaoulgeentered = true;
                
            case KeyEvent.VK_ENTER:
                eHandler.healingpool(true);
               
                break;
            case KeyEvent.VK_SPACE:
                check = true;
                break;
            case KeyEvent.VK_R:
            	player.Manualreload(); 
            	
            	break;
           
        }
    	  }
    }
    // Add a method to start a dialogue
    public void startDialogue(String[] dialogue) {
        this.currentDialogue = dialogue;  // Set the current dialogue
        this.dialogueIndex = 0;           // Start from the first line
        setDialouge(true);                // Activate the dialogue system
    }

    // Modify the method to proceed through dialogue
    public void proceedDialogue() {
        if (currentDialogue != null && dialogueIndex < currentDialogue.length - 1) {
            dialogueIndex++;
        } else {
            // Dialogue is finished, turn it off
            setDialouge(false);
            dialogueIndex = 0;
            player.diaoulgeentered = false;
        }
    }

    // Modify the existing setDialouge method
   

    @Override
    public void keyReleased(KeyEvent e) {
    	if(gameOver) {
    		gameOverOverlay.keyPressed(e);
    	}
    	 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    	        canProceedDialogue = true;  // Reset the flag once the key is released
    	    }
    	else
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setU(false);
                break;
            case KeyEvent.VK_A:
                player.setL(false);
                break;
            case KeyEvent.VK_S:
                player.setD(false);
                break;
            case KeyEvent.VK_D:
                player.setR(false);
                break;
            case KeyEvent.VK_SHIFT:
                player.setRunn(false);
                break;
            case KeyEvent.VK_SPACE:
                check = false;
                break;
         
                
        }
    }

    public void setupGame() {
        assetr.setObject();
    }

    public void gameMusic() {
        playMusic(6);
    }

    public void WindowsLostFocus() {}

    public Player getPlayer() {
        return player;
    }

    public void resetAll() {
        // Reset the game over state
        gameOver = false;
        
        gameon = true;
        assetr.setObject();

        // Reset the player and game elements to their initial states
        player.resetPlayer();  // Ensure player is reset to initial state
        choppie = new choppie(29 * TILES_SIZE, 14 * TILES_SIZE,cChecker,this, player); 

        // Reset any other variables or states that need to be cleared
    }
    private void saveGame() {
    	getPlayer();
        if (player != null) {
            dbManager.savePlayerData(player);  // Save player data
            System.out.println("Game saved!");
       }
    }

    // Load game methods
    private void loadGame() {
        player = dbManager.loadLatestPlayerData(this); // Load the latest player data
        if (player != null) {
            cChecker = new CollisionCheck(this, tileManager); // Re-initialize CollisionCheck
            tileManager = new TileManager(this, player);      // Re-initialize TileManager
            player.loadPlayer();                              // Set player attributes after loading
            System.out.println("Game loaded with the latest save!");
        } else {
            System.out.println("No saved game data available to load.");
        }
    }



}