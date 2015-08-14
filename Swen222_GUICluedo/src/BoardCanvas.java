import gameOfCluedo.Board;
import gameOfCluedo.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Acts as a controller between the board class(model) and the screen(view)
 *
 */
public class BoardCanvas extends JPanel {
	Board board;

	public BoardCanvas(){
		setPreferredSize(new Dimension(600, 600));
		board = new Board("src\\CluedoBoard.txt");
		board.addPlayer(new Player(Player.Character.Miss_Scarlett));
		board.addPlayer(new Player(Player.Character.Colonel_Mustard));
		board.addPlayer(new Player(Player.Character.Mrs_White));
		board.addPlayer(new Player(Player.Character.Professor_Plum));
		board.addPlayer(new Player(Player.Character.Mrs_Peacock));
		board.addPlayer(new Player(Player.Character.The_Reverend_Green));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage b = board.draw();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		int size = Math.min(this.getSize().width, this.getSize().height);
		int x = Math.max(0, (this.getSize().width - this.getSize().height)/2);
		int y = Math.max(0, (this.getSize().height - this.getSize().width)/2);
		g.drawImage(b, x, y, size, size, this);
	}
}
