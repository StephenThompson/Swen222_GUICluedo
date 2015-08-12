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
	BufferedImage board = null;

	public BoardCanvas(){
		setPreferredSize(new Dimension(480, 480));
		try {
			board = ImageIO.read(new File("src/Images/board.jpg"));
		} catch (IOException e) {
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		int size = Math.min(this.getSize().width, this.getSize().height);
		int x = Math.max(0, (this.getSize().width - this.getSize().height)/2);
		int y = Math.max(0, (this.getSize().height - this.getSize().width)/2);

		g.drawImage(board, x, y, size, size, this);
	}
}
