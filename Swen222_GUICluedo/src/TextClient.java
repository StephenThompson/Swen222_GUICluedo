
import java.util.List;
import java.util.Scanner;

import gameOfCluedo.*;
import gameOfCluedo.cards.*;

public class TextClient {
	private GameOfCluedo goc;
	private Scanner inScanner = new Scanner(System.in);

	private enum PlayerOption{
		MOVE, SUGGEST, ACCUSE
	}

	/**
	 * Returns player decision, asks user what the player wants to do (move, accuse or suggest), note not all three always available
	 * @param p
	 * @return
	 */
	private PlayerOption playerTurn(Player p){
		//Draw Board
		goc.drawBoard();
		//Setup option sets
		PlayerOption[] inRoomOptions = {PlayerOption.MOVE, PlayerOption.SUGGEST, PlayerOption.ACCUSE};
		PlayerOption[] options = {PlayerOption.MOVE, PlayerOption.ACCUSE};
		if(goc.getPlayerPos().isRoom()){
			options = inRoomOptions;
		}
		//Ask player for choice and print options
		System.out.println("\n" + p.getName().name().replace('_', ' ') + "'s turn!\n");
		printPlayerHand(p);
		System.out.println("\n-- Make a choice --");
		for(int i=0; i<options.length; i++){
			System.out.println(i+1 + "\t"+options[i].name());
		}
		//Read choice
		int selected = readInt("Choice : ");
		//Check choice and get another input if invalid
		while(selected<1||selected>3){
			System.out.println("Invalid choice");
			selected = readInt("Choice : ");
		}
		return options[selected-1];
	}

	/**
	 * Asks player where they want to move and moves that player if valid. (also calls askSuggest if lands in room)
	 * @param diceRoll
	 * @param player
	 */
	private void getMove(int diceRoll, Player player){
		System.out.println("You Rolled a " + diceRoll);
		Position originalPos = goc.getPlayerPos();
		//Prints out current pos
		if(originalPos.isRoom()){
			System.out.println("You are in room " + originalPos.getRoom().getName());
		}else{
			System.out.println("You are at this point " + (char)(originalPos.getX()+65) +","+ (originalPos.getY()+1));
		}
		//Get reachable rooms
		List<Room> reachable = goc.getReachableRooms(diceRoll);
		//Prints rooms and asks for input
		if (reachable.size() > 0){
			System.out.println("You can reach these rooms : ");
			for (int i = 0; i < reachable.size(); i++){
				System.out.printf("%2d. %s%n", i + 1, reachable.get(i).toString());
			}
			System.out.println("1. Enter X-Coordinate(A-X) or index of room");
		} else {
			System.out.println("1. Enter X-Coordinate(A-X): ");
		}
		Position newPos = null;
		//Reads inputs while valid move not found
		while(newPos==null){
			if(inScanner.hasNext("[a-zA-Z]")){
				//x is char read into int
				int x = (Character.toUpperCase(readChar(""))) - 65;
				//y read a an int
				int y = readInt("Enter Y-coordinate(1-25): ") - 1;
				newPos = new Position(x,y);
				//index for room read as an int
			}else if (inScanner.hasNextInt()){
				int roomIndex = readInt("") - 1;
				//Get room at index
				for(Room r : reachable){
					if (r.getName().equals(reachable.get(roomIndex).toString())){
						newPos = new Position(r);
					}
				}
			}
			//Checks if move valid
			if(newPos!=null && goc.validMove(newPos, diceRoll)){
				goc.move(newPos);
				//If landed in room ask for suggestion
				if(newPos.isRoom()){
					System.out.println("You can make a suggestion.");
					getSuggest();
				}
			}else{
				//Invalid move
				System.out.println("Invalid Move");
				newPos=null;
			}
		}
	}

	/**
	 * Asks player to make a suggestion
	 */
	private void getSuggest(){
		String[] charList = GameOfCluedo.charList;
		String[] weaponList = GameOfCluedo.weaponList;

		System.out.println("- Guess");
		//Get Room (room that you are in)
		System.out.println("You are accusing in room " + goc.getPlayerPos().getRoom().getName());
		// Get Character
		System.out.println("Choose your character");
		for (int i = 0; i < charList.length; i++){
		System.out.println((i+1) + ". " + charList[i]);
		}
		int charChoice = readInt(": ") - 1;

		//Get  Weapon
		System.out.println("Choose your weapon");
		for (int i = 0; i < weaponList.length; i++){
		System.out.println((i+1) + ". " + weaponList[i]);
		}
		int weaponChoice = readInt(": ") - 1;

		//Create cards
		CharCard character = new CharCard(charList[charChoice]);
		WeaponCard weapon = new WeaponCard(weaponList[weaponChoice]);
		RoomCard room = new RoomCard(goc.getPlayerPos().getRoom().getName());


		GuessTuple guess = new GuessTuple(character, weapon, room);
		//Check cards
		Card back = goc.guess(guess);
		if (back != null){
			System.out.println("The next player card is \"" + back.getTitle() + "\"");
		} else {
			System.out.println("The next player cannot disprove your guess");
		}
	}

