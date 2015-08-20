package gameOfCluedo.cards;

import java.awt.image.BufferedImage;

/**
 * This class acts as an abstract class for the different type of cards found in the game of Cluedo.
 */
public abstract class Card {
	private final String title; // The word shown on the card
	private final BufferedImage image;

	public Card(String title) {
		super();
		image = null;
		this.title = title;
	}

	public Card(String title, BufferedImage image) {
		super();
		this.image = image;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public BufferedImage getImage() {
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this.getClass() == obj.getClass()){
			Card other = (Card) obj;
			return title.equals(other.title);
		}
		return false;
	}

	@Override
	public String toString(){
		return title;
	}
}
