	package Enemies;
	
	import java.awt.Color;
	import java.awt.Graphics2D;
	import java.awt.Rectangle;
	import java.awt.image.BufferedImage;
	import java.io.IOException;
	import java.io.InputStream;
	import java.util.Random;
	import javax.imageio.ImageIO;
	
	import Entities.Entity;
	import Entities.Player;
	import Gamestates.Play;
	import Main.CollisionCheck;
	import objects.obj_BlueHeart;
	import objects.obj_Heart;
	import objects.obj_key;
	import objects.obj_soulOrb;
	import objects.superObj;
	
	public class enemy_orc extends Entity {
	
		private BufferedImage img; 
	    private float speed; 
	    private Random random; 
	    private BufferedImage[][] walkM, attackM, idle; // Death animation added
		public BufferedImage[][] deathM;
	    public int animationFrame = 0; 
	    private int frameTimer = 0; 
	    public int direction = 0; 
	    private CollisionCheck collisionCheck;
	    private Play play;
	    private int health;
	    private boolean dead = false;
	    public  boolean deathAnimationFinished = false; // Tracks if the death animation has finished
	    public Rectangle attackHitbox;
	    private boolean isAttacking = false;
	    private static final int ATTACK_RANGE = 48;
	    private static final int HEALTH_BAR_WIDTH = 50;
	    private static final int HEALTH_BAR_HEIGHT = 5;
	    private static final int HEALTH_BAR_OFFSET_Y = -10; // Offset above the orc's sprite
	    private boolean isAlive = true;
	    private boolean isIdle = false;  // New field to track idle state
	    private int idleTimer = 0;       // Timer for idle duration
	    private final int MAX_IDLE_TIME = 190;  // Duration of idle (e.g., 190 frames)
	
	
	    public enemy_orc(int x, int y, BufferedImage img, CollisionCheck collisionCheck, Play play) {
	        super(x, y);
	        this.img = img; 
	        this.speed = 0.5f; 
	        this.solidArea = new Rectangle(38, 38, 25, 16);
	        this.random = new Random();
	        this.collisionCheck = collisionCheck;
	        this.play = play;
	        this.attackHitbox = new Rectangle(x, y, 32, 32);
	        this.health = 20; 
	        loadAnimation();
	    }
	  
	    private void playDeathAnimation() {
	        if (direction < 4 && animationFrame < deathM[direction].length) {
	            // Death animation logic (similar to walk/attack logic)
	        }
	    }
	    
	    public Rectangle getHitbox() {
	        return this.solidArea;
	    }
	
	    public int getHealth() {
	        return health;
	    }
	
	    public boolean isDead() {
	        return dead;
	    }
	
	    public void takeDamage(float f) {
	        if (!dead) {
	            
	            health -= f;
	            play.playSE(11);
	            if (health <= 0) {
	            	isAlive = false;
	                die();
	            }
	        } else {
	            
	            play.dead = true;
	        }
	    }
	
	    public void die() {
	        dead = true;
	        play.playSE(12);
	        isAttacking = false;
	        animationFrame = 0; // Reset the animation frame for the death animation
	        dropItem();
	    }
	    
	 // Method to handle item drop
	    public void dropItem() {
	        int randomItem = (int)(Math.random() * 3); // Randomly select an item (3 different types for example)
	        superObj droppedItem = null;
	        superObj defaultitemdrop = new obj_soulOrb();
	
	        switch(randomItem) {
	            case 0:
	                droppedItem = new obj_Heart() ;// Drop a Heart
	                break;
	            case 1:
	                droppedItem = new obj_soulOrb(); // Drop a soul orb
	                break;
	            case 2:
	                droppedItem = new obj_BlueHeart(); // Drop a shield i just named it blueheart
	                break;
	        }
	
	        if (droppedItem != null) {
	            // Set the item's position to the enemy's world position
	            droppedItem.worldx = (int) this.worldx;
	            droppedItem.worldy = (int) this.worldy;
	            // Add the dropped item to the play object's item array (assuming you have 50 items max)
	            for (int i = 0; i < play.obj.length; i++) {
	                if (play.obj[i] == null) {
	                    play.obj[i] = droppedItem;
	                    break;
	                }
	            }
	        }
	        if (defaultitemdrop != null) {
	            // Set the item's position to the enemy's world position
	            defaultitemdrop.worldx = (int) this.worldx + 20;
	            defaultitemdrop.worldy = (int) this.worldy + 20;
	            // Add the dropped item to the play object's item array (assuming you have 50 items max)
	            for (int i = 0; i < play.obj.length; i++) {
	                if (play.obj[i] == null) {
	                    play.obj[i] = defaultitemdrop;
	                    break;
	                }
	            }
	        }
	    }
	    @Override
	  
	    public void update() {
	        if (dead) {
	            if (!deathAnimationFinished) {
	                playDeathAnimation();
	            }
	            return;
	        }
	
	        // Check if the player is visible based on line of sight (ignoring walls)
	        if (isPlayerInLineOfSight()) {
	        	if(play.player.gun || play.player.spear) {
	            if (isPlayerInAttackRange()) {
	                isAttacking = true;
	                attackPlayer();
	            } else {
	                isAttacking = false;
	                chasePlayer();  // Chase player if visible
	            }
	        	}
	        	else {
	                // If player isn't visible, use random movement
	                isAttacking = false;
	                randomMovement();
	        	}
	        } else {
	            // If player isn't visible, use random movement
	            isAttacking = false;
	            randomMovement();
	        }
	
	        updateAttackHitbox();
	    }
	
	    private void randomMovement() {
	        boolean movingLeft = direction == 2;
	        boolean movingRight = direction == 3;
	        boolean movingUp = direction == 1;
	        boolean movingDown = direction == 0;
	
	        collisionCheck.checkTile(this, movingLeft, movingRight, movingUp, movingDown, speed);
	
	        if (collisionON) {
	            direction = random.nextInt(4);
	            collisionON = false;
	        } else {
	            moveWithinBounds();
	        }
	    }
	
	    private void moveWithinBounds() {
	        switch (direction) {
	            case 0:
	                if (worldy + speed >= 0) {
	                    worldy += speed;
	                }
	                break;
	            case 1:
	                if (worldy - speed >= 0) {
	                    worldy -= speed;
	                }
	                break;
	            case 2:
	                if (worldx - speed >= 0) {
	                    worldx -= speed;
	                }
	                break;
	            case 3:
	                if (worldx + speed >= 0) {
	                    worldx += speed;
	                }
	                break;
	        }
	    }
	
	    @Override
	    public void draw(Graphics2D g2, Play play) {
	        int screenX = (int) (worldx - play.player.getWorldX() + play.player.getScreenX());
	        int screenY = (int) (worldy - play.player.getWorldY() + play.player.getScreenY());
	
	        // Draw health bar if the orc is not dead
	        if (!dead) {
	            drawHealthBar(g2, screenX + 20, screenY);
	        }
	
	        // Draw the appropriate animation based on the current state
	        if (dead) {
	            // Ensure death animation plays only once
	            if (!deathAnimationFinished) {
	                if (direction < 4 && animationFrame < deathM[direction].length) {
	                    g2.drawImage(deathM[direction][animationFrame], screenX, screenY, 96, 96, null);
	                }
	            }
	
	            // Stop the death animation from repeating
	            frameTimer++;
	            if (frameTimer > 10) {
	                frameTimer = 0;
	                animationFrame++;
	                if (animationFrame >= deathM[direction].length) {
	                    deathAnimationFinished = true;  // Mark death animation as finished
	                    animationFrame = deathM[direction].length - 1;  // Hold the last death frame
	                }
	            }
	
	        } else if (isAttacking) {
	            if (direction < 4 && animationFrame < attackM[direction].length) {
	                g2.drawImage(attackM[direction][animationFrame], screenX, screenY, 96, 96, null);
	            }
	        } else if (isIdle) {
	            if (direction < 4 && animationFrame < idle[direction].length) {
	                g2.drawImage(idle[direction][animationFrame], screenX, screenY, 96, 96, null);
	            }
	        } else {
	            if (direction < 4 && animationFrame < walkM[direction].length) {
	                g2.drawImage(walkM[direction][animationFrame], screenX, screenY, 96, 96, null);
	            }
	        }
	
	        // Increment frame for animation based on the current state
	        if (!dead) {
	            frameTimer++;
	            if (frameTimer > 10) {
	                frameTimer = 0;
	                animationFrame++;
	                if (isAttacking && animationFrame >= attackM[direction].length) {
	                    animationFrame = 0;
	                } else if (isIdle && animationFrame >= idle[direction].length) {
	                    animationFrame = 0;
	                } else if (!isAttacking && !isIdle && animationFrame >= walkM[direction].length) {
	                    animationFrame = 0;
	                }
	            }
	        }
	    }
	
	
	    private void drawHealthBar(Graphics2D g2, int screenX, int screenY) {
	        // Calculate the health bar width based on the current health
	        float healthPercentage = (float) health / 20; // Assuming max health is 20
	        int healthBarCurrentWidth = (int) (HEALTH_BAR_WIDTH * healthPercentage);
	
	        // Draw background (red for missing health)
	        g2.setColor(Color.RED);
	        g2.fillRect(screenX, screenY + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
	
	        // Draw the current health (green)
	        g2.setColor(Color.green);
	        g2.fillRect(screenX, screenY + HEALTH_BAR_OFFSET_Y, healthBarCurrentWidth, HEALTH_BAR_HEIGHT);
	
	        // Optionally draw a border around the health bar (white)
	        g2.setColor(Color.WHITE);
	        g2.drawRect(screenX, screenY + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
	    }
	
	
	    private void loadAnimation() {
	        InputStream is = getClass().getResourceAsStream("/orc2_walk_full.png");
	        InputStream is2 = getClass().getResourceAsStream("/orc2_attack_full.png");
	        InputStream is3 = getClass().getResourceAsStream("/orc2_death_full.png");
	        InputStream is4 = getClass().getResourceAsStream("/orc2_idle_full.png");
	
	        try {
	            BufferedImage runningimg = ImageIO.read(is);
	            walkM = new BufferedImage[4][6];
	            for (int j = 0; j < walkM.length; j++) {
	                for (int i = 0; i < walkM[j].length; i++) {
	                    walkM[j][i] = runningimg.getSubimage(i * 64, j * 64, 64, 64);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Failed to load enemy orc sprite sheet");
	            e.printStackTrace();
	        } finally {
	            try {
	                if (is != null) {
	                    is.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	
	        try {
	            BufferedImage attackimg = ImageIO.read(is2);
	            attackM = new BufferedImage[4][8];
	            for (int j = 0; j < attackM.length; j++) {
	                for (int i = 0; i < attackM[j].length; i++) {
	                    attackM[j][i] = attackimg.getSubimage(i * 64, j * 64, 64, 64);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Failed to load enemy orc attack sprite sheet");
	            e.printStackTrace();
	        } finally {
	            try {
	                if (is2 != null) {
	                    is2.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	
	        try {
	            BufferedImage deathimg = ImageIO.read(is3);
	            deathM = new BufferedImage[4][8];
	            for (int j = 0; j < deathM.length; j++) {
	                for (int i = 0; i < deathM[j].length; i++) {
	                    deathM[j][i] = deathimg.getSubimage(i * 64, j * 64, 64, 64);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Failed to load enemy orc death sprite sheet");
	            e.printStackTrace();
	        } finally {
	            try {
	                if (is3 != null) {
	                    is3.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        try {
	            BufferedImage deathimg = ImageIO.read(is4);
	            idle = new BufferedImage[4][4];
	            for (int j = 0; j < idle.length; j++) {
	                for (int i = 0; i < idle[j].length; i++) {
	                    idle[j][i] = deathimg.getSubimage(i * 64, j * 64, 64, 64);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (is4 != null) {
	                    is4.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	    private void updateAttackHitbox() {
	        attackHitbox.setBounds((int) worldx, (int) worldy, 48, 48);
	    }
	
	//    private boolean isPlayerInRange() {
	//        int distanceX = (int) Math.abs(play.player.getWorldX() - this.worldx);
	//        int distanceY = (int) Math.abs(play.player.getWorldY() - this.worldy);
	//        int chaseRange = 200; // Example range in pixels
	//        return distanceX <= chaseRange && distanceY <= chaseRange;
	//    }
	
	    private boolean isPlayerInAttackRange() {
	        int distanceX = (int) Math.abs(play.player.getWorldX() - this.worldx);
	        int distanceY = (int) Math.abs(play.player.getWorldY() - this.worldy);
	        return distanceX <= ATTACK_RANGE && distanceY <= ATTACK_RANGE;
	    }
	
	    private void attackPlayer() {
	        int playerX = (int) play.player.getWorldX();
	        int playerY = (int) play.player.getWorldY();
	
	        double distanceToPlayer = Math.sqrt(Math.pow(worldx - playerX, 2) + Math.pow(worldy - playerY, 2));
	
	        if (distanceToPlayer <= ATTACK_RANGE) {
	            play.player.takeDamage(1);
	        }
	    }
	
	    private void chasePlayer() {
	        int deltaX = (int) (play.player.getWorldX() - this.worldx);
	        int deltaY = (int) (play.player.getWorldY() - this.worldy);
	
	        boolean movingLeft = deltaX < 0;
	        boolean movingRight = deltaX > 0;
	        boolean movingUp = deltaY < 0;
	        boolean movingDown = deltaY > 0;
	
	        // Adjust speed if the player holds a gun
	        if (play.player.gun) {
	            speed += 0.2f;  // Increase speed gradually when player holds a gun
	            speed = Math.min(speed, 0.8f);  // Cap the max speed at 2.0f
	        } else {
	            speed = 0.5f;  // Default speed when player doesn't hold a gun
	        }
	
	        // Perform collision checks
	        collisionCheck.checkTile(this, movingLeft, movingRight, movingUp, movingDown, speed);
	
	        if (!collisionON) {
	            // Move the orc if there's no collision
	            if (deltaY != 0) {
	                if (deltaY > 0) {
	                    direction = 0; // Down
	                    if (worldy + speed <= play.player.getWorldY()) {
	                        worldy += speed;
	                    } 
	                } else {
	                    direction = 1; // Up
	                    if (worldy - speed >= play.player.getWorldY()) {
	                        worldy -= speed;
	                    }
	                }
	            }
	            if (deltaX != 0) {
	                if (deltaX > 0) {
	                    direction = 3; // Right
	                    if (worldx + speed <= play.player.getWorldX()) {
	                        worldx += speed;
	                    }
	                } else {
	                    direction = 2; // Left
	                    if (worldx - speed >= play.player.getWorldX()) {
	                        worldx -= speed;
	                    }
	                }
	            }
	        } else {
	            // Handle collision: stop movement or adjust path 
	       	isIdle = true;
	            collisionON = false;  // Reset the collision flag for next checks
	        }
	    }
	
	
	   
	
	    private boolean isPlayerInLineOfSight() {
	        int playerX = (int) play.player.getWorldX();
	        int playerY = (int) play.player.getWorldY();
	
	        int orcX = (int) this.worldx;
	        int orcY = (int) this.worldy;
	
	        // Calculate the direction towards the player
	        int deltaX = playerX - orcX;
	        int deltaY = playerY - orcY;
	
	        // Normalize the direction (unit step direction)
	        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	        float stepX = deltaX / distance;
	        float stepY = deltaY / distance;
	
	        // Step through each tile in the direction towards the player to check for obstacles
	        int steps = (int) distance / 32;  // Assuming each tile is 32x32
	        for (int i = 1; i <= steps; i++) {
	            int checkX = orcX + (int) (stepX * i * 32);
	            int checkY = orcY + (int) (stepY * i * 32);
	
	            if (collisionCheck.isTileBlocking(checkX, checkY)) {
	                return false;  // An obstacle blocks the line of sight
	            }
	        }
	
	        // No obstacles were found, so the player is visible
	        return true;
	    }
	
		public int getX() {
			// TODO Auto-generated method stub
			return (int) worldx;
		}
		public int getY() {
			// TODO Auto-generated method stub
			return (int) worldy;
		}
		public boolean isAlive() {
			if (health <= 0) {
				return false;
			}
			return true;
		}
	}
