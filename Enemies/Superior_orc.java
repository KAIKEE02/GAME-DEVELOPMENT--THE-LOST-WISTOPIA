package Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;

import Entities.Entity;
import Gamestates.Play;
import Main.CollisionCheck;
import objects.obj_BlueHeart;
import objects.obj_Heart;
import objects.obj_soulOrb;
import objects.superObj;

public class Superior_orc extends Entity {


    private BufferedImage img;
    private float speed;
    private Random random;
    private BufferedImage[][] walkM, attackM, idle;
	public BufferedImage[][] deathM;
    
    public int animationFrame = 0;
    private int frameTimer = 0;
    public int direction = 0;
    private CollisionCheck collisionCheck;
    private Play play;
    private int health;
    private boolean dead = false;
    private boolean deathAnimationFinished = false;
    public Rectangle attackHitbox;
    private boolean isAttacking = false;
    private static final int ATTACK_RANGE = 48;
    private static final int HEALTH_BAR_WIDTH = 50;
    private static final int HEALTH_BAR_HEIGHT = 5;
    private static final int HEALTH_BAR_OFFSET_Y = -10;
    private boolean isAlive = true;   
    // New fields for healing and intelligence
    private int maxHealth = 50;
    private boolean isRetreating = false; // New defensive behavior flag
    private boolean isIdle = false;  // New field to track idle state
    private int idleTimer = 0;       // Timer for idle duration
    private final int MAX_IDLE_TIME = 190;  // Duration of idle (e.g., 190 frames)

    public Superior_orc(int x, int y, BufferedImage img, CollisionCheck collisionCheck, Play play) {
        super(x, y);
        this.img = img;
        this.speed = 0.5f;
        this.solidArea = new Rectangle(38, 38, 25, 16);
        this.random = new Random();
        this.collisionCheck = collisionCheck;
        this.play = play;
        this.attackHitbox = new Rectangle(x, y, 32, 32);
        this.health = 50;
        loadAnimation();
    }

