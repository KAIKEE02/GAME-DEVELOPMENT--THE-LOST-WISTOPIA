package Main;

import java.awt.Rectangle;
import static Main.MAINGAME.TILES_SIZE;
import Gamestates.Play;

public class EventHandler {

    Play screen;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    private long lastHitTime; // To track the last time damage was applied
    private static final long DAMAGE_COOLDOWN = 1000; // 2 seconds in milliseconds
   boolean drink;

    public EventHandler(Play screen) {
        this.screen = screen;
        eventRect = new Rectangle();
        eventRect.x = 0;
        eventRect.y = 0;
        eventRect.width = 48;
        eventRect.height = 48;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
        lastHitTime = System.currentTimeMillis(); // Initialize the last hit time
    }

    public void checkEvent() {
        if (hit(34, 16, "right")) {
            damagePit();
        }
        if (hit(30, 12,"up")){
            healingpool(false);
            drink = true;
           
        }
        if(screen.player.life == 0) {
        	screen.player.Death = true;
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        screen.player.solidArea.x = (int) (screen.player.worldx + screen.player.solidArea.x);
        screen.player.solidArea.y = (int) (screen.player.worldy + screen.player.solidArea.y);

        eventRect.x = eventCol * TILES_SIZE + eventRect.x;
        eventRect.y = eventRow * TILES_SIZE + eventRect.y;

        if (screen.player.solidArea.intersects(eventRect)) {
            if ((reqDirection.equals("left") && screen.player.L) ||
                (reqDirection.equals("right") && screen.player.R) ||
                (reqDirection.equals("up") && screen.player.U) ||
                (reqDirection.equals("down") && screen.player.D) ||
                reqDirection.equals("any")) {
                hit = true;
            }
        }

        screen.player.solidArea.x = screen.player.solidAreaDefaultX;
        screen.player.solidArea.y = screen.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHitTime >= DAMAGE_COOLDOWN) {
            if (screen.player.defense > 0) {
                screen.player.defense -= 1;
                System.out.println("Defense hit! Remaining defense: " + screen.player.defense);
            } else {
                screen.player.life -= 1;
                System.out.println("Life hit! Remaining life: " + screen.player.life);
            }
            lastHitTime = currentTime;
        }
    }

    public void healingpool(boolean logic) {
        if(logic == true && drink == true){
        	
            screen.player.life = screen.player.maxLife;
            screen.player.defense = screen.player.maxDef;
            screen.ui.showMessage("fountain healed you!");
            lastHitTime = System.currentTimeMillis(); // Reset cooldown after healing
        	}
        	logic = false;
        	drink = false;
        }
    
}
