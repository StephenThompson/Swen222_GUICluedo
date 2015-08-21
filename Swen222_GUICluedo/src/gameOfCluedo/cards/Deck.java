package gameOfCluedo.cards;

import gameOfCluedo.GameOfCluedo;
import gameOfCluedo.GuessTuple;
import gameOfCluedo.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

/**
 * This class represents the deck of cards in the game of Cluedo. It keeps
 */
public class Deck {
	private List<CharCard> characters;
	private List<WeaponCard> weapons;
	private List<RoomCard> rooms;

	public Deck(List<CharCard> characters, List<WeaponCard> weapons, List<RoomCard> room) {
		super();
		this.characters = characters;
		this.weapons = weapons;
		this.rooms = room;
	}

	public Deck() {
		super();
		// Create lists
		List<CharCard> charCards = new ArrayList<CharCard>();
		List<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
		List<RoomCard> roomCards = new ArrayList<RoomCard>();

		// Characters
		try{
			String[] c = GameOfCluedo.charList;
			charCards.add(new CharCard(c[0], ImageIO.read(new File("src/Images/CharacterCards/MissScarlett.png"))));
			charCards.add(new CharCard(c[1], ImageIO.read(new File("src/Images/CharacterCards/ColonelMustard.png"))));
			charCards.add(new CharCard(c[2], ImageIO.read(new File("src/Images/CharacterCards/MrsWhite.png"))));
			charCards.add(new CharCard(c[3], ImageIO.read(new File("src/Images/CharacterCards/MrGreen.png"))));
			charCards.add(new CharCard(c[4], ImageIO.read(new File("src/Images/CharacterCards/MrsPeacock.png"))));
			charCards.add(new CharCard(c[5], ImageIO.read(new File("src/Images/CharacterCards/ProfessorPlum.png"))));

			// Weapons
			String[] w = GameOfCluedo.weaponList;
			weaponCards.add(new WeaponCard(w[0], ImageIO.read(new File("src/Images/WeaponCards/Candlestick.png"))));
			weaponCards.add(new WeaponCard(w[1], ImageIO.read(new File("src/Images/WeaponCards/Knife.png"))));
			weaponCards.add(new WeaponCard(w[2], ImageIO.read(new File("src/Images/WeaponCards/LeadPipe.png"))));
			weaponCards.add(new WeaponCard(w[3], ImageIO.read(new File("src/Images/WeaponCards/Revolver.png"))));
			weaponCards.add(new WeaponCard(w[4], ImageIO.read(new File("src/Images/WeaponCards/Rope.png"))));
			weaponCards.add(new WeaponCard(w[5], ImageIO.read(new File("src/Images/WeaponCards/Wrench.png"))));

			// Rooms
			String[] r = GameOfCluedo.roomList;
			roomCards.add(new RoomCard(r[0], ImageIO.read(new File("src/Images/RoomCards/Kitchen.png"))));
			roomCards.add(new RoomCard(r[1], ImageIO.read(new File("src/Images/RoomCards/Ballroom.png"))));
			roomCards.add(new RoomCard(r[2], ImageIO.read(new File("src/Images/RoomCards/Conservatory.png"))));
			roomCards.add(new RoomCard(r[3], ImageIO.read(new File("src/Images/RoomCards/DiningRoom.png"))));
			roomCards.add(new RoomCard(r[4], ImageIO.read(new File("src/Images/RoomCards/BillardRoom.png"))));
			roomCards.add(new RoomCard(r[5], ImageIO.read(new File("src/Images/RoomCards/Library.png"))));
			roomCards.add(new RoomCard(r[6], ImageIO.read(new File("src/Images/RoomCards/Lounge.png"))));
			roomCards.add(new RoomCard(r[7], ImageIO.read(new File("src/Images/RoomCards/Hall.png"))));
			roomCards.add(new RoomCard(r[8], ImageIO.read(new File("src/Images/RoomCards/Study.png"))));

		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		this.characters = charCards;
		this.weapons = weaponCards;
		this.rooms = roomCards;
	}

	/**
	 * Deals cards to the players hands and creates and returns the answer envelope.
	 * @param players
	 * @return
	 */
	public GuessTuple deal(List<Player> players){

		//Creates envelope
		CharCard envChar = characters.get((int)(Math.random()*characters.size()));
		WeaponCard envWeapon = weapons.get((int)(Math.random()*weapons.size()));
		RoomCard envRoom = rooms.get((int)(Math.random()*rooms.size()));
		GuessTuple envelope = new GuessTuple(envChar, envWeapon, envRoom);

		//Clears players hands
		for(Player p:players){
			p.gethand().clear();
		}

		//Combines decks
		List<Card> fullDeck = new ArrayList<Card>(characters);
		fullDeck.addAll(weapons);
		fullDeck.addAll(rooms);

		//Remove envelope
		fullDeck.remove(envChar);
		fullDeck.remove(envWeapon);
		fullDeck.remove(envRoom);

		//Deals out all cards to players hands
		while(!fullDeck.isEmpty()){
			//Deals a card to each player
			for(Player p:players){
				if(fullDeck.isEmpty()){break;}
				Card nextCard = fullDeck.get((int)(Math.random()*fullDeck.size()));
				fullDeck.remove(nextCard);
				p.addToHand(nextCard);
			}
		}

		return envelope;
	}
}
