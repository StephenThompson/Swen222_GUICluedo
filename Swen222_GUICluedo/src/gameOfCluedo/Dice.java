package gameOfCluedo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class represents a dice
 * @author Stephen Thompson
 *
 */
public class Dice {

	private int currentValue = 0;
	private BufferedImage[] images;

	/**
	 * Returns a randomly generated number between 1 and 6
	 * @return int
	 */
	public int roll(){
		currentValue = 6;//(int)(1 + Math.random()*6);
		System.out.println("Dice rolled a : " + currentValue);
		try{
		images = new BufferedImage[]{ImageIO.read(new File("src/Images/1.png")),
				ImageIO.read(new File("src/Images/2.png")),
				ImageIO.read(new File("src/Images/3.png")),
				ImageIO.read(new File("src/Images/4.png")),
				ImageIO.read(new File("src/Images/5.png")),
				ImageIO.read(new File("src/Images/6.png")),};
		}catch(IOException e){
			System.out.println("Dice pic not here!");
		}
		return currentValue;
	}

	/**
	 * Returns a randomly generated number between 1 and 6
	 * @return int
	 */
	public int currentValue(){
		return currentValue;
	}

	public ImageIcon getDiceIcon(){
		return new ImageIcon(images[currentValue-1]);
	}
}
