package Main;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Entities.Player;
import Gamestates.Play;
import Gamestates.gamestates;
import Gamestates.menu;

public class MAINGAME implements Runnable {
    private GAME game;
    private Gamescreen gamescreen;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Play play;
    private menu menu;
    private Player player;
    private DatabaseManager dbManager;  // Add the DatabaseManager instance

    // screen SETTINGS
    public static final int TILES_DEF_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_SIZE = (int) (TILES_DEF_SIZE * SCALE);
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    // WORLD SETTINGS
    public static final int maxWorldCol = 250;
    public static final int maxWorldRow = 250;
    public static final int worldWidth = TILES_SIZE * maxWorldCol;
    public static final int worldHeight = TILES_SIZE * maxWorldRow;

    public MAINGAME() {
        
        initClasses();

        gamescreen = new Gamescreen(this, player); // Pass player to Gamescreen
        game = new GAME(gamescreen);
        game.setMainGame(this);
        gamescreen.requestFocus();
        play.setupGame();
        startGameLoop();

        // Key listener to save and load data
        
    }
    private void initClasses() {

        menu = new menu(this);
        play = new Play(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (gamestates.state) {
            case MENU:
                menu.update();
                break;
            case PLAY:
                play.update();
                play.gameon = true;
                break;
            case OPTIONS:
            case QUIT:
            default:
            	System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        switch (gamestates.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAY:
                play.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
            	update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamescreen.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public void windowsLostFocus() {
        if (gamestates.state == gamestates.PLAY) {
            play.getPlayer().resetBoolean();
        }
    }

    public menu getMenu() {
        return menu;
    }

    public Play getPlaying() {
        return play;
    }
}
