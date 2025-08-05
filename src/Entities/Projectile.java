package Entities;

import static Main.MAINGAME.TILES_SIZE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Gamestates.Play;
import Main.MAINGAME;

public class Projectile {
    public float x, y;       // Position in the world
    public float speed;      // Speed
    public int width, height;
    public float directionX, directionY; // Direction (normalized vector)
    public boolean active;   // Whether the projectile is active
    public Rectangle hitbox; // Collision box
    public int worldx, worldy;
	public Player player;

    public Projectile(float x, float y, float directionX, float directionY, float speed) {
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = speed;
        this.width = 5;  // Example width
        this.height = 5; // Example height
        this.active = true;
        this.hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    public void update() {
        // Move projectile based on direction and speed
        x += directionX * speed;
        y += directionY * speed;

        // Update hitbox position
        hitbox.x = (int) x;
        hitbox.y = (int) y;

        // Debug log to check if the projectile is moving
        System.out.println("Projectile Position: x=" + x + ", y=" + y);

        // Deactivate if out of bounds
        int worldWidth = MAINGAME.worldWidth; // Replace with your actual world width
        int worldHeight = MAINGAME.worldHeight; // Replace with your actual world height
        if (x < -width || y < -height || x > worldWidth || y > worldHeight) {
            active = false;
            System.out.println("Projectile out of bounds, deactivating.");
        }
    }

    public void draw(Graphics2D g2, Play play, Player player) {
        if (active) {
            // Adjust screen coordinates based on the world-to-screen transformation
        this.player = player;
            int screenX = (int)Math.floor(x - player.getWorldX() + player.getScreenX());
    	    int screenY = (int)Math.floor(y - player.getWorldY() + player.getScreenY());

            // Debug log to check if the projectile is correctly positioned on screen
            System.out.println("Drawing projectile at screen coordinates: x=" + screenX + ", y=" + screenY);

            // Set the color for the projectile
            g2.setColor(Color.YELLOW);

            // Draw the projectile
            g2.fillRect(screenX, screenY, width, height);
       
        }
    }
}
