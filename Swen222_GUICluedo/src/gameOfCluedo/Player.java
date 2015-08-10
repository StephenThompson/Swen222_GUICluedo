package gameOfCluedo;

import java.util.*;
import gameOfCluedo.cards.*;

public class Player {
	static public enum Character{
		Miss_Scarlett, Colonel_Mustard, Mrs_White
		,The_Reverend_Green ,Mrs_Peacock ,Professor_Plum
	}

	private List<Card> hand = new ArrayList<Card>();
	private Character name;

	public Player(Character name) {
		super();
		this.name = name;
	}

	/**
	 * Get player name
	 * @return
	 */
	public Character getName(){
		return name;
	}

	/**
	 * Checks to see if you have a card in a guess from another player and return said card
	 * @param guess
	 * @return
	 */
	public Card checkGuess(GuessTuple guess){
		long seed = System.nanoTime();
		Collections.shuffle(hand, new Random(seed));

		for (Card c : hand){
			if (c instanceof CharCard){
				if (c.equals(guess.getCharacter())){
					return guess.getCharacter();
				}
			} else if (c instanceof WeaponCard){
				if (c.equals(guess.getWeapon())){
					return guess.getWeapon();
				}
			} else if (c instanceof RoomCard){
				if (c.equals(guess.getRoom())){
					return guess.getRoom();
				}
			}
		}
		return null;
	}

	/**
	 *Returns players hand
	 * @return
	 */
	public List<Card> gethand(){
		return hand;
	}

	/**
	 * Add a card to players hand
	 * @param c
	 */
	public void addToHand(Card c){
		hand.add(c);
	}
}
