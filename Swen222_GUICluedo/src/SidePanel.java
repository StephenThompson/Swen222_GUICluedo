import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SidePanel extends JPanel{
	BufferedImage board = null;
	
	public SidePanel(){
		JButton viewCards = new JButton("View Cards");
		JButton move = new JButton("Move");
		JButton guess = new JButton("Guess");
		JButton accuse = new JButton("Accuse");

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);

		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;


		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("src/Images/ColonelMustard.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel, c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.gridx = 0;
		c.gridy = 1;

		JTextField name = new JTextField("Colonel Mustard");
		name.setEditable(false);
		add(name, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(viewCards, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(move, c);
		
		c.gridx = 0;
		c.gridy = 4;
		add(guess, c);
		
		c.gridx = 0;
		c.gridy = 5;
		add(accuse, c);
		
		setPreferredSize(new Dimension(300, 480));
		try {
			board = ImageIO.read(new File("src/Images/MenuTexture.jpg"));
		} catch (IOException e) {
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(210, 230, 220, 50);
        Color color2 = new Color(40, 65, 45, 200);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        TexturePaint tp = new TexturePaint(board, new Rectangle2D.Double(0,0, board.getWidth(), board.getHeight()));
        g2d.setPaint(tp);
        g2d.fillRect(0, 0, w, h);
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRect(0, 0, 5, h);
        
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(getWidth()-5, 0, 5, h);
    }
}
