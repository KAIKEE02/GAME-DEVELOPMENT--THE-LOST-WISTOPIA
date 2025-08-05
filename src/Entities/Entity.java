package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Gamestates.Play;

public abstract class Entity {
    public float worldx, worldy;
    public Rectangle solidArea;
    public boolean collisionON = false;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // Character status
    public int maxLife;
    public int life; 
    public int defense; // New defense attribute
    public int maxDef; // New defense attribute
    public BufferedImage[] animated; // Animation frames

    public Entity(float worldx, float worldy) {
        this.worldx = worldx;
        this.worldy = worldy;
        this.solidArea = new Rectangle(); // Initialize solidArea
    }

    public Entity(Play play) {
        animated = new BufferedImage[5];
        this.solidArea = new Rectangle(); // Initialize solidArea here too
    }

    public void draw(Graphics2D g2, Play play) {
        if (animated != null && animated.length > 0) {
            g2.drawImage(animated[0], (int) worldx, (int) worldy, null); // Draw first frame
        } else {
            System.out.println("No animation frames to draw for NPC."); // Debugging
        }
    }

    // Add update method here
    public abstract void update();
}
