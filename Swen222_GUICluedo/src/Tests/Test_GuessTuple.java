package Tests;
import static org.junit.Assert.*;
import gameOfCluedo.GuessTuple;
import gameOfCluedo.cards.*;

import org.junit.Test;

public class Test_GuessTuple {
	
	@Test
	public void test_CorrectGuess_1(){
		CharCard charCard = new CharCard("TestChar");
		WeaponCard weaponCard = new WeaponCard("TestWeapon");
		RoomCard roomCard = new RoomCard("TestRoom");
		GuessTuple g1 = new GuessTuple(charCard, weaponCard, roomCard);
		GuessTuple g2 = new GuessTuple(charCard, weaponCard, roomCard);
		assertTrue(g1.equals(g2));
	}
	
	@Test
	public void test_CorrectGuess_2(){
		CharCard charCard1 = new CharCard("TestChar");
		WeaponCard weaponCard1 = new WeaponCard("TestWeapon");
		RoomCard roomCard1 = new RoomCard("TestRoom");
		
		CharCard charCard2 = new CharCard("TestChar");
		WeaponCard weaponCard2 = new WeaponCard("TestWeapon");
		RoomCard roomCard2 = new RoomCard("TestRoom");
		GuessTuple g1 = new GuessTuple(charCard1, weaponCard1, roomCard1);
		GuessTuple g2 = new GuessTuple(charCard2, weaponCard2, roomCard2);
		assertTrue(g1.equals(g2));
	}
	
	@Test
	public void test_IncorrectGuess_1(){
		CharCard charCard = new CharCard("TestChar");
		WeaponCard weaponCard = new WeaponCard("TestWeapon");
		RoomCard roomCard = new RoomCard("TestRoom");
		
		CharCard charCardGuess = new CharCard("TestCharWrong");
		GuessTuple g1 = new GuessTuple(charCard, weaponCard, roomCard);
		GuessTuple g2 = new GuessTuple(charCardGuess, weaponCard, roomCard);
		assertFalse(g1.equals(g2));
	}
}
