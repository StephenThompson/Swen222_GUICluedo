package gameOfCluedo;
/**
 * This class represents a dice
 * @author Stephen Thompson
 *
 */
public class Dice {

	private int currentValue = 0;

	/**
	 * Returns a randomly generated number between 1 and 6
	 * @return int
	 */
	public int roll(){
		currentValue = (int)(1 + Math.random()*6);
		System.out.println("Dice rolled a : " + currentValue);
		return currentValue;
	}

	/**
	 * Returns a randomly generated number between 1 and 6
	 * @return int
	 */
	public int currentValue(){
		return currentValue;
	}
}
