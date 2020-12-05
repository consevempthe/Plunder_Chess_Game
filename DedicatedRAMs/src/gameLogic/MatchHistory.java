package gameLogic;

import java.util.ArrayList;

/**
 * MatchHistory is a class that contains a list of games for a user. You can add games to the matchHistory or get the list of games. Games cannot be deleted from your history.
 * @author DedicatedRAMs NF
 *
 */
public class MatchHistory {
	
	private ArrayList<Game> games;
	
	/**
	 * The default constructor simply instantiates the games ArrayList. 
	 */
	public MatchHistory() {
		games = new ArrayList<>();
	}
	
	/**
	 * addGame() adds a game to the games list.
	 * @param game - Game to be added.
	 */
	public void addGame(Game game) {
		games.add(game);
	}
	
	/**
	 * getGames() gets the list of games in the MatchHistory.
	 * @return ArrayList of Games.
	 */
	public ArrayList<Game> getGames(){
		return games;	
	}

	/**
	 * getGame() gets the game corresponding to the parameter gameID which should be unique.
	 * @param gameID - gameID to get the Game of.
	 * @return Game corresponding to the gameID.
	 */
	public Game getGame(String gameID) {
		for (Game game : games) {
			if (game.getGameID().equals(gameID))
				return game;
		}
		return null;
	}
}
