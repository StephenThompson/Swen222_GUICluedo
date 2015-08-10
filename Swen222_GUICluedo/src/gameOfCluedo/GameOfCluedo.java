package gameOfCluedo;

import gameOfCluedo.cards.*;

import java.util.ArrayList;
import java.util.List;

public class GameOfCluedo {
	private Board board;
	private List<Player> players = new ArrayList<Player>();
	private List<Player> eliminated =  new ArrayList<Player>();
	private Player currentPlayer;
	private Deck deck;
	private GuessTuple envelope;
	private Player winner = null;

	public final  static String charList[] = {"Miss Scarlett", "Colonel Mustard", "Mrs. White", "The Reverend Green",
			"Mrs. Peacock", "Professor Plum"};

	public final  static String weaponList[] = {"Candlestick", "Dagger", "Lead Pipe", "Revolver",
			"Rope", "Spanner"};

	public final  static String roomList[] = {"Kitchen", "Ball Room", "Conservatory", "Dining Room",
			"Billard Room", "Library",  "Lounge",  "Hall",  "Study"};

	public GameOfCluedo() {
		super();
		this.board = new Board("src/CluedoBoard.txt");
	}

	/**
	 * Sets up a new game of Cluedo
	 * @param numPlayers
	 */
	public void startGame(int numPlayers){
		// Create lists
		List<CharCard> charCards = new ArrayList<CharCard>();
		List<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
		List<RoomCard> roomCards = new ArrayList<RoomCard>();

		// Characters
		charCards.add(new CharCard("Miss Scarlett"));
		charCards.add(new CharCard("Colonel Mustard"));
		charCards.add(new CharCard("Mrs. White"));
		charCards.add(new CharCard("The Reverend Green"));
		charCards.add(new CharCard("Mrs. Peacock"));
		charCards.add(new CharCard("Professor Plum"));

		// Weapons
		weaponCards.add(new WeaponCard("Candlestick"));
		weaponCards.add(new WeaponCard("Dagger"));
		weaponCards.add(new WeaponCard("Lead Pipe"));
		weaponCards.add(new WeaponCard("Revolver"));
		weaponCards.add(new WeaponCard("Rope"));
		weaponCards.add(new WeaponCard("Spanner"));

		// Rooms
		roomCards.add(new RoomCard("Kitchen"));
		roomCards.add(new RoomCard("Ball Room"));
		roomCards.add(new RoomCard("Conservatory"));
		roomCards.add(new RoomCard("Dining Room"));
		roomCards.add(new RoomCard("Billard Room"));
		roomCards.add(new RoomCard("Library"));
		roomCards.add(new RoomCard("Lounge"));
		roomCards.add(new RoomCard("Hall"));
		roomCards.add(new RoomCard("Study"));

		// Setup game
		players = new ArrayList<Player>();
		eliminated = new ArrayList<Player>();
		for(int i=0; i<numPlayers; i++){
			Player newPlayer = new Player(Player.Character.values()[i]);
			players.add(newPlayer);
			board.addPlayer(newPlayer);
		}

		deck = new Deck(charCards, weaponCards, roomCards);
		currentPlayer = players.get(0);
		envelope = deck.deal(players);
	}

	/**
	 * Attempts to move current player to new x,y.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean move(Position pos){
		if(pos==null){return false;}
		board.setPlayerPosition(currentPlayer, pos);
		return true;
	}

	/**
	 * Checks the current player's guess with the next player. If the player has a card that disproves the guess, the player returns it.
	 * @param guess
	 * @return Card
	 */
	public Card guess(GuessTuple guess){
		int i = players.indexOf(currentPlayer) + 1;
		if (i >= players.size()){
			i = 0;
		}

		while (players.indexOf(currentPlayer) != i){
			Card c = players.get(i).checkGuess(guess);
			if (c != null){
				return c;
			}
			i++;
			if (i >= players.size()){
				i = 0;
			}
		}
		return null;
	}

	/**
	 * Returns whether the player has guessed the cards in the envelope correctly or not
	 * @param guess
	 * @return boolean
	 */
	public boolean accuse(GuessTuple guess){
		if (guess.equals(envelope)){
			Player c = currentPlayer;
			eliminated = new ArrayList<Player>(players);
			eliminated.remove(c);
			players.clear();
			players.add(c);
			return true;
		}
		return false;
	}



	/**
	 * Checks whether there is one player remaining
	 * @return boolean
	 */
	public boolean checkGameOver(){
		return players.size()-eliminated.size() <= 1||winner!=null;
	}

	/**
	 * Returns reachable rooms for currentPlayer with the given dice roll
	 * @param diceRoll
	 * @return
	 */
	public List<Room> getReachableRooms(int diceRoll){
		return board.reachableRooms(diceRoll, getPlayerPos());
	}

	/**
	 * Returns current players position
	 */
	public Position getPlayerPos(){
		return board.getPlayerPosition(currentPlayer);
	}

	/**
	 * Return currentPlayer
	 * @return
	 */
	public Player getCurrentPlayer(){
		return currentPlayer;
	}

	/**
	 * Check if move to new position valid with dice roll for current player
	 * @param newPos
	 * @param diceRoll
	 * @return
	 */
	public boolean validMove(Position newPos, int diceRoll){
		if(newPos.isRoom()||(newPos.getX()>=0 && newPos.getY()>=0 && newPos.getX()<board.xSize && newPos.getY()<board.ySize)){
			return board.validMove(currentPlayer, newPos, diceRoll);
		}
		return false;
	}

	/**
	 * Moves the turn to the next player
	 */
	public void endTurn(){
		if(players.indexOf(currentPlayer)<players.size()-1){
			currentPlayer = players.get(players.indexOf(currentPlayer)+1);
		}else{
			currentPlayer = players.get(0);
		}
	}

	/**
	 * Gets the next player
	 * @return
	 */
	public Player nextPlayer(){
		if(players.indexOf(currentPlayer)<players.size()-1){
			return players.get(players.indexOf(currentPlayer)+1);
		}else{
			return players.get(0);
		}
	}

	/**
	 * Draws the board
	 */
	public void drawBoard(){
		board.draw();
	}

	/**
	 * Returns winner of game
	 * @return
	 */
	public Player getWinner(){
		if(players.size()-eliminated.size()<=1){
			players.removeAll(eliminated);
			winner=players.get(0);
		}
		return winner;
	}

	/**
	 * Set winner
	 * @param winner
	 */
	public void setWinner(Player winner){
		this.winner = winner;
	}

	/**
	 * Adds player to eliminated list and removes them from board
	 * @param loser
	 */
	public void playerLost(Player loser){
		eliminated.add(loser);
		board.removePlayer(loser);
	}

	/**
	 * Check if player is eliminated
	 * @param p
	 * @return
	 */
	public boolean isEliminated(Player p){
		return eliminated.contains(p);
	}
}
