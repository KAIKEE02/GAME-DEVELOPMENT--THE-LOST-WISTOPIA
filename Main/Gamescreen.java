package Main;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Entities.Player;
import Gamestates.Play;
import Inputs.Keyboard;
import Inputs.Mouse;
import Tile.TileManager;

public class Gamescreen extends JPanel {
    private Player player;
    private Mouse mouseInput;
    private TileManager tileManager;
    private MAINGAME game;
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "screendes.png";

    public Gamescreen(MAINGAME game, Player player) {
        this.game = game;
        this.player = player;
        this.mouseInput = new Mouse(this);


        setScreenSize();
        
        addKeyListener(new Keyboard(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    private void setScreenSize() {
        Dimension size = new Dimension(MAINGAME.GAME_WIDTH, MAINGAME.GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : " + MAINGAME.GAME_WIDTH + " : " + MAINGAME.GAME_HEIGHT);
    }

    public static  BufferedImage importImage(String fileName) {
         BufferedImage img = null;
         InputStream is = Gamescreen.class.getResourceAsStream("/" + fileName);
         try {
             img = ImageIO.read(is);
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
			try {
				is.close();
			}
			catch (Exception e) {
				 e.printStackTrace();
			}
		}
         return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        game.render(g);
    }

    public MAINGAME getGame() {
        return game;
    }
}
