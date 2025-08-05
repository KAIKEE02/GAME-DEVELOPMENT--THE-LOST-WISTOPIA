package Entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;


import static utilz.Constants.PlayerConstants.WALKING_D;
import static utilz.Constants.PlayerConstants.WALKING_L;
import static utilz.Constants.PlayerConstants.WALKING_R;
import static utilz.Constants.PlayerConstants.WALKING_U;
import static utilz.Constants.PlayerConstants.WALKING_UR;
import static utilz.Constants.PlayerConstants.WALKING_UL;
import static Main.MAINGAME.TILES_IN_WIDTH;
import static Main.MAINGAME.TILES_IN_HEIGHT;
import static Main.MAINGAME.GAME_HEIGHT;
import static Main.MAINGAME.GAME_WIDTH;
import static Main.MAINGAME.TILES_SIZE;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


import Gamestates.Play;
import Gamestates.state;
import objects.superObj;
import Enemies.enemy_orc;

public class Player  extends Entity{
	
	Play screen;
	BufferedImage[][] walkM,walkS,walkG, idleM, attackM, shotM, idleG, idleS, runM, runG, runS, deathS, deathG, deathM, reloadM; 
	private BufferedImage shadow , stamicon;
	 private int aniTick, aniIndex, aniSpeed = 15;
	 private int PlayerAction;
	 private boolean moving = false;
	 public boolean L ,D ,R ,U,Death = false, att, runn, gun, spear, obtain = true;
	 private float playerSpeed = 1.0f;
	 public int hashKey = 1, hashorb = 0, invihash = 0;
	 public int doorAttemptCounter = 0, chestAttemptCounter = 0, AttemptCounter = 0; 
	private boolean doorSoundPlayed, chestSoundPlayed, SoundPlayed = false;
	public final  int screenx;
	public final  int screeny;
	private boolean walkingSoundPlaying = false;
	private boolean canPlaySound = true;
	private long soundCooldown = 1000; // Cooldown time in milliseconds
	private long lastSoundPlayedTime = 0;
	private long gameOverStartTime; // To track when the game over started
	private boolean showGameOverOverlay = false; // To control the display of the overlay
	private static final long GAME_OVER_DELAY = 800; // 2 seconds delay
	  public Rectangle weaponHitbox;  // Define the hitbox for the weapon
	  public int attackDamage ;
	    public boolean isAttacking = false; // Indicates if the player is currently attacking
	    public long attackCooldown = 600; // Cooldown time in milliseconds
	    public long lastAttackTime;
	    public long lastreloadtime;
	    public long reloadCooldown = 600;
	    private  int DEFAULT_ATTACK_DAMAGE = 4;
	    private   int GUN_ATTACK_DAMAGE = 8;
	  private int invincibilityFrames = 0;  // Cooldown frames after getting hit
	    private int invincibilityDuration = 240;  // Duration of invincibility in frames (1 second if 60 FPS)
	    private boolean invincible = false;
	    
	    private int STAMFrames = 0;  // Cooldown frames after getting hit
	    private int STAMDuration = 30;  // Duration of invincibility in frames (1 second if 60 FPS)
	    private boolean STAMCOUNT = false;
	  
	    private int dmgIncrease = 2; // Increment value for damage per level up
	    private int heartIncrease = 2; // Increment value for damage per level up
	    private int GunDmgIncrease = 4; // Increment value for gun damage per level up
	    private int Level = 1;    // Tracks the current level of damage
	    private int LevelCap = 20; // cap of lvl
	    private int HeartCap = 20; // cap of lvl
	    private int orbThreshold = 3; // Number of orbs required to level up damage
		private BufferedImage[] lvlup;
		private int aniLIndex;
		private boolean levelingUp = false;  // Flag to check if the player is leveling up
		private int lvlupAniDuration = 117;   // Duration to show the level-up animation (adjust as needed)
		private int lvlupAniTimer = 0;   
		public int bulletcounter = 40;
		private boolean reloadbool;
		 private static final int STAMBAR_BAR_WIDTH = 50;
		    private static final int STAMBAR_BAR_HEIGHT = 5;
		    private static final int STAMBAR_BAR_OFFSET_Y = +60; // Offset above the orc's sprite
		    public float Stamina;
		    public int maxStamina;
			private boolean isRegen;
			private int stamIncrease = 4;
			public boolean diaoulgeentered;
			private int keys;
			private boolean rdng;
	    
	   
	
	public Player(float x, float y, Play screen) {
		
		super(x, y);
		this.screen = screen;
		screenx =  GAME_WIDTH/2 - (TILES_SIZE /1);
		screeny =  GAME_HEIGHT/2 - (TILES_SIZE /1);
		solidArea = new Rectangle();
		solidArea.x = 58;
		solidArea.y = 38;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 16;
		solidArea.height = 16;
		
		//player status
		maxLife = 6;
		maxDef = 0;
		life = maxLife;
		defense = maxDef;
		Level = 1;
		maxStamina = 20;
		Stamina = maxStamina;
		
		// Initialize weapon hitbox (adjust position and size based on weapon)
        weaponHitbox = new Rectangle();  // Example size
        weaponHitbox.x = 35;
		weaponHitbox.y = 15;
		weaponHitbox.width = 58;
		weaponHitbox.height = 58;
		System.out.println("person  Starting position: (" + x + ", " + y + ")");
		initialize();
		loadAnimations();		
	}
	private String lastFacingDirection = "DOWN"; // Default facing direction
	private int meleeDamage;

