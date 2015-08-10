package gameOfCluedo;

public class Position {
	private Room room;
	private int x, y;

	/**
	 * Creats Position from co-ordinate
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates position from room, creates co-ordinate of room aswell
	 * @param room
	 */
	public Position(Room room) {
		super();
		this.room = room;
		this.x = room.getX();
		this.y = room.getY();
	}

	/**
	 * Check if this position is a room
	 * @return
	 */
	public boolean isRoom(){
		if(room!=null){
			return true;
		}
		return false;
	}

	/**
	 * Gets room
	 * @return
	 */
	public Room getRoom() {
		return room;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		if(isRoom()){
			return room.hashCode() * prime;
		}else{
			return x + y * prime;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		//if not room vs room
		if (!isRoom()) {
			if (other.isRoom())
				return false;
		//both are rooms with same room
		} else if (room.equals(other.getRoom()))
			return true;
		//is normal co-ordinate check if at same points
		if (x != other.getX())
			return false;
		if (y != other.getY())
			return false;
		return true;
	}
}
