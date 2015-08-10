package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gameOfCluedo.GuessTuple;
import gameOfCluedo.Player;
import gameOfCluedo.cards.*;

public class Test_Cards {

	@Test
	public void pass_Equals_1(){
		Card c1 = new CharCard("Test");
		Card c2 = new CharCard("Test");
		assertTrue(c1.equals(c2));
	}

	@Test
	public void pass_Equals_2(){
		CharCard c1 = new CharCard("Test");
		CharCard c2 = new CharCard("Test");
		assertTrue(c1.equals(c2));
	}

	@Test
	public void pass_Equals_3(){
		Card c1 = new WeaponCard("Test");
		Card c2 = new WeaponCard("Test");
		assertTrue(c1.equals(c2));
	}

	@Test
	public void pass_Equals_4(){
		Card c1 = new RoomCard("Test");
		Card c2 = new RoomCard("Test");
		assertTrue(c1.equals(c2));
	}

	@Test
	public void fail_Equals_1(){
		Card c1 = new WeaponCard("Test");
		Card c2 = new RoomCard("Test");
		assertFalse(c1.equals(c2));
	}

	@Test
	public void fail_Equals_2(){
		Card c1 = new CharCard("Test");
		Card c2 = null;
		assertFalse(c1.equals(c2));
	}

	@Test
	public void fail_Equals_3(){
		Card c1 = new WeaponCard("Test");
		RoomCard c2 = new RoomCard("Test");
		assertFalse(c1.equals(c2));
	}
	@Test
	public void GetTitle(){
		String title = "Test";
		Card c1 = new CharCard(title);
		assertTrue(c1.getTitle().equals(title));
	}

	@Test
	public void testDeal_1(){
		CharCard c1 = new CharCard("TestChar1");
		WeaponCard w1 = new WeaponCard("TestWeapon1");
		RoomCard r1 = new RoomCard("TestRoom1");

		CharCard c2 = new CharCard("TestChar2");
		WeaponCard w2 = new WeaponCard("TestWeapon2");
		RoomCard r2 = new RoomCard("TestRoom2");

		Player p = new Player(Player.Character.Miss_Scarlett);

		ArrayList<CharCard> charCards = new ArrayList<CharCard>();
		charCards.add(c1);
		charCards.add(c2);

		ArrayList<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
		weaponCards.add(w1);
		weaponCards.add(w2);

		ArrayList<RoomCard> roomCards = new ArrayList<RoomCard>();
		roomCards.add(r1);
		roomCards.add(r2);

		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p);

		Deck d = new Deck(charCards, weaponCards, roomCards);
		GuessTuple env = d.deal(players);


		assertNull(p.checkGuess(env));
	}

	@Test
	public void testDeal_2(){
		CharCard c1 = new CharCard("TestChar1");
		WeaponCard w1 = new WeaponCard("TestWeapon1");
		RoomCard r1 = new RoomCard("TestRoom1");

		CharCard c2 = new CharCard("TestChar2");
		WeaponCard w2 = new WeaponCard("TestWeapon2");
		RoomCard r2 = new RoomCard("TestRoom2");

		Player p = new Player(Player.Character.Miss_Scarlett);

		ArrayList<CharCard> charCards = new ArrayList<CharCard>();
		charCards.add(c1);
		charCards.add(c2);

		ArrayList<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
		weaponCards.add(w1);
		weaponCards.add(w2);

		ArrayList<RoomCard> roomCards = new ArrayList<RoomCard>();
		roomCards.add(r1);
		roomCards.add(r2);

		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p);

		Deck d = new Deck(charCards, weaponCards, roomCards);
		GuessTuple env = d.deal(players);

		Set<Card> actualCards = new HashSet<Card>();
		Set<Card> dealtCards = new HashSet<Card>();

		actualCards.addAll(charCards);
		actualCards.addAll(weaponCards);
		actualCards.addAll(roomCards);

		dealtCards.add(env.getCharacter());
		dealtCards.add(env.getWeapon());
		dealtCards.add(env.getRoom());

		dealtCards.addAll(p.gethand());

		assertTrue(actualCards.equals(dealtCards));
	}
}
