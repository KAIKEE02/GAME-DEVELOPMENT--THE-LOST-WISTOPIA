	package Main;

import java.awt.Image;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;


public class GAME extends JFrame {
    MAINGAME gMaingame; // Reference to MAINGAME, not Play directly
    
    public GAME(Gamescreen gamescreen) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("The Lost Wistopia");
        Image img = null;
        setIconImage(img);
        add(gamescreen);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("byeee");
                if (gMaingame != null && gMaingame.getPlaying().getPlayer() != null) {
                    gMaingame.windowsLostFocus();  
                }
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                
            }
        });
        
    }

    public void setMainGame(MAINGAME gMaingame) {
        this.gMaingame = gMaingame; 
    }
}
