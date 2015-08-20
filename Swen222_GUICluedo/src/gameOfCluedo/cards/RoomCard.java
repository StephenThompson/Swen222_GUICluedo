package gameOfCluedo.cards;

import java.awt.image.BufferedImage;

/**
 * This class represents a room card in the game of Cluedo.
 */
public class RoomCard extends Card {

	public RoomCard(String title) {
		super(title);
	}

	public RoomCard(String title, BufferedImage image) {
		super(title, image);
	}

}

