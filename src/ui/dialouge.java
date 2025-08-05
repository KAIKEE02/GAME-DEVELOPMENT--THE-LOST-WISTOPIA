package ui;

import static Main.MAINGAME.TILES_SIZE;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Gamestates.Play;
import Gamestates.gamestates;
import Main.MAINGAME;
import Main.UtilityTool;

public class dialouge {
    private Play playing;
    public BufferedImage img;
    public UtilityTool uTool = new UtilityTool();
    private Font mincraft;
    private long startTime;   // Track time for text reveal
    private int charIndex = 0;  // Index of the character currently being displayed
    private int revealSpeed = 20;  // Delay between characters (milliseconds)

    public dialouge(Play playing) {
        this.playing = playing;
        try {
            img = ImageIO.read(getClass().getResource("/Dialougeplate2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = getClass().getResourceAsStream("/Font/Minecraft.ttf");
        try {
            mincraft = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        img = uTool.scaleImage(img, MAINGAME.GAME_WIDTH, TILES_SIZE);
        startTime = System.currentTimeMillis(); // Initialize the timer
    }

    public void draw(Graphics2D g) {
        g.setFont(mincraft);
        g.setFont(g.getFont().deriveFont(20f));  // Set font size
        
        g.drawImage(img, 0, 500, MAINGAME.GAME_WIDTH, 150, null);  // Dialogue box at the bottom
        
        g.setColor(Color.white);
        
        // Get the current dialogue line
        String currentLine = playing.currentDialogue[playing.dialogueIndex];
        
        // Track how much time has passed to reveal characters
        long elapsedTime = System.currentTimeMillis() - startTime;
        
        // Calculate how many characters to reveal based on elapsed time and reveal speed
        if (elapsedTime > revealSpeed) {
            charIndex++;  // Increment the character index
            startTime = System.currentTimeMillis();  // Reset the timer
        }
        
        // Ensure charIndex doesn't exceed the length of the current line
        charIndex = Math.min(charIndex, currentLine.length());
        
        // Get the substring to display (up to charIndex)
        String visibleText = currentLine.substring(0, charIndex);
        
        // Split the text by lines in case of newline characters
        String[] parts = visibleText.split("\n");
        
        // Start position for rendering text
        int xPosition = 50;
        int yPosition = 550;
        
        // Draw each visible part of the dialogue
        for (String part : parts) {
            g.drawString(part, xPosition, yPosition);
            yPosition += 30;  // Adjust y position for each new line
        }
    }


    // Reset method to restart text reveal when new dialogue starts
    public void startNewDialogue() {
        charIndex = 0;
        startTime = System.currentTimeMillis();  // Reset the timer
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Skip text reveal if it's not fully shown, or proceed to next dialogue
            if (charIndex < playing.currentDialogue[playing.dialogueIndex].length()) {
               charIndex = playing.currentDialogue[playing.dialogueIndex].length();  // Reveal all text
            } else {
            	 startNewDialogue();  // Reset charIndex for new dialogue
                playing.proceedDialogue();  // Proceed to the next dialogue
               
            }
        }
    }
}

