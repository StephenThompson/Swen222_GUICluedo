package gameOfCluedo.cards;

import gameOfCluedo.GuessTuple;
import gameOfCluedo.Player;

import java.util.*;

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
		charCards.add(new CharCard("Miss Scarlett"));
		charCards.add(new CharCard("Colonel Mustard"));
		charCards.add(new CharCard("Mrs. White"));
		charCards.add(new CharCard("The Reverend Green"));
		charCards.add(new CharCard("Mrs. Peacock"));
		charCards.add(new CharCard("Professor Plum"));

		// Weapons
		weaponCards.add(new WeaponCard("Candlestick"));
		weaponCards.add(new WeaponCard("Knife"));
		weaponCards.add(new WeaponCard("Lead Pipe"));
		weaponCards.add(new WeaponCard("Revolver"));
		weaponCards.add(new WeaponCard("Rope"));
		weaponCards.add(new WeaponCard("Wrench"));

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
