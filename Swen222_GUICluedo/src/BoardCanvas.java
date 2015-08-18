import gameOfCluedo.Board;
import gameOfCluedo.Player;
import gameOfCluedo.Position;

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
	BufferedImage board;

	public BoardCanvas(){
		setPreferredSize(new Dimension(600, 600));
	}

	public void setBoard(BufferedImage img){
		board = img;
		this.repaint();
	}

	public Position getBoardPosition(int x, int y){
		double boardScale = Math.min(this.getSize().width, this.getSize().height)/(double)Board.SIZE;
		System.out.println("Scale: "+ boardScale);
		int boardX = Math.max(0, (this.getSize().width - this.getSize().height)/2);
		int boardY = Math.max(0, (this.getSize().height - this.getSize().width)/2);
		int posX = (int)((((x-boardX)/boardScale)-Board.OFF_X)/(Board.GRID_SIZE));
		int posY = (int)((((y-boardY)/boardScale)-Board.OFF_Y)/(Board.GRID_SIZE));
		return new Position(posX, posY);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);

		if (board == null) {
			return;
		}
		int size = Math.min(this.getSize().width, this.getSize().height);
		int x = Math.max(0, (this.getSize().width - this.getSize().height)/2);
		int y = Math.max(0, (this.getSize().height - this.getSize().width)/2);
		g.drawImage(board, x, y, size, size, this);
	}
}
