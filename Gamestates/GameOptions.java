package Gamestates;

import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.MAINGAME;
import ui.PauseButton;


public class GameOptions extends state implements Statemethods {

//	private AudioOptions audioOptions;
	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
//	private UrmButton menuB;

	public GameOptions(MAINGAME game) {
		super(game);
		loadImgs();
		loadButton();
//		audioOptions = game.getAudioOptions();
	}

	private void loadButton() {
		int menuX = (int) (387 * MAINGAME.SCALE);
		int menuY = (int) (325 * MAINGAME.SCALE);

//		menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
	}

	private void loadImgs() {
		

		bgW = (int) (optionsBackgroundImg.getWidth() * MAINGAME.SCALE);
		bgH = (int) (optionsBackgroundImg.getHeight() * MAINGAME.SCALE);
		bgX = MAINGAME.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * MAINGAME.SCALE);
	}

	@Override
	public void update() {
//		menuB.update();
//		audioOptions.update();

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, MAINGAME.GAME_WIDTH, MAINGAME.GAME_HEIGHT, null);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

//		menuB.draw(g);
//		audioOptions.draw(g);

	}

	public void mouseDragged(MouseEvent e) {
//		audioOptions.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		if (isIn(e, menuB)) {
//			menuB.setMousePressed(true);
//		} else
//			audioOptions.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		if (isIn(e, menuB)) {
//			if (menuB.isMousePressed())
//				Gamestate.state = Gamestate.MENU;
//		} else
//			audioOptions.mouseReleased(e);

//		menuB.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		menuB.setMouseOver(false);

//		if (isIn(e, menuB))
//			menuB.setMouseOver(true);
//		else
//			audioOptions.mouseMoved(e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			gamestates.state = gamestates.MENU;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
