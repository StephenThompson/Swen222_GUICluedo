package gameOfCluedo;

import gameOfCluedo.cards.*;

/**
 * This class is used for suggestions, accusations and the envelope in the Game of Cluedo.
 * @author Stephen Thompson
 *
 */
public class GuessTuple {
	private final CharCard character;
	private final WeaponCard weapon;
	private final RoomCard room;

	public GuessTuple(CharCard character, WeaponCard weapon, RoomCard room) {
		super();
		this.character = character;
		this.weapon = weapon;
		this.room = room;
	}

	/**
	 * Return the guessed character
	 * @return CharCard
	 */
	public CharCard getCharacter() {
		return character;
	}

	/**
	 * Return the guessed weapon
	 * @return WeaponCard
	 */
	public WeaponCard getWeapon() {
		return weapon;
	}

	/**
	 * Return the guessed room
	 * @return RoomCard
	 */
	public RoomCard getRoom() {
		return room;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((character == null) ? 0 : character.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o instanceof GuessTuple){
			GuessTuple t = (GuessTuple)o;
			return character.equals(t.character) && weapon.equals(t.weapon) && room.equals(t.room);
		}
		return false;
	}
}
