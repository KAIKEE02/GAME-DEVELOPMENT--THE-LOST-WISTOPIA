package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import Gamestates.Play;
import Gamestates.gamestates;
import Main.MAINGAME;

public class DialougeContents {
    private Play playing;
 
    String[] god = {
            "Welcome to the world of WISTOPIA!",
            "Your journey begins here.",
            "I need You to save the princess, In exchange i'll send you back to ur world.\n In order to save the princess, You need to defeat Garaak (necro orc) ",
            "Collect SoulOrbs and keys to help With Your Adventure,\n Orbs are useful for leveling up and obtaining weapons",
            "Now your first goal is to obtain the Mystery weapon on the WIst house, \n You can explore the Maliya forest you must....... b??... be............ ",
            "Beware of the dangers ahead!....... \n .  There are two powerful orcs created by Garaak,",
            "Superior Orcs are powerful they steal ur life when they hit you, you must use Ranged weapon. \n when their life is low Superior orcs panics,",
            "Normal Orcs are weak you can Melee them ,\n overall orcs can sense you when Your nearby \n and Also they become fast and aggresive when you are using ranged weapon.",
            "Goodluck Javan!!",
            "The Controls are  1, 2 , 3 for Weapon switching,  W , A , S , D For movements \n Right Click for attack \n"
            + " ENTER for Interaction."
            + " SHIFT for Sprint"
};
    String[] entryguards = {
            "Sorry we cant let you in........ \n There so many superior orcs",
            "In order for you to enter the Castle help us to kill this orcs"
           
};
    public DialougeContents(Play playing) {
        this.playing = playing;
        
        
    }

   public String[] god() {
	
	   return god;
}
   public String[] guardsentry() {
		
	   return entryguards;
}
}
