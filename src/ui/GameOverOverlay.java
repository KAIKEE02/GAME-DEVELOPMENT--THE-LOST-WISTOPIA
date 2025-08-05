package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Gamestates.Play;
import Gamestates.gamestates;
import Main.MAINGAME;

public class GameOverOverlay {

		private Play playing;

		public GameOverOverlay(Play playing) {
			this.playing = playing;
		}

		public void draw(Graphics g) {
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, MAINGAME.GAME_WIDTH, MAINGAME.GAME_HEIGHT);

			g.setColor(Color.white);
			g.drawString("YOU DIED! ", MAINGAME.GAME_WIDTH / 2, 150);
			g.drawString("Press esc to enter Main Menu!", MAINGAME.GAME_WIDTH / 2, 300);

		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				playing.resetAll();
				gamestates.state = gamestates.MENU;
				playing.resetAll();
			}
		}
	}


