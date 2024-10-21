package Zork;

public enum GameState {
	GAME_OVER_WIN,
	GAME_OVER_LOSE,
	GAME_NOT_OVER;
	
	/*
	 * print out enums as strings
	 * @return 
	 * string form of enum
	 */
	public String toString() {
		switch(this) {
		case GAME_OVER_WIN:
			return "GAME_OVER_WIN";
		case GAME_OVER_LOSE:
			return "GAME_OVER_LOSE";
		default:
			return "GAME_NOT_OVER";
		}
	}
}
