package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.CollisionCheck;
import Gamestates.Play;

public class choppie extends Entity {

    private BufferedImage img; 
    private float speed; 
    private BufferedImage[][] walkM, attackM, idle; 
    public int animationFrame = 0; 
    private int frameTimer = 0; 
    public int direction = 0; 
    private CollisionCheck collisionCheck;
    private Play play;
    private int health;
    private boolean dead = false;
    public boolean deathAnimationFinished = false;
    public Rectangle attackHitbox;
    public boolean isAttacking = false;
    private static final int ATTACK_RANGE = 48;
    private static final int HEALTH_BAR_WIDTH = 50;
    private static final int HEALTH_BAR_HEIGHT = 5;
    private static final int HEALTH_BAR_OFFSET_Y = -10;
    private boolean isAlive = true;
    private boolean isIdle = false;  
    private int idleTimer = 0;
    private final int MAX_IDLE_TIME = 190;
    private int attackAnimationFrame = 0;
    private int attackCooldownTimer = 0;
    private static final int ATTACK_COOLDOWN = 120; // Frames for 2 seconds cooldown assuming 60 FPS
    private boolean attackOnCooldown = false;
    private Random random = new Random();
    private int followDelayTimer = 0;
    private int maxFollowDelay = 60; // Max delay before moving again (adjust as needed)
    private int stuckTimer = 0;
    private static final int MAX_STUCK_TIME = 120; // Adjust this as needed, 180 frames = 3 seconds at 60 FPS

    

    private Entity player; // Reference to the player character
	public Rectangle weaponHitbox;

