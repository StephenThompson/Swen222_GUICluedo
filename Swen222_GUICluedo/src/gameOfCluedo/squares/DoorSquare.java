package gameOfCluedo.squares;

import gameOfCluedo.Room;

/**
 * This class represents a door entrance on the cluedo board. The door square holds a reference to a room.
 */
public class DoorSquare implements Square {
	private final Room to;

	public DoorSquare(Room to){
		this.to = to;
	}

	/**
	 * Returns the room which this door square belongs to
	 * @return
	 */
	public Room to(){
		return to;
	}
}