	/**
	 * Asks player to make accusation
	 */
	private void getAccuse(){
		String[] charList = GameOfCluedo.charList;
		String[] roomList = GameOfCluedo.roomList;
		String[] weaponList = GameOfCluedo.weaponList;

		System.out.println("- Accuse");

		//Get Character
		System.out.println("Choose your character");
		for (int i = 0; i < charList.length; i++){
		System.out.println((i+1) + ". " + charList[i]);
		}
		int charChoice = readInt(": ") - 1;

		//Get Weapon
		System.out.println("Choose your weapon");
		for (int i = 0; i < weaponList.length; i++){
		System.out.println((i+1) + ". " + weaponList[i]);
		}
		int weaponChoice = readInt(": ") - 1;

		//Get Room
		System.out.println("Choose your room");
		for (int i = 0; i < roomList.length; i++){
		System.out.println((i+1) + ". " + roomList[i]);
		}
		int roomChoice = readInt(": ") - 1;

		//Create cards
		CharCard character = new CharCard(charList[charChoice]);
		WeaponCard weapon = new WeaponCard(weaponList[weaponChoice]);
		RoomCard room = new RoomCard(roomList[roomChoice]);

		GuessTuple guess = new GuessTuple(character, weapon, room);
		//Check if correct
		if (goc.accuse(guess)){
			System.out.println("You win");
			goc.setWinner(goc.getCurrentPlayer());//Correct set winner and end game
		} else {
			System.out.println("You lost");
			goc.playerLost(goc.getCurrentPlayer());//Wrong remove player from play
		}
	}

	/**
	 * Prints a players hand to the output
	 * @param p
	 */
	private void printPlayerHand(Player p){
		List<Card> hand = p.gethand();
		System.out.println(p.getName().name().replace('_', ' ') + "'s hand contains : ");
		for(int i = 0; i<hand.size()-1; i++){
			System.out.print(hand.get(i).toString() + ", ");
		}
		System.out.println(hand.get(hand.size()-1));
	}

	/**
	 * Main game loop, loops til game is over
	 * Controls the text output and when to ask players for information
	 */
	private void gameLoop(){
		System.out.println("Welcome to Game of Cluedo");
		System.out.println("Callum Gill and Stephen Thompson\nversion 1.0\n2015");

		//Get player number
		int numPlayers = 0;
		while (numPlayers < 3 || numPlayers > 6){
			numPlayers = readInt("\nPlease input the number of players : ");
			if (numPlayers < 3 || numPlayers > 6) {
				System.out.println("There must be between 3 - 6 players!");
			}
		}

		goc = new GameOfCluedo();

		//Start Game
		goc.startGame(numPlayers);

		//Loop through players until game has ended
		while (!goc.checkGameOver()){
			//	Ask player option
			Player currentPlayer = goc.getCurrentPlayer();
			if(!goc.isEliminated(currentPlayer)){
				PlayerOption opt = playerTurn(currentPlayer);
				//	Respond to player's choice
				switch (opt){
				case MOVE:
					getMove(Dice.roll(), currentPlayer);
					break;
				case SUGGEST:
					getSuggest();
					break;
				case ACCUSE:
					getAccuse();
					break;
				}
			}
			goc.endTurn();
		}
		System.out.println("GAME OVER!!!!!!!!!!!");
		System.out.println("The winner was : " + goc.getWinner().getName());
		//End game
	}

	/**
	 * Reads an int input, return -1 if none found
	 * @param msg
	 * @return
	 */
	private int readInt(String msg) {
		System.out.println(msg);
		if(inScanner.hasNextInt()){
			return inScanner.nextInt();
		}else{
			inScanner.nextLine();
			return -1;
		}
	}

	/**
	 * Reads char input returns \0 if none found
	 * @param msg
	 * @return
	 */
	private char readChar(String msg) {
		System.out.println(msg);
		if(inScanner.hasNext("[a-zA-Z]")){
			return inScanner.next("[a-zA-Z]").charAt(0);
		}else{
			inScanner.nextLine();
			return '\0';
		}
	}

	/**
	 * MAIN
	 * @param args
	 */
	public static void main(String[] args) {
		new TextClient().gameLoop();
	}
}