    public choppie(int x, int y,  CollisionCheck collisionCheck, Play play, Entity player) {
        super(x, y);
      
        this.speed = 0.9f; 
        this.solidArea = new Rectangle(48, 60, 25, 16);
        this.collisionCheck = collisionCheck;
        this.play = play;	
        this.attackHitbox = new Rectangle(x, y, 48, 48);
        this.health = 20;
        this.player = player; // Initialize the player reference
        loadAnimation();
        weaponHitbox = new Rectangle();  // Example size
        weaponHitbox.x = 25;
		weaponHitbox.y = 29;
		weaponHitbox.width = 90;
		weaponHitbox.height = 90;
    }
    private void loadAnimation() {
        try {
            InputStream is = getClass().getResourceAsStream("/MiniWolf_run.png");
            BufferedImage runningimg = ImageIO.read(is);
            walkM = new BufferedImage[2][6];
            for (int j = 0; j < walkM.length; j++) {
                for (int i = 0; i < walkM[j].length; i++) {
                    walkM[j][i] = runningimg.getSubimage(i * 32, j * 32, 32, 32);
                }
            }
            is.close();

            InputStream is2 = getClass().getResourceAsStream("/MiniWolf_Attack.png");
            BufferedImage attackimg = ImageIO.read(is2);
            attackM = new BufferedImage[2][6];
            for (int j = 0; j < attackM.length; j++) {
                for (int i = 0; i < attackM[j].length; i++) {
                    attackM[j][i] = attackimg.getSubimage(i * 32, j * 32, 32, 32);
                }
            }
            is2.close();

            InputStream is4 = getClass().getResourceAsStream("/MiniWolf_idle.png");
            BufferedImage idleimg = ImageIO.read(is4);
            idle = new BufferedImage[2][4];
            for (int j = 0; j < idle.length; j++) {
                for (int i = 0; i < idle[j].length; i++) {
                    idle[j][i] = idleimg.getSubimage(i * 32, j * 32, 32, 32);
                }
            }
            is4.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // Check if the companion is attacking or not
        if (!attackOnCooldown) {
            if (isEnemyInRange()) {
                attack();
            } else {
                followPlayer();
            }
        } else {
            attackCooldownTimer++;
            if (attackCooldownTimer >= ATTACK_COOLDOWN) {
                attackOnCooldown = false;
                attackCooldownTimer = 0;
            }
        }

        // Update the attack hitbox
        attackHitbox.setLocation((int) worldx, (int) worldy);

        // Animation frame update
        updateAnimation();
    }

  

    private void followPlayer() {
        int deltaX = (int) (play.player.getWorldX() - this.worldx);
        int deltaY = (int) (play.player.getWorldY() - this.worldy);

        boolean movingLeft = deltaX < 0;
        boolean movingRight = deltaX > 0;
        boolean movingUp = deltaY < 0;
        boolean movingDown = deltaY > 0;

        if (play.player.runn) {
            speed = 1.5f;
        } else {
            speed = 0.9f;
        }

        int followDistance = 30; // Distance buffer between Choppie and player

        if (isIdle) {
            idleTimer++;
            if (idleTimer >= MAX_IDLE_TIME) {
                isIdle = false;
                idleTimer = 0;
            }
            return;
        }

        if (Math.abs(deltaX) <= followDistance && Math.abs(deltaY) <= followDistance) {
            isIdle = true;
            return;
        }

        followDelayTimer++;
        if (followDelayTimer >= maxFollowDelay) {
            if (random.nextInt(100) < 10) {
                isIdle = true;
                followDelayTimer = 0;
                maxFollowDelay = 60 + random.nextInt(120);
                return;
            }
            followDelayTimer = 0;
        }

        collisionCheck.checkTile(this, movingLeft, movingRight, movingUp, movingDown, speed);

        if (!collisionON) {
            stuckTimer = 0;  // Reset stuck timer when Choppie moves

            if (deltaY != 0) {
                if (deltaY > 0) {
                    direction = 0; // Moving down
                    worldy += Math.min(speed, deltaY);
                } else {
                    direction = 1; // Moving up
                    worldy -= Math.min(speed, -deltaY);
                }
            }

            if (deltaX != 0) {
                if (deltaX > 0) {
                    direction = 0; // Moving right
                    worldx += Math.min(speed, deltaX);
                } else {
                    direction = 1; // Moving left
                    worldx -= Math.min(speed, -deltaX);
                }
            }

        } else {
            stuckTimer++;
            if (stuckTimer >= MAX_STUCK_TIME) {
                // Teleport Choppie to the player if stuck for too long
                teleportToPlayer();
                stuckTimer = 0; // Reset the stuck timer after teleporting
            }
            adjustPath(movingLeft, movingRight, movingUp, movingDown);
            collisionON = false;
        }
    }
    private void teleportToPlayer() {
        // Set Choppie's position to the player's position
        this.worldx = play.player.getWorldX();
        this.worldy = play.player.getWorldY();
    }


    /**
     * Adjusts Choppie's movement if a collision is detected, attempting to move
     * in a different direction to avoid getting stuck.
     */
    private void adjustPath(boolean movingLeft, boolean movingRight, boolean movingUp, boolean movingDown) {
        // Try to move sideways or diagonally to get around obstacles
        if (movingLeft || movingRight) {
            if (!movingUp) {
                worldy += speed; // Try to move down to avoid the obstacle
            } else if (!movingDown) {
                worldy -= speed; // Try to move up to avoid the obstacle
            }
        } else if (movingUp || movingDown) {
            if (!movingLeft) {
                worldx += speed; // Try to move right to avoid the obstacle
            } else if (!movingRight) {
                worldx -= speed; // Try to move left to avoid the obstacle
            }
        }

        // If all directions are blocked, attempt random small movements to "shake" out
        if (collisionON) {
            worldx += random.nextInt(5) - 2; // Random small movement in X direction
            worldy += random.nextInt(5) - 2; // Random small movement in Y direction
        }
    }




    private boolean isEnemyInRange() {
        // Check for enemies in the enemy array
        for (int i = 0; i < play.enemies.length; i++) {
            if (play.enemies[i] != null && play.enemies[i].isAlive()) {  // Add check if enemy is alive
                double distance = Math.hypot(play.enemies[i].getX() - this.worldx, play.enemies[i].getY() - this.worldy);
                if (distance < ATTACK_RANGE) {
                    return true;
                }
            }
        }

        // Check for superior enemies
        for (int i = 0; i < play.superior.length; i++) {
            if (play.superior[i] != null && play.superior[i].isAlive()) {  // Add check if superior enemy is alive
                double distance = Math.hypot(play.superior[i].getX() - this.worldx, play.superior[i].getY() - this.worldy);
                if (distance < ATTACK_RANGE) {
                    return true;
                }
            }
        }

        return false;
    }


    private void attack() {
        // Make sure an enemy is in range and still alive
        if (isEnemyInRange()) {
            isAttacking = true;
            attackOnCooldown = true;
            attackAnimationFrame = 0;
            // Implement enemy damage logic here, ensuring that the enemy's health is reduced.
        } else {
            isAttacking = false; // Stop attacking if no enemies are in range or alive
        }
    }


    private void updateAnimation() {
        if (isAttacking) {
            // Advance attack animation
            attackAnimationFrame++;
            if (attackAnimationFrame >= attackM[0].length) {
                isAttacking = false;
                attackAnimationFrame = 0;
            }
        } else if (isIdle) {
            // Handle idle animation
            frameTimer++;
            if (frameTimer >= 15) { // Adjust timer for smooth idle animation
                animationFrame = (animationFrame + 1) % idle[0].length;
                frameTimer = 0;
            }
        } else {
            // Walking animation
            frameTimer++;
            if (frameTimer >= 10) {
                animationFrame = (animationFrame + 1) % walkM[0].length;
                frameTimer = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = (int) (worldx - play.player.getWorldX() + play.player.getScreenX());
        int screenY = (int) (worldy - play.player.getWorldY() + play.player.getScreenY());

        // Ensure direction is within bounds of 0 or 1 (since you have two directions)
        direction = Math.max(0, Math.min(direction, 1));

        // Determine which animation to draw
        if (isAttacking) {
            // Attack animation with bounds check
            attackAnimationFrame = Math.max(0, Math.min(attackAnimationFrame, attackM[direction].length - 1));
            image = attackM[direction][attackAnimationFrame];
        } else if (isIdle) {
            // Idle animation with bounds check
            animationFrame = Math.max(0, Math.min(animationFrame, idle[direction].length - 1));
            image = idle[direction][animationFrame];
        } else {
            // Walking animation with bounds check
            animationFrame = Math.max(0, Math.min(animationFrame, walkM[direction].length - 1));
            image = walkM[direction][animationFrame];
        }

        // Draw Choppie
        g2.drawImage(image, screenX, screenY, 110, 70, null);

//        // Draw the collision hitbox (solid area) for debugging purposes
//        g2.setColor(new java.awt.Color(255, 0, 0, 100)); // Red with transparency
//        g2.fillRect(
//            (int) (screenX + solidArea.x), 
//            (int) (screenY + solidArea.y), 
//            solidArea.width, 
//            solidArea.height
//        );
//
//        // Draw the weapon hitbox for debugging purposes
//        g2.setColor(new java.awt.Color(0, 0, 255, 100)); // Blue with transparency
//        g2.fillRect(
//            (int) (screenX + weaponHitbox.x), 
//            (int) (screenY + weaponHitbox.y), 
//            weaponHitbox.width, 
//            weaponHitbox.height
//        );
//        
//        // Optionally draw attack hitbox
//        g2.setColor(new java.awt.Color(0, 255, 0, 100)); // Green with transparency
//        g2.fillRect(
//            (int) (screenX + attackHitbox.x), 
//            (int) (screenY + attackHitbox.y), 
//            attackHitbox.width, 
//            attackHitbox.height
//        );
    }


	
}