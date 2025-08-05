package Gamestates;

import java.awt.event.MouseEvent;

import Main.MAINGAME;
import ui.MenuButton;

public class state {
	
	protected MAINGAME game;
	public state(MAINGAME game) {
		this.game = game;
		
	}
	
	public boolean isIn(MouseEvent e,MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
		
	}
	
	public MAINGAME getGame(){
		return game;
	}  

}
