package Gamestates;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import javax.accessibility.AccessibleRelationSet;
import javax.imageio.ImageIO;

import Main.Gamescreen;
import Main.MAINGAME;
import ui.MenuButton;

public class menu extends state implements Statemethods{
	private BufferedImage img;
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg;
	private int menuX, menuY, menuWidth, menuHeight;
	

	public menu(MAINGAME game) {
		super(game);
		loadButtons();
		loadBackground();
	}
	
	private void loadBackground() {
		backgroundImg = Gamescreen.importImage(Gamescreen.MENU_BACKGROUND);
		menuWidth = (int)( backgroundImg.getWidth() * MAINGAME.SCALE);
		menuHeight = (int)( backgroundImg.getHeight() * MAINGAME.SCALE);
		menuX = game.GAME_WIDTH / 2 - menuWidth;
		menuY = (int) (45 * MAINGAME.SCALE);
		
	}

	/*
	 * public void importImage() {
	 * 
	 * InputStream is = Gamescreen.class.getResourceAsStream("/newscreen.png"); try
	 * { img = ImageIO.read(is); } catch (IOException e) { e.printStackTrace(); }
	 * finally { try { is.close(); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * }
	 */

	private void loadButtons() {
		// loading the buttons 
		buttons[0] = new MenuButton(game.GAME_WIDTH / 2,(int) (250 * game.SCALE),0,gamestates.PLAY);
		buttons[1] = new MenuButton(game.GAME_WIDTH / 2,(int) (320 * game.SCALE),1,gamestates.OPTIONS);
		buttons[2] = new MenuButton(game.GAME_WIDTH / 2,(int) (390 * game.SCALE),2,gamestates.QUIT);
	}

	@Override
	public void update() {
		
		for(MenuButton mb : buttons) {
			mb.updated();
		}
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(backgroundImg, 0, 0,game.GAME_WIDTH  ,game.GAME_HEIGHT , null);
		

		
		for(MenuButton mb : buttons) {
			g.drawImage(img, 0, 0,0, 0 ,null);
			mb.draw(g);
		}	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if (isIn(e,mb)) {
			mb.setMousePressed(true);
			break;
		}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
		if (isIn(e, mb)) {
			if(mb.isMousePressed());
			mb.applygamestates();
			
			break;
		
			
		}
		}
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
			for(MenuButton mb : buttons) {
				if(isIn(e, mb)) {
					mb.setMouseOver(true);
					break;
					
				
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
