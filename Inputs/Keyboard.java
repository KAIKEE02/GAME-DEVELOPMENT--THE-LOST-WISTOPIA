package Inputs;

import java.awt.event.KeyEvent;


import java.awt.event.KeyListener;

import Gamestates.gamestates;
import Main.Gamescreen;
import static utilz.Constants.PlayerConstants.*;



public class Keyboard implements KeyListener  {

	   private Gamescreen gamescreen;
	   public Keyboard(Gamescreen gamescreen) {
		   this.gamescreen = gamescreen; 
	   }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (gamestates.state) {
		case MENU:
			gamescreen.getGame().getMenu().keyPressed(e);
			break;
		case PLAY:
			gamescreen.getGame().getPlaying().keyPressed(e);
			break;
		default:
			break;
		
		}
	

}
	 

	@Override
	public void keyReleased(KeyEvent e) {
		switch (gamestates.state) {
		case MENU:
			gamescreen.getGame().getMenu().keyReleased(e); 
			break;
		case PLAY:
			gamescreen.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		
		}
	
	   
	     
	}

}
