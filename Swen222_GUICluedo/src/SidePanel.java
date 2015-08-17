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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;


public class SidePanel extends JPanel{
	BufferedImage board = null;
	JPanel characterPanel;
	JPanel optionPanel;

	public SidePanel(){
		Dimension btnSize = new Dimension(170, 32);
		JButton viewCards = new JButton("View Cards");
		viewCards.setPreferredSize(btnSize);
		JButton move = new JButton("Move");
		move.setPreferredSize(btnSize);
		JButton guess = new JButton("Guess");
		guess.setPreferredSize(btnSize);
		JButton accuse = new JButton("Accuse");
		accuse.setPreferredSize(btnSize);


		//Character Panel
		characterPanel = new JPanel();

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		characterPanel.setLayout(grid);
		characterPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		characterPanel.setBackground(new Color(0,0,0,50));
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;


		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("src/Images/Mustard.jpg"));
			ImageIcon img = new ImageIcon(myPicture);
			JLabel picLabel = new JLabel(img);
			characterPanel.add(picLabel, c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.gridx = 0;
		c.gridy = 1;

		JTextField name = new JTextField("Colonel Mustard");
		name.setEditable(false);
		characterPanel.add(name, c);

		c.gridx = 0;
		c.gridy = 2;
		characterPanel.add(viewCards, c);

		// Option Panel
		optionPanel = new JPanel();
		GridBagLayout grid2 = new GridBagLayout();
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.BOTH;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.insets = new Insets(5, 5, 5, 5);
		//c2.ipadx = 5;
		//c2.ipady = 5;
		optionPanel.setLayout(grid2);
		optionPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		optionPanel.setBackground(new Color(0,0,0,50));
		//optionPanel.setSize(100, 150);
		c2.gridx = 0;
		c2.gridy = 1;
		optionPanel.add(move, c2);

		c2.gridx = 0;
		c2.gridy = 2;
		optionPanel.add(guess, c2);

		c2.gridx = 0;
		c2.gridy = 3;
		optionPanel.add(accuse, c2);

		c2.gridy = 4;
		c2.weighty = 1.0;
		optionPanel.add(new JLabel(), c2);
		// Add stuff to main panel
		setLayout(new GridBagLayout());
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridwidth = 1;
		c3.gridheight = 1;
		c3.fill = GridBagConstraints.BOTH;

		c3.ipadx = 5;
		c3.ipady = 5;
		c3.weightx = 0.5;
		c3.weighty = 0.0;

		c3.insets = new Insets(15, 15, 15, 15);
		c3.anchor = GridBagConstraints.NORTHWEST;
		c3.gridx = 0;
		c3.gridy = 0;
		add(characterPanel, c3);
		c3.gridy = 1;
		c3.weighty = 1;
		add(optionPanel, c3);


		// Background
		//setPreferredSize(new Dimension(220, 480));
		try {
			board = ImageIO.read(new File("src/Images/MenuTexture.jpg"));
		} catch (IOException e) {
		}
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }

	private void drawBackground(Graphics g){
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
