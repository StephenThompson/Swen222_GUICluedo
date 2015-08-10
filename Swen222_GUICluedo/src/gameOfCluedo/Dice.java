package gameOfCluedo;
/**
 * This class represents a dice
 * @author Stephen Thompson
 *
 */
public class Dice {
	/**
	 * Returns a randomly generated number between 1 and 6
	 * @return int
	 */
	public static int roll(){
		return (int)(1 + Math.random()*6);
	}
}