	// Update the last facing direction based on movement
	public void updateFacingDirection() {
	    if (U) {
	        lastFacingDirection = "UP";
	    } else if (D) {
	        lastFacingDirection = "DOWN";
	    } else if (L) {
	        lastFacingDirection = "LEFT";
	    } else if (R) {
	        lastFacingDirection = "RIGHT";
	    }
	}

	// Getters for facing direction
	public boolean isFacingUp() {
	    return "UP".equals(lastFacingDirection);
	}

	public boolean isFacingDown() {
	    return "DOWN".equals(lastFacingDirection);
	}

	public boolean isFacingLeft() {
	    return "LEFT".equals(lastFacingDirection);
	}

	public boolean isFacingRight() {
	    return "RIGHT".equals(lastFacingDirection);
	}
	private void drawLevel(Graphics2D g2, int screenX, int screenY) {
	    // Set the font and color for the level text
	    g2.setFont(new Font("Arial", Font.BOLD, 12));  // Adjust font size as needed
	    g2.setColor(Color.WHITE);  // Set the color of the level text
	    
	    // Draw the level above the player
	    String levelText = "Level " + Level;  // Display "Level X"
	    g2.drawString(levelText, screenX + 20, screenY + 15);  // Adjust position as necessary
	}

	
	 private void drawStamBar(Graphics2D g2, int screenX, int screenY) {
	        // Calculate the health bar width based on the current health
	        float healthPercentage = (float) Stamina / maxStamina; // Assuming max health is 20
	        int healthBarCurrentWidth = (int) (STAMBAR_BAR_WIDTH * healthPercentage);

	        g2.drawImage(stamicon, screenx + 20,  screeny + STAMBAR_BAR_OFFSET_Y - 5 , 15, 15, null);
	        // Draw background (red for missing health)
	        g2.setColor(Color.WHITE);
	        g2.fillRect(screenX + 40, screenY + STAMBAR_BAR_OFFSET_Y, STAMBAR_BAR_WIDTH, STAMBAR_BAR_HEIGHT);

	        // Draw the current health (green)
	        g2.setColor(Color.blue);
	        g2.fillRect(screenX + 40, screenY + STAMBAR_BAR_OFFSET_Y, healthBarCurrentWidth, STAMBAR_BAR_HEIGHT);

	        // Optionally draw a border around the health bar (white)
	        g2.setColor(Color.WHITE);
	        g2.drawRect(screenX + 40, screenY + STAMBAR_BAR_OFFSET_Y,STAMBAR_BAR_WIDTH, STAMBAR_BAR_HEIGHT);
	    }
	 
	 private void initialize() {
	        // Initialization code
	        this.weaponHitbox = new Rectangle(35, 15, 58, 58); // Default weapon hitbox
	        
	        this.attackDamage = attackDamage;
	    }
	    public int getLevel() {
	        return Level;
	    }

	    public int getMaxLife() {
	        return maxLife;
	    }

	    public int getMaxDefense() {
	        return maxDef;
	    }
	 // Getter and Setter for attack damage
	    public int getAttackDamage() {
	        return meleeDamage;
	    }

	    public void setAttackDamage(int attackDamage) {
	        this.meleeDamage = attackDamage;
	    }

	    // Getter and Setter for gun damage
	    public int getGunDamage() {
	        return GUN_ATTACK_DAMAGE; // or your variable to hold gun damage
	    }

	    public void setGunDamage(int gunDamage) {
	        this.GUN_ATTACK_DAMAGE = gunDamage;  // or your variable to hold gun damage
	    }


	    public int getLife() {
	        return life;
	    }

	    public int getDefense() {
	        return defense;
	    }

	    public int getMaxStamina() {
	        return maxStamina;
	    }

	    public float getStamina() {
	        return Stamina;
	    }

	   

	    public int getBulletCounter() {
	        return bulletcounter;
	    }


	    

	    private void equipGun() {
	        attackDamage = GUN_ATTACK_DAMAGE;
	        weaponHitbox.setBounds(-35, -45, 200, 200); // Adjust hitbox for gun
	    }

	    private void equipDefaultWeapon() {
	        attackDamage = meleeDamage;
	        weaponHitbox.setBounds(35, 15, 58, 58); // Reset to default hitbox
	    }

