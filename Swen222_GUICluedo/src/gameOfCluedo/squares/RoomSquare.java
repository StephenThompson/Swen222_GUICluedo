package gameOfCluedo.squares;

import gameOfCluedo.Room;

/**
 * This class represents a room square which the player cannot move onto. The room square is not the actual room, but rather
 * acts as a boundary on the Cluedo board.
 */
public class RoomSquare implements Square {

	private Room room;

	public RoomSquare(Room room){
		super();
		this.room = room;
	}

	public Room room(){
		return room;
	}
}
