package Inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Gamestates.Play;
import Gamestates.gamestates;
import Main.Gamescreen;

public class Mouse implements MouseListener, MouseMotionListener {
	public Play play;
	private Gamescreen gamescreen;
	public Mouse(Gamescreen gamescreen ) {
		this.gamescreen = gamescreen;
	
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (gamestates.state) {
		case MENU:
			gamescreen.getGame().getMenu().mouseMoved(e);
			break;
		case PLAY:
			gamescreen.getGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (gamestates.state) {
		
		case PLAY:
			gamescreen.getGame().getPlaying().mouseClicked(e);
			
			break;
		default:
			break;
		
		}
		
		
		
		
	}
 
	@Override
	public void mousePressed(MouseEvent e) {
		switch (gamestates.state) {
		case MENU:
			gamescreen.getGame().getMenu().mousePressed(e);
			break;
		case PLAY:
			gamescreen.getGame().getPlaying().mousePressed(e);
			break;
		default:
			break;
		
		}
	
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (gamestates.state) {
		case MENU:
			gamescreen.getGame().getMenu().mouseReleased(e);
			break;
		case PLAY:
			gamescreen.getGame().getPlaying().mouseReleased(e);
			break;
		default:
			break;
		
		}
	
//		gamescreen.getGame().getPlayer().setAtt(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