    private void playDeathAnimation() {
        if (direction < 4 && animationFrame < deathM[direction].length) {
            // Death animation logic
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
            	die();
                isAlive = false;
                
            }
        }
    }

    public void die() {
        dead = true;
        play.playSE(12);
        isAttacking = false;
        animationFrame = 0;
        play.player.invihash += 1;
        dropItem();
    }

    public void dropItem() {
        int randomItem = random.nextInt(3); // Randomly select an item
        superObj droppedItem = null;
        superObj defaultitemdrop = new obj_soulOrb();

        switch (randomItem) {
            case 0:
                droppedItem = new obj_Heart();
                break;
            case 1:
                droppedItem = new obj_soulOrb();
                break;
            case 2:
                droppedItem = new obj_BlueHeart();
                break;
        }

        if (droppedItem != null) {
            droppedItem.worldx = (int) this.worldx;
            droppedItem.worldy = (int) this.worldy;
            for (int i = 0; i < play.obj.length; i++) {
                if (play.obj[i] == null) {
                    play.obj[i] = droppedItem;
                    break;
                }
            }
        }
        if (defaultitemdrop != null) {
            defaultitemdrop.worldx = (int) this.worldx + 20;
            defaultitemdrop.worldy = (int) this.worldy + 20;
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

        // Check if the orc should pause and idle
        if (!isAttacking && !isPlayerInLineOfSight() && !isIdle && random.nextInt(200) < 1) {  // 5% chance to idle if not already idle
            isIdle = true;
            idleTimer = MAX_IDLE_TIME;
        }

        // Handle the idle timer
        if (isIdle) {
            idleTimer--;
            if (idleTimer <= 0) {
                isIdle = false; // Exit idle state when the timer ends
            } else {
                return;  // Stay idle while the timer is active
            }
        }

        // After idle ends or if not idle, proceed with regular behavior
        if (health <= maxHealth * 0.25) {  // Less than 25% health triggers retreat
            isRetreating = true;
            speed += 0.2f;
            speed = Math.min(speed, 1.4f);  // Cap the max speed
        } else {
            isRetreating = false;
        }

        // Behavior if retreating
        if (isRetreating) {
            isAttacking = false;
            randomMovement();
        } else if (isPlayerInLineOfSight()) {
            if (isPlayerInAttackRange()) {
                isAttacking = true;
                attackPlayer();  // Attack and heal on hit
            } else {
                isAttacking = false;
                chasePlayer();  // Intelligent chasing
            }
        } else {
            isAttacking = false;
            randomMovement();  // Reverts to random movement if player isn't visible
        }

        updateAttackHitbox();
    }

    

    private void randomMovement() {
        boolean movingLeft = direction == 2;
        boolean movingRight = direction == 3;
        boolean movingUp = direction == 1;
        boolean movingDown = direction == 0;

        collisionCheck.checkTile(this, movingLeft, movingRight, movingUp, movingDown, speed);
        collisionCheck.checkNpc(this,true, movingLeft, movingRight, movingUp, movingDown, speed);

        if (collisionON) {
            direction = random.nextInt(4);
            collisionON = false;
        } else {
            moveWithinBounds();
        }
    }

    private void moveWithinBounds() {
        switch (direction) {
            case 0: // Move down
                if (worldy + speed >= 0) {
                    worldy += speed;
                }
                break;
            case 1: // Move up
                if (worldy - speed >= 0) {
                    worldy -= speed;
                }
                break;
            case 2: // Move left
                if (worldx - speed >= 0) {
                    worldx -= speed;
                }
                break;
            case 3: // Move right
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
//        g2.setColor(Color.RED);  // Set color to easily identify the collision box
//    g2.drawRect((int) screenX + solidArea.x  , (int) screenY + solidArea.y , solidArea.width, solidArea.height);
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
        float healthPercentage = (float) health / 50;
        int healthBarCurrentWidth = (int) (HEALTH_BAR_WIDTH * healthPercentage);

        g2.setColor(Color.RED);
        g2.fillRect(screenX, screenY + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);

        g2.setColor(Color.MAGENTA);
        g2.fillRect(screenX, screenY + HEALTH_BAR_OFFSET_Y, healthBarCurrentWidth, HEALTH_BAR_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.drawRect(screenX, screenY + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
    }

    private void loadAnimation() {
        InputStream is = getClass().getResourceAsStream("/orc3_walk_full.png");
        InputStream is2 = getClass().getResourceAsStream("/orc3_attack_full.png");
        InputStream is3 = getClass().getResourceAsStream("/orc3_death_full.png");
        InputStream is4 = getClass().getResourceAsStream("/orc3_idle_full.png");

        try {
            BufferedImage runningimg = ImageIO.read(is);
            walkM = new BufferedImage[4][6];
            for (int j = 0; j < walkM.length; j++) {
                for (int i = 0; i < walkM[j].length; i++) {
                    walkM[j][i] = runningimg.getSubimage(i * 64, j * 64, 64, 64);
                }
            }
        } catch (IOException e) {
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

    private void attackPlayer() {
        int playerX = (int) play.player.getWorldX();
        int playerY = (int) play.player.getWorldY();

        double distanceToPlayer = Math.sqrt(Math.pow(worldx - playerX, 2) + Math.pow(worldy - playerY, 2));

        if (distanceToPlayer <= ATTACK_RANGE) {
            // Deal damage to the player
            play.player.takeDamage(2);
            
            // Heal the orc when it hits the player
            heal(2);  // Heals 5 health points on hit (adjust this value as needed)
        }
    }
    private void heal(int amount) {
        if (!dead) {
            health = Math.min(health + amount, maxHealth);  // Ensure health does not exceed maxHealth
        }
    }

    private void updateAttackHitbox() {
        attackHitbox.setLocation((int) worldx, (int) worldy);
    }

    private boolean isPlayerInRange() {
        int distanceX = (int) Math.abs(play.player.getWorldX() - this.worldx);
        int distanceY = (int) Math.abs(play.player.getWorldY() - this.worldy);
        int chaseRange = 200; // Example range in pixels
        return distanceX <= chaseRange && distanceY <= chaseRange;
    }

    private boolean isPlayerInAttackRange() {
        int distanceX = (int) Math.abs(play.player.getWorldX() - this.worldx);
        int distanceY = (int) Math.abs(play.player.getWorldY() - this.worldy);
        return distanceX <= ATTACK_RANGE && distanceY <= ATTACK_RANGE;
    }

  

    /**
     * Checks if the player is visible and there are no obstacles between the orc and the player.
     * Uses a simple line-of-sight check by iterating through the tiles between the orc and the player.
     */
    private boolean isPlayerInLineOfSight() {
        int playerX = (int) play.player.getWorldX() ;
        int playerY = (int) play.player.getWorldY() ;

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
        int steps = (int) distance / 48;  // Assuming each tile is 32x32
        for (int i = 1; i <= steps; i++) {
            int checkX = orcX + (int) (stepX * i * 48);
            int checkY = orcY + (int) (stepY * i * 48);

            if (collisionCheck.isTileBlocking(checkX, checkY)) {
                return false;  // An obstacle blocks the line of sight
            }
        }

        // No obstacles were found, so the player is visible
        return true;
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
            speed = Math.min(speed, 0.8f);  // Cap the max speed at 0.8
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
