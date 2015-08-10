package Tests;

import static org.junit.Assert.*;
import gameOfCluedo.Dice;
import gameOfCluedo.GameOfCluedo;

import org.junit.Test;

public class Test_GameLogic {

	@Test
	public void gameover(){
		GameOfCluedo game = new GameOfCluedo();
		game.startGame(6);
		game.setWinner(game.getCurrentPlayer());
		assertTrue(game.checkGameOver());
		assertTrue(game.getWinner().equals(game.getCurrentPlayer()));
	}
	
	@Test
	public void dice(){
		for (int i = 0; i < 10000; i++){
			int value = Dice.roll();
			assert value >= 1 && value <= 6;
		}
	}
}
