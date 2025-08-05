package utilz;

import Main.MAINGAME;

public class Constants {
	
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * MAINGAME.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * MAINGAME.SCALE);
			
		}
	}

	// this indicates the movement indexes of tour character 0-5

	public static class  PlayerConstants{
		public static final int WALKING_U = 3;
		public static final int WALKING_D = 0;
		public static final int WALKING_UL = 2;
		public static final int WALKING_UR = 4;
		public static final int WALKING_R = 5;
		public static final int WALKING_L = 1;
		
		public static final int npc1 = 1;
		
		
		
		
		
		public static int GetSpriteAmount(int player_action) {
			//this indicates the animation frames
			switch(player_action) {
			case WALKING_U:
				return 8;
			case WALKING_D:
				return 8;
			case WALKING_R:
				return 8;
			case WALKING_L:
				return 8;
			case WALKING_UL:
				return 8;
			case WALKING_UR:
				return 8;
				
				default:
					return 1;
			}
			
			
		}
		
		public static int GetNpcSpriteAmount(int npc_action) {
			//this indicates the animation frames
			switch(npc_action) {
			case npc1:
				return 5;
			
				
				default:
					return 1;
			}
			
			
		}

		
	}
}