    public void attack() {
    	
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= attackCooldown) {
            isAttacking = true; // Start attacking
            if(gun) {
            bulletcounter -= 10;
            if (bulletcounter >= 0) {
            screen.fireGun(this);
            }
            }
            lastAttackTime = currentTime; // Update the last attack time
        }
    	
    }

    public void resetAttack() {
        isAttacking = false; // Reset the attack state after the attack is done
    }
 public void playReloadSound() {
	 long currentTime = System.currentTimeMillis();
	 
	    if (currentTime - lastSoundPlayedTime > soundCooldown) {
	    	 
	            	att = false;
		        	screen.playSE(13);
		            lastSoundPlayedTime = currentTime;
	            
	    }
	  
 }
	public void playATTsound() {
	    long currentTime = System.currentTimeMillis();
	    
	    if (currentTime - lastSoundPlayedTime > soundCooldown) {
	    	 if(reloadbool) {
	            	att = false;
		        	screen.playSE(13);
		            lastSoundPlayedTime = currentTime;
	            }
	    else if (gun && att && obtain && !reloadbool) {
	            screen.playSE(10);
	            lastSoundPlayedTime = currentTime;
	            
	        }
	        if (spear && att) {
	            screen.playSE(5);
	            lastSoundPlayedTime = currentTime;
	        }
	       
	    }
	}

	
	public void update() {
		if(defense <= 0) {
			maxDef = 0;
		}
		   if (gun && obtain) {
	            equipGun();
	        } else {
	            equipDefaultWeapon();
	        }
		
	
	    if (att) {
	    	if(bulletcounter <= 0) {
	    		playReloadSound();
	    	}
	    	
	    	if(bulletcounter >=0) {
	        playATTsound();
	    	}
	        attack();
	       
	        
	    }
	    if(rdng) {
    		playReloadSound();
    		rdng = false;
    	}
	    if (STAMCOUNT) {
	    	handlestam();
	    }
	    
	    if (invincible) {
	        handleInvincibility();
	    }

	    updatePos();
	    if (att || moving || !moving) {
	    	updateFacingDirection();
	        updateAniTick();
	        Lvlup();
	        setAnimation();
	        reload();
	       
	        
	        
	    }

	    if (life <= 0) {
	        handleDeath();
	    }
	}
	private void staminadecrease() {
		if(!STAMCOUNT) {
			if(runn) {
            Stamina -= 1;
            STAMCOUNT = true;  // Enable invincibility after taking damage
            STAMFrames = STAMDuration;
			}
//			if(att && spear) {
//	            Stamina -= 2;
//	            STAMCOUNT = true;  // Enable invincibility after taking damage
//	            STAMFrames = STAMDuration;
//				}
		}
		 if (!runn && !att && Stamina < maxStamina && STAMFrames == 0) {
		        Stamina += 0.05;  // Example stamina regeneration rate
		        isRegen = true;
		        // Ensure stamina does not exceed maxStamina
		        if (Stamina > maxStamina) {
		            Stamina = maxStamina;
		            if(Stamina == maxStamina) {
		            	isRegen = false;
		            }
		        }
		    }
		
	}
	private void reload() {
	    // Check if the player needs to reload
	    if (bulletcounter <= -5) {
	    	
	        bulletcounter = 40; // Reset bullet count
	        reloadbool = true;
	        lastreloadtime = System.currentTimeMillis(); // Set the last reload time
	        isAttacking = false;
	    }
	    
	    long currentTime = System.currentTimeMillis();
	    
	    // Check if the reload cooldown has passed
	    if (reloadbool && (currentTime - lastreloadtime >= reloadCooldown)) {
	    	
	        reloadbool = false; // End the reloading process after the cooldown
	    }
	}
	public void Manualreload() {
	    // Check if the player needs to reload
	    if (bulletcounter <= 39) {
	    	
	        bulletcounter = 40; // Reset bullet count
	        reloadbool = true;
	        rdng = true;
	        lastreloadtime = System.currentTimeMillis(); // Set the last reload time
	        isAttacking = false;
	    }
	    
	    long currentTime = System.currentTimeMillis();
	    
	    // Check if the reload cooldown has passed
	    if (reloadbool && (currentTime - lastreloadtime >= reloadCooldown)) {
	    	
	        reloadbool = false; // End the reloading process after the cooldown
	        rdng = false;
	    }
	}

	
	private void Lvlup() {
	    // Assuming `hashorb` is the number of collected orbs
		if(Level <= LevelCap) {
	    if (hashorb >= Level * orbThreshold) {
	        Level++; // Increment  level
	        if(maxLife <= HeartCap) {
	        maxLife += heartIncrease;
	        maxStamina += stamIncrease ;
	        life = maxLife;
	        }
	        DEFAULT_ATTACK_DAMAGE += dmgIncrease; // Increase damage
	        GUN_ATTACK_DAMAGE += GunDmgIncrease;
	        screen.ui.showMessage("Player lvl up. lvl : " + Level); 
	        levelingUp = true;
	    }
		}if (Level == LevelCap) {
			screen.ui.showMessage("Already Maxed out. lvl : " + Level);
			
		}
	}
	

	private void handleInvincibility() {
	    if (invincibilityFrames > 0) {
	        invincibilityFrames--;  // Decrease the cooldown timer
	    } else {
	        invincible = false;  // Reset invincibility after cooldown
	       
	    }
	}
	private void handlestam() {
	    if (STAMFrames > 0) {
	    	STAMFrames--;  // Decrease the cooldown timer
	    } else {
	        STAMCOUNT = false;  // Reset invincibility after cooldown
	       
	    }
	}

    public void takeDamage(int damage) {
        if (!invincible) {  
        	
        	// Only take damage if not invincible
        	if(defense <= 0) {
            life -= damage;
            invincible = true;  // Enable invincibility after taking damage
            invincibilityFrames = invincibilityDuration;
        	} else {
        		defense -= damage;
        		 invincible = true;  // Enable invincibility after taking damage
                 invincibilityFrames = invincibilityDuration;
        	}
         

            // Play hit sound effect or visual feedback if desired
           // screen.playSE(9);  // Example sound effect ID for taking damage
        }
    }


    private void handleDeath() {
        if (life <= 0) {
            if (gameOverStartTime == 0) {
                gameOverStartTime = System.currentTimeMillis();  // Record time of death
            }
            long currentTime = System.currentTimeMillis();
            if (currentTime - gameOverStartTime >= GAME_OVER_DELAY) {
                screen.setGameOver(true);  // Trigger game over screen after delay
                return;
            }
        }
    

    // Rest of the Player class (updatePos(), setAnimation(), etc.) remains unchanged
}
		
	
	

	 public void resetBoolean() {
		 U = false;
		 L = false;
		 D = false;
		 R = false;	
		
	 }
	 public Rectangle getSolidArea() {
		
		    return this.solidArea;
		}
	   public float getWorldX() {
	        return worldx ;
	    }

	    public int getScreenX() {
	        return screenx;
	    }
	    public float getWorldY() {
	        return worldy ;
	    }

	    public int getScreenY() {
	        return screeny;
	    }
	    public int getOrbs() {
	        return hashorb;
	    }

	    public void setOrbs(int orbs) {
	        this.hashorb = orbs;
	    }

	    public int getKeys() {
	        return hashKey;
	    }

	    public void setKeys(int keys) {
	        this.hashKey = keys;
	    }

		   
			public boolean isL() {
				return L;
			}

			public void setL(boolean l) {
				L = l;
			}

			public boolean isD() {
				return D;
			}

			public void setD(boolean d) {
				D = d;
			}

			public boolean isU() {
				return U;
			}

			public void setU(boolean u) {
				U = u;
			}

			public boolean isR() {
				return R;
			}

			public void setR(boolean r) {
				R = r;
			}

			
			public void setAtt(boolean att) {
				this.att = att;
			}
			

			public boolean isRunn() {
				return runn;
			}

			public void setRunn(boolean runn) {
				this.runn = runn;
			}
			

			public boolean isGun() {
				return gun;
			}

			public void setGun(boolean gun) {
				this.gun = gun;
			}

			public boolean isSpear() {
				return spear;
			}

			public void setSpear(boolean spear) {
				this.spear = spear; 
			}
			public void setLevel(int level) {
			    this.Level = level;
			}

			public void setMaxLife(int maxLife) {
			    this.maxLife = maxLife;
			}

			public void setMaxDefense(int maxDefense) {
			    this.maxDef = maxDefense;
			}

			public void setLife(int life) {
			    this.life = life;
			}

			public void setDefense(int defense) {
			    this.defense = defense;
			}

			public void setMaxStamina(int maxStamina) {
			    this.maxStamina = maxStamina;
			}

			public void setStamina(float stamina) {
			    this.Stamina = stamina;
			}

			public void setBulletCounter(int bulletCounter) {
			    this.bulletcounter = bulletCounter;
			}

			
			public void equipWeapon(int weaponIndex) {
			    switch (weaponIndex) {
			    case 1:
			    	 spear = false;  // Equip spear when '2' is pressed
			            gun = false;   // Ensure gun is unequipped
			         
			            screen.ui.showMessage("unequipped");  // Optional: Show message on the screen
			            break;
			    	
			        case 2:
			            spear = true;  // Equip spear when '2' is pressed
			            gun = false;   // Ensure gun is unequipped
			          
			            screen.ui.showMessage("Spear equipped!");  // Optional: Show message on the screen
			            break;
			            
			        case 3:
			            spear = false;  // Equip spear when '2' is pressed
			            gun = true;   // Ensure gun is unequipped
			          if(gun && obtain) {
			            screen.ui.showMessage("gun equipped!");  // Optional: Show message on the screen
			          }
			            break;
			            
			        // other cases for different weapons if needed
			    }
			}

			private void updateAniTick() {
				aniTick++;
				if (aniTick >= aniSpeed) {
					aniTick -= aniSpeed;
					aniTick = 0;
					aniIndex++;
					aniLIndex++;
					if (aniIndex >= GetSpriteAmount(PlayerAction)) {
						
						aniIndex = 0;
						att = false;
					}
                 if (aniLIndex >= 11) {
						
                	 aniLIndex = 0;
						
					}
				}
			}
			
			private void resetAniTick() {
				aniTick = 0;
				aniIndex = 0;
				
				
			}

			public void setAnimation() {
			int startAni = PlayerAction;
			
	
			if(moving) {
				
				if(R) {
				PlayerAction = WALKING_R;
			}
				
				else if(L) {
					PlayerAction = WALKING_L;
				}
				else if(U) {
					PlayerAction = WALKING_U;
				}
				else {
					PlayerAction = WALKING_D; 
				}
				
				
				 if(U && R) {
					PlayerAction = WALKING_UR; 
				}
				else if(U && L) {
					PlayerAction = WALKING_UL; 
				}
				 
					
				 
				 
				}
			else {	
	
			if (startAni != PlayerAction) {
				resetAniTick();
			}
			
			}
			}
			
			public void pickUpObj(int i) {
				
				if(i != -1) {
					String objectName = screen.obj[i].name;
					switch(objectName) {
					
					case "key" :
						
					    screen.playSE(3);
						hashKey++;
						screen.obj[i] = null;
						screen.ui.showMessage("You got a key!");
						break;
						
					case "door":
						
		                doorAttemptCounter++; // Increment the attempt counter

		                if (doorAttemptCounter % 160 == 0) { // Play sound again every 3 attempts
		                    doorSoundPlayed = false;
		                }

		                if (hashKey > 0) {
		                    screen.playSE(4);
		                    screen.obj[i] = null;
		                    hashKey--;
		                    doorSoundPlayed = false; // Reset the flag when the door is successfully opened
		                    doorAttemptCounter = 0; // Reset the counter
		                } else {
		                    
		                    if (!doorSoundPlayed) {
		                        screen.playSE(8); // Play the sound only if it hasn't been played yet
		                        doorSoundPlayed = true;
		                        
		                    }

		                    screen.ui.showMessage("You need a key!");
		                }
		                System.out.println("Key(s): " + hashKey);
		                break;
						
					case "soulOrb":
						
						screen.playSE(7);
						hashorb++;
						screen.obj[i] = null;
						screen.ui.showMessage("You got an soul orb!!!!");
						break;
						
					case "chest" :
						
					     chestAttemptCounter++; // Increment the attempt counter

			                if (chestAttemptCounter % 160 == 0) { // Play sound again every 1.9 SECS
			                    chestSoundPlayed = false;
			                }
						if(hashorb >= 10) {
							screen.playSE(4);
							screen.ui.obtains();
							obtain = true;
							screen.obj[i] = null;
							hashorb--;
							 chestSoundPlayed = false; // Reset the flag when the door is successfully opened
			                    chestAttemptCounter = 0; // Reset 
						}
						else {
							 if (!chestSoundPlayed) {
			                        screen.playSE(8); // Play the sound only if it hasn't been played yet
			                        chestSoundPlayed = true;
			                        
			                    }
							screen.ui.showMessage("You need a 10 souls orb!");
							
							
						}
						break;
					case "maliya" :
						
						 AttemptCounter++; // Increment the attempt counter
						 if(R || U) {

			                if (AttemptCounter % 30 == 0) { // Play sound again every 1.9 SECS
			                	
			                    SoundPlayed = false;
			                }
						 if (!SoundPlayed) {
							 screen.stopMusic();
		                        SoundPlayed = true;
		                        screen.ui.showMapName("    MALIYA FOREST");
		                        screen.playMusic(0); // Play the sound only if it hasn't been played yet
		                        AttemptCounter = 0;
		                        
		                    }
						 }
						break;
						
					case "wistg" :
						
						 AttemptCounter++; // Increment the attempt counter
						 if(L || U) {

			                if (AttemptCounter % 30 == 0) { // Play sound again every 1.9 SECS
			                	
			                    SoundPlayed = false;
			                }
						 if (!SoundPlayed) {
							 screen.stopMusic();
		                        SoundPlayed = true;
		                        screen.ui.showMapName("WISTOPIA GROUNDS");
		                        screen.playMusic(6); // Play the sound only if it hasn't been played yet
		                        AttemptCounter = 0;
		                        
		                    }
						 }
						break;
						
                       case "blue_heart" :
						
					    screen.playSE(3);
					    screen.obj[i] = null;
					
					    
					    
						if(maxDef == 0) {
							maxDef = 6;
							defense = maxDef - 2;
						}
							
						defense += 2;
						
					    
						screen.ui.showMessage("You got a Shield!");
						break;
						
                       case "heart" :
   						
   					    screen.playSE(3);
   					    screen.obj[i] = null;
   						if(life >= maxLife) {
   						life += 2;
   						}
   						screen.ui.showMessage("You are Healed!");
   						break;
   						
   						
                    
						
					}
				}
			}
				
               public void npcInteract(int i) {
				
				if(i != -1) {
					String npcname = screen.npc[i].name;
					switch(npcname) {
					
					
   						
   						
                       case "shadow" :  
                    	   screen.ui.showMessage("Press T TO TALK");
                    	   if (diaoulgeentered) {
                    	   screen.setDialouge(true);
                   
                    	 screen.startDialogue(screen.contents.god());
                    	   
                    	   }
                    			       
      						break;

                       case "guards" :  
                    	   screen.ui.showMessage("Press T TO TALK");
                    	   if (diaoulgeentered) {
                    	   screen.setDialouge(true);
                   
                    	 screen.startDialogue(screen.contents.guardsentry());
                    	   
                    	   }
                    	  
                    			       
      						break;
						
					}
				}
			}
				
			
			private void updatePos() {
			    moving = false;
			    staminadecrease();
			    int npcIndex;

			    if (!att && !reloadbool) {
			        collisionON = false;

			        // Movement logic
			        if (L && !R) {
			            screen.cChecker.checkTile(this, true, false, false, false, playerSpeed);
			            int objIndex = screen.cChecker.checkObjectCollision(this, true, true, false, false, false, playerSpeed);
			            npcIndex = screen.cChecker.checkNpc(this, true, true, false, false, false, playerSpeed);
			            pickUpObj(objIndex);
			            npcInteract(npcIndex);
			            screen.eHandler.checkEvent();
			         
			            if (!collisionON) {
			                worldx -= playerSpeed;
			                moving = true;
			            }
			        }
			        if (R && !L) {
			            screen.cChecker.checkTile(this, false, true, false, false, playerSpeed);
			            int objIndex = screen.cChecker.checkObjectCollision(this, true, false, true, false, false, playerSpeed);
			            npcIndex = screen.cChecker.checkNpc(this, true, false, true, false, false, playerSpeed);
			            pickUpObj(objIndex);
			            npcInteract(npcIndex);
			            if (npcIndex != -1) {
			                System.out.println("Interacting with NPC: " + screen.npc[npcIndex].name);  // Debug: Check if NPC interaction is being triggered
			                npcInteract(npcIndex);
			            }
			            screen.eHandler.checkEvent();

			            if (!collisionON) {
			                worldx += playerSpeed;
			                moving = true;
			            }
			        }
			        if (U && !D) {
			            screen.cChecker.checkTile(this, false, false, true, false, playerSpeed);
			            int objIndex = screen.cChecker.checkObjectCollision(this, true, false, false, true, false, playerSpeed);
			            npcIndex= screen.cChecker.checkNpc(this, true, false, false, true, false, playerSpeed);
			            pickUpObj(objIndex);
			            npcInteract(npcIndex);
			            screen.eHandler.checkEvent();
			            

			            if (!collisionON ) {
			                worldy -= playerSpeed;
			                moving = true;
			            }
			        }
			        if (D && !U) {
			            screen.cChecker.checkTile(this, false, false, false, true, playerSpeed);
			            int objIndex = screen.cChecker.checkObjectCollision(this, true, false, false, false, true, playerSpeed);
			            npcIndex = screen.cChecker.checkNpc(this, true, false, false, false, true, playerSpeed);
			            pickUpObj(objIndex);
			            npcInteract(npcIndex);
			            screen.eHandler.checkEvent();
			             
			            

			            if (!collisionON ) {
			                worldy += playerSpeed;
			                moving = true;
			            }
			        }

			        if ((L && U) || (R && U) || (L && D) || (R && D)) {
			            playerSpeed *= 0.707f;  // Adjust speed for diagonal movement (sqrt(2)/2)
			        }

			        // Adjust speed based on running state
			        playerSpeed = runn ? 1.9f : 1.0f;
			        if (Stamina  <= 0 ) {
			        	playerSpeed = 1.0f;
			        	runn = false;
			        	
			        }
			        
			        
			    }
		
			} 

			
			
	
			public void updateG() {
				 
				update();
			
			}
			public void resetPlayer() {
			    // Reset player's position, life, and other variables
				  this.worldx = 29 * TILES_SIZE + 8;
				    this.worldy = 12 * TILES_SIZE - 7;
				    maxLife = 6;
				    this.life = maxLife;
				    this.defense = maxDef;
				    this.Death = false;
				    gameOverStartTime = 0;
				    // Reset player state variables like movement and attack flags
				    this.L = this.D = this.R = this.U = false;
				    this.att = this.runn = false;
				    this.hashKey = 0;
				    this.hashorb = 0;
				    this.gun = this.spear = false;

				    // Reset animation variables
				    this.aniIndex = 0;
				    this.aniTick = 0;
				    this.DEFAULT_ATTACK_DAMAGE = 7;
				    this.meleeDamage = 7;
				   this.GUN_ATTACK_DAMAGE = 10;
				   this.Level = 1;
				   this.Stamina = maxStamina;
				   
			   
			}
			public void loadPlayer() {
			    // Reset player's position, life, and other variables
				
				    gameOverStartTime = 0;
				    // Reset player state variables like movement and attack flags
				    this.L = this.D = this.R = this.U = false;
				    this.att = this.runn = false;
				  
				    this.gun = this.spear = false;

				    // Reset animation variables
				    this.aniIndex = 0;
				    this.aniTick = 0;
				 
				   
			   
			}
	
			private void drawStatusWindow(Graphics g, int x, int y) {
			    // Draw background for the status window
			    g.setColor(Color.BLACK);
			    g.fillRect(x-750, y, 200, 300); // Size of the window to accommodate health, stamina, etc.
			    g.setColor(Color.WHITE);
			    g.drawRect(x-750, y, 200, 300); // Draw border for the window
			    
  			    // Draw the Health Status
			    g.setColor(Color.RED);
			    g.fillRect(x-750 + 10, y + 18, 180, 15); // Background health bar
			    g.setColor(Color.BLUE);
			    float healthPercentage = (float) life / maxLife;
			    g.fillRect(x-750 + 10, y + 18, (int) (180 * healthPercentage), 15); // Foreground health bar
			    g.setColor(Color.WHITE);
			    g.drawString("Health: " + life + "/" + maxLife, x -750 + 10, y + 30); // Display health value

			    // Draw the Stamina Value with number (below the Health)
			    g.setColor(Color.WHITE);
			    g.drawString("Stamina: " + Stamina , x -750 + 10, y + 50); // Stamina as numbers

			    // Draw the Attack Power
			    g.setColor(Color.WHITE);
			    g.drawString("melee Power: " + meleeDamage, x  -750+ 10, y + 80);

			    // Draw the Defense Value
			    g.setColor(Color.WHITE);
			    g.drawString("Defense: " + defense, x -750 + 10, y + 100);

			    // Draw the Keys Collected
			   
			    
			   
			    
			    // Draw the Keys Collected
			    g.setColor(Color.WHITE);
			    g.drawString("GUN POWER: " + GUN_ATTACK_DAMAGE, x-750  + 10, y + 180);
			    
			    // Draw the Keys Collected
			    g.setColor(Color.WHITE);
			    g.drawString("melee: " + meleeDamage, x-750  + 10, y + 200);


			    // Draw the Keys Collected
			    g.setColor(Color.WHITE);
			    g.drawString("defense: " + defense, x-750  + 10, y + 220);
			    
			    // Draw the Keys Collected
			    g.setColor(Color.WHITE);
			    g.drawString("level: " + Level, x-750  + 10, y + 240);
			   
			    
			    // Draw the Keys Collected
			 

			    // Optionally, draw Invincibility Status (remaining invincibility frames)
			    if (invincible) {
			        g.setColor(Color.YELLOW);
			        g.drawString("Invincible: " + invincibilityFrames + " frames", x -750 + 10, y + 280);
			    } else {
			        g.setColor(Color.GRAY);
			        g.drawString("Invincible: N/A", x -750 + 10, y + 280); // If not invincible
			    }


			    
			}	

	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/walk.png");
		InputStream idl = getClass().getResourceAsStream("/idle.png");
		InputStream att = getClass().getResourceAsStream("/attack_spear.png");
		InputStream sht = getClass().getResourceAsStream("/Shooting.png");
		InputStream idlS = getClass().getResourceAsStream("/idle_spear.png");
		InputStream idlG = getClass().getResourceAsStream("/Idle_Gun.png");
		InputStream runns = getClass().getResourceAsStream("/run.png"); 
		InputStream runGUN = getClass().getResourceAsStream("/Run_Gun.png"); 
		InputStream runsp = getClass().getResourceAsStream("/run_spear.png"); 
		InputStream walkSp = getClass().getResourceAsStream("/walk_spear.png"); 
		InputStream walkGu = getClass().getResourceAsStream("/Walk_Gun.png"); 
		InputStream shd = getClass().getResourceAsStream("/Shadow.png"); 
		InputStream dts = getClass().getResourceAsStream("/death_spear.png"); 
		InputStream dtg = getClass().getResourceAsStream("/death_gun.png"); 
		InputStream dtm = getClass().getResourceAsStream("/death_normal.png"); 
		InputStream up = getClass().getResourceAsStream("/lvlupAni.png"); 
		InputStream rld = getClass().getResourceAsStream("/Reloading.png"); 
		InputStream stam = getClass().getResourceAsStream("/staminaIcon.png"); 
		
			 try {
				shadow = ImageIO.read(shd);
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
			 
			 try {
					stamicon = ImageIO.read(stam);
				} catch (IOException e) {
					
					e.printStackTrace();
				}	
		
		try {
			BufferedImage runningimg = ImageIO.read(is);
			walkM = new BufferedImage[6][8];
			
			
		for(int j = 0; j < walkM.length; j++) {
			for(int i = 0; i < walkM[j].length; i++) {
			walkM[j][i] = runningimg.getSubimage(i*48, j*64, 48, 64);
			}
		}
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage attack = ImageIO.read(att);
			attackM = new BufferedImage[6][8];
			
			
		for(int j = 0; j < attackM.length; j++) {
			for(int i = 0; i < attackM[j].length; i++) {
			attackM[j][i] = attack.getSubimage(i*48, j*64, 48, 64);
			}
		}
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				att.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}	
		try {
			BufferedImage idleimg = ImageIO.read(idl);
			idleM = new BufferedImage[6][8];
			for(int j = 0; j < idleM.length; j++) {
				for(int i = 0; i < idleM[j].length; i++) {
				idleM[j][i] = idleimg.getSubimage(i*48, j*64, 48, 64);
				}
			}
				
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				idl.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage shot = ImageIO.read(sht);
			shotM = new BufferedImage[6][8];
			for(int j = 0; j < shotM.length; j++) {
				for(int i = 0; i < idleM[j].length; i++) {
				shotM[j][i] = shot.getSubimage(i*48, j*64, 48, 64);
				}
			}
				
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				sht.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage IDLES = ImageIO.read(idlS);
			idleS = new BufferedImage[6][8];
			for(int j = 0; j < shotM.length; j++) {
				for(int i = 0; i < idleS[j].length; i++) {
				idleS[j][i] = IDLES.getSubimage(i*48, j*64, 48, 64);
				}
			}
				
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				idlS.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage IDLEG = ImageIO.read(idlG);
			idleG = new BufferedImage[6][8];
			for(int j = 0; j < shotM.length; j++) {
				for(int i = 0; i < idleG[j].length; i++) {
				idleG[j][i] = IDLEG.getSubimage(i*48, j*64, 48, 64);
				}
			}
				
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				idlG.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage RUNNS = ImageIO.read(runns);
			runM = new BufferedImage[6][8];
			for(int j = 0; j < runM.length; j++) {
				for(int i = 0; i < runM[j].length; i++) {
				runM[j][i] = RUNNS.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				runns.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage RUNGUN = ImageIO.read(runGUN);
			runG = new BufferedImage[6][8];
			for(int j = 0; j < runG.length; j++) {
				for(int i = 0; i < runG[j].length; i++) {
				runG[j][i] = RUNGUN.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				runGUN.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage RUNSPE = ImageIO.read(runsp);
			runS = new BufferedImage[6][8];
			for(int j = 0; j < runS.length; j++) {
				for(int i = 0; i < runG[j].length; i++) {
				runS[j][i] = RUNSPE.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				runsp.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage walks = ImageIO.read(walkSp);
			walkS = new BufferedImage[6][8];
			for(int j = 0; j < walkS.length; j++) {
				for(int i = 0; i < walkS[j].length; i++) {
				walkS[j][i] = walks.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				walkSp.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage walkg = ImageIO.read(walkGu);
			walkG = new BufferedImage[6][8];
			for(int j = 0; j < walkG.length; j++) {
				for(int i = 0; i < walkG[j].length; i++) {
				walkG[j][i] = walkg.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				walkGu.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage death = ImageIO.read(dts);
			deathS = new BufferedImage[6][8];
			for(int j = 0; j < deathS.length; j++) {
				for(int i = 0; i < deathS[j].length; i++) {
				deathS[j][i] = death.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				walkGu.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage death = ImageIO.read(dtg);
			deathG= new BufferedImage[6][8];
			for(int j = 0; j < deathG.length; j++) {
				for(int i = 0; i < deathG[j].length; i++) {
				deathG[j][i] = death.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				walkGu.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage death = ImageIO.read(dtm);
			deathM= new BufferedImage[6][8];
			for(int j = 0; j < deathM.length; j++) {
				for(int i = 0; i < deathM[j].length; i++) {
				deathM[j][i] = death.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				walkGu.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			BufferedImage reloads = ImageIO.read(rld);
			reloadM= new BufferedImage[6][8];
			for(int j = 0; j < reloadM.length; j++) {
				for(int i = 0; i < reloadM[j].length; i++) {
				reloadM[j][i] = reloads.getSubimage(i*48, j*64, 48, 64);
				}
			}
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				walkGu.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		try {
			BufferedImage lvl = ImageIO.read(up);
			lvlup = new BufferedImage[11];
			 
				for(int i = 0; i < lvlup.length; i++) {
				lvlup[i] = lvl.getSubimage(i*64, 0, 64, 64);
				}
			
				 
		} catch (IOException e) {
			System.out.println("fucked up");
			e.printStackTrace();
		} finally {
			try {
				up.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}	
	public void render(Graphics g) {
		
		
	    g.drawImage(shadow, screenx,  screeny, 128, 90, null);
	    
	    
	    BufferedImage currentFrame;
	    Graphics2D g2 = (Graphics2D) g;
	    if (isRegen) {
	    drawStamBar(g2, screenx, screeny);
	    
	    }
	    drawLevel(g2, screenx, screeny);
	    
	    if (att) {
	        if (spear) {
	            currentFrame = attackM[PlayerAction][aniIndex];
	        } else if (gun && obtain) {
	            currentFrame = shotM[PlayerAction][aniIndex];
	        } else {
	        	currentFrame = idleM[PlayerAction][aniIndex];
	        }
	    } else if (moving) {
	    	
	    	
	        if (runn) {
	        	drawStamBar(g2, screenx, screeny);
	            if (spear) {
	                currentFrame = runS[PlayerAction][aniIndex];
	            } else if (gun && obtain) {
	                currentFrame = runG[PlayerAction][aniIndex];
	            } else {
	                currentFrame = runM[PlayerAction][aniIndex];
	            }
	        } else {
	            if (spear) {
	                currentFrame = walkS[PlayerAction][aniIndex];
	            } else if (gun && obtain) {
	                currentFrame = walkG[PlayerAction][aniIndex];
	            } else {
	                currentFrame = walkM[PlayerAction][aniIndex];
	            }
	        }
	    }
	    else if(Death == true) {
	    	 if (spear) {
	                currentFrame = deathS[PlayerAction][aniIndex];
	            } else if (gun && obtain) {
	                currentFrame = deathG[PlayerAction][aniIndex];
	            } else {
	                currentFrame = deathM[PlayerAction][aniIndex];
	            }
	    }
	    
	    else {
	        if (spear) {
	            currentFrame = idleS[PlayerAction][aniIndex];
	        } else if (gun && obtain) {
	            currentFrame = idleG[PlayerAction][aniIndex];
	        } else {
	            currentFrame = idleM[PlayerAction][aniIndex];
	        }
	    }
	    
	    if (gun && reloadbool) {
	    	 currentFrame = reloadM[PlayerAction][aniIndex];
	    }
	    if (levelingUp) {
	        g.drawImage(lvlup[aniLIndex], screenx, screeny + 20, 128, 80, null);
	        lvlupAniTimer++;  // Increment the timer
	        if (lvlupAniTimer >= lvlupAniDuration) {
	            levelingUp = false;  // Stop showing the level-up animation
	            lvlupAniTimer = 0;   // Reset the timer
	        }
	    }
	    if(screen.check == true) {
	    drawStatusWindow (g, screenx + 1000, screeny - 200);
	    }
	    g.drawImage(currentFrame, screenx,  screeny, 128, 90, null);
	    
	  
//	     //FOR COLLISION DEBUGGOMNG
    g.setColor(Color.RED);  // Set color to easily identify the collision box
//	    g.drawRect((int) screenx + solidArea.x  , (int) screeny + solidArea.y , solidArea.width, solidArea.height);
//	   g.drawRect((int) screenx + weaponHitbox.x  , (int) screeny + weaponHitbox.y , weaponHitbox.width, weaponHitbox.height);
	}		
		}


