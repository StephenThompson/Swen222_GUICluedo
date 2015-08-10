package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gameOfCluedo.GuessTuple;
import gameOfCluedo.Player;
import gameOfCluedo.Player.Character;
import gameOfCluedo.cards.*;

import org.junit.Test;

public class Test_Player {

	@Test
	public void test_GetName(){
		Player player = new Player(Character.Miss_Scarlett);
		assertEquals(Character.Miss_Scarlett, player.getName());
	}

	@Test
	public void test_GetHand(){
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(new CharCard("TestChar1"));
		hand.add(new CharCard("TestChar2"));
		hand.add(new CharCard("TestChar3"));

		Player player = new Player(Character.Miss_Scarlett);
		for (Card c : hand){
			player.addToHand(c);
		}
		assertTrue(player.gethand().equals(hand));
	}

	@Test
	public void test_GuessDisprove(){
		CharCard charCard = new CharCard("TestChar");
		WeaponCard weaponCard = new WeaponCard("TestWeapon");
		RoomCard roomCard = new RoomCard("TestRoom");

		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(charCard);
		hand.add(weaponCard);
		hand.add(roomCard);

		Player player = new Player(Character.Miss_Scarlett);
		for (Card c : hand){
			player.addToHand(c);
		}

		GuessTuple guess = new GuessTuple(charCard, weaponCard, roomCard);
		assertNotNull(player.checkGuess(guess));
	}

	@Test
	public void test_GuessConfirm(){
		CharCard charCard = new CharCard("TestChar");
		WeaponCard weaponCard = new WeaponCard("TestWeapon");
		RoomCard roomCard = new RoomCard("TestRoom");

		CharCard charCard2 = new CharCard("TestChar2");
		WeaponCard weaponCard2 = new WeaponCard("TestWeapon2");
		RoomCard roomCard2 = new RoomCard("TestRoom2");

		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(charCard);
		hand.add(weaponCard);
		hand.add(roomCard);

		Player player = new Player(Character.Miss_Scarlett);
		for (Card c : hand){
			player.addToHand(c);
		}

		GuessTuple guess = new GuessTuple(charCard2, weaponCard2, roomCard2);
		assertNull(player.checkGuess(guess));
	}
}
