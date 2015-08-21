import gameOfCluedo.cards.Card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class ShowCardsFrame extends JFrame {
	private BufferedImage backTexture = null;
	private JPanel pnl_main;
	private JPanel pnl_button;

	public ShowCardsFrame(List<Card> cards, String title, String message){
		GUIStart(cards, title);

		JTextArea lbl_message = new JTextArea(message);
		lbl_message.setOpaque(false);
		lbl_message.setForeground(Color.WHITE);
		lbl_message.setEditable(false);
		lbl_message.setWrapStyleWord(true);
		lbl_message.setMaximumSize(lbl_message.getPreferredSize());

		pnl_button.add(lbl_message);
		GUIEnd();
	}

	public ShowCardsFrame(List<Card> cards, String title){
		GUIStart(cards, title);
		GUIEnd();
	}

	private void GUIStart(List<Card> cards, String title){
		// Load background texture
		try {
			backTexture = ImageIO.read(new File("src/Images/MenuTexture.jpg"));
		} catch (IOException e) {
		}

		setTitle(title);

		pnl_main = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;

			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        drawBackground(g, getWidth(), getHeight());
		    }
		};

		setLayout(new GridBagLayout());
		//Create Card panels
		JPanel pnl_cards = new JPanel();

		if (cards.size() < 3){
			pnl_cards.setLayout(new GridLayout(0, cards.size(), 3, 3));
		} else {
			pnl_cards.setLayout(new GridLayout(0, 3, 3, 3));
		}
		pnl_cards.setOpaque(false);
		pnl_cards.setBorder(new EmptyBorder(15, 15, 15, 15));


		for(Card c: cards){
			JLabel cardLabel = new JLabel();
			System.out.println(c.getTitle());
			cardLabel.setIcon(new ImageIcon(c.getImage()));
			pnl_cards.add(cardLabel);
		}
		pnl_main.add(pnl_cards, BorderLayout.CENTER);

		//Create OK btn
		pnl_button = new JPanel();
		pnl_button.setLayout(new BoxLayout(pnl_button, BoxLayout.PAGE_AXIS));
		pnl_button.setOpaque(false);
		pnl_button.setBorder(new EmptyBorder(15, 15, 15, 15));
	}

	private void GUIEnd(){
		JButton btn_ok = new JButton("OK");
		btn_ok.setActionCommand("OK");

		btn_ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnl_button.add(btn_ok);

		pnl_main.add(pnl_button, BorderLayout.EAST);

		add(pnl_main);

		pack(); // pack components tightly together
		setResizable(true); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		System.out.println("Show cards created");
	}

	/**
	 * Draws the textured background on the side panel
	 * @param g
	 */
	private void drawBackground(Graphics g, int width, int height){
		Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(210, 230, 220, 50);
        Color color2 = new Color(40, 65, 45, 200);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
        TexturePaint tp = new TexturePaint(backTexture, new Rectangle2D.Double(0,0, backTexture.getWidth(), backTexture.getHeight()));

        // Draw the texture
        g2d.setPaint(tp);
        g2d.fillRect(0, 0, width, height);

        // Draw the gradient
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        // Draw the bevel
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRect(0, 0, 5, height);

        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(width-5, 0, 5, height);
	}
}
