package Tests;

import static org.junit.Assert.*;
import gameOfCluedo.Board;
import gameOfCluedo.Player;
import gameOfCluedo.Position;

import org.junit.Test;

public class Test_Board {

	@Test
	public void getInvalidPlayer(){
		Board board = new Board("src/CluedoBoard.txt");
		Player player = new Player(Player.Character.Miss_Scarlett);
		assertNull(board.getPlayerPosition(player));
	}
	
	@Test
	public void movePlayer(){
		Board board = new Board("src/CluedoBoard.txt");
		Player player = new Player(Player.Character.Miss_Scarlett);
		Position newPos = new Position(0, 0);
		board.addPlayer(player);
		board.setPlayerPosition(player, newPos);
		assertTrue(board.getPlayerPosition(player).equals(newPos));
	}
}
