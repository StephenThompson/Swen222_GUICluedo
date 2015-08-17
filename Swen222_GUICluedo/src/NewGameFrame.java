import gameOfCluedo.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TexturePaint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class NewGameFrame extends JFrame {
	private BufferedImage[] myPicture = null;
	private JLabel picLabel = null;
	private BufferedImage backTexture = null;
	private JPanel pnl_main;

	public NewGameFrame(){
		super("New Game");

		pnl_main = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        drawBackground(g, getWidth(), getHeight());
		    }
		};

		// Background
		try {
			backTexture = ImageIO.read(new File("src/Images/MenuTexture.jpg"));
		} catch (IOException e) {
		}
		//dir

		GridBagConstraints lay_gridConst = new GridBagConstraints();
		pnl_main.setLayout(new GridBagLayout());



		ButtonGroup group = new ButtonGroup();
		lay_gridConst.gridx = 0;
		lay_gridConst.gridy = 0;

		lay_gridConst.insets = new Insets(15, 15, 5, 15);
		lay_gridConst.ipadx = 5;
		lay_gridConst.ipady = 5;
		lay_gridConst.fill = lay_gridConst.BOTH;
		
		
		//BufferedImage[] myPicture = null;
		try {
			myPicture = new BufferedImage[]{ImageIO.read(new File("src/Images/Scarlett.jpg")),
											ImageIO.read(new File("src/Images/Mustard.jpg")),
											ImageIO.read(new File("src/Images/White.jpg")),
											ImageIO.read(new File("src/Images/Green.jpg")),
											ImageIO.read(new File("src/Images/Peacock.jpg")),
											ImageIO.read(new File("src/Images/Plum.jpg"))};
			ImageIcon img = new ImageIcon(myPicture[0]);
			picLabel = new JLabel(img);
			pnl_main.add(picLabel, lay_gridConst);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lay_gridConst.insets = new Insets(5, 15, 5, 15);
		lay_gridConst.gridy = 1;
		JTextField name = new JTextField("Player 1");
		pnl_main.add(name, lay_gridConst);
		lay_gridConst.gridy = 1;
		
		//NewGameFrame frm_this = this;
		
		JPanel pnl_radio = new JPanel();
		pnl_radio.setLayout(new GridLayout(6,1));
		pnl_radio.setBackground(new Color(0,0,0,0));
		for (Player.Character c : Player.Character.values()){
			JRadioButton rad_op = new JRadioButton(c.name().replace('_', ' '));
			if (c == Player.Character.Miss_Scarlett)
				rad_op.setSelected(true);
			//rad_op.set
			rad_op.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					//if (myPicture == null || picLabel == null) return;
					ImageIcon img = new ImageIcon(myPicture[c.ordinal()]);
					picLabel.setIcon(img);
				}
			});
			rad_op.setBackground(new Color(0,0,0,0));
			rad_op.setForeground(new Color(225,250,255,255));
		    group.add(rad_op);
		    //lay_gridConst.gridy++;
		    pnl_radio.add(rad_op);
		}
		lay_gridConst.gridy = 2;
		lay_gridConst.insets = new Insets(5, 15, 15, 15);
		pnl_main.add(pnl_radio, lay_gridConst);
		
		lay_gridConst.gridx = 1;
		lay_gridConst.gridy = 0;
		lay_gridConst.insets = new Insets(15, 15, 5, 15);
		lay_gridConst.gridwidth = 2;
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("Player 1");
		listModel.addElement("Player 2");
		listModel.addElement("Player 3");

		JList list_players = new JList(listModel);
		//list_players.setPreferredSize(new Dimension(160,160));
		
		list_players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pnl_main.add(list_players, lay_gridConst);


		JButton btn_add = new JButton("Add");
		JButton btn_delete = new JButton("Delete");
		JButton btn_done = new JButton("Done");
		
		lay_gridConst.insets = new Insets(5, 15, 15, 0);
		lay_gridConst.gridwidth = 1;
		lay_gridConst.gridx = 1;
		lay_gridConst.gridy = 1;

		pnl_main.add(btn_add, lay_gridConst);
		
		lay_gridConst.insets = new Insets(5, 0, 15, 15);
		lay_gridConst.gridx = 2;
		pnl_main.add(btn_delete, lay_gridConst);
		
		lay_gridConst.gridx = 2;
		lay_gridConst.gridy = 2;
		lay_gridConst.insets = new Insets(5, 15, 15, 15);
		lay_gridConst.fill = lay_gridConst.HORIZONTAL;
		lay_gridConst.anchor = lay_gridConst.NORTHEAST;
		pnl_main.add(btn_done, lay_gridConst);

		add(pnl_main);
		pack(); // pack components tightly together
		//setPreferredSize(new Dimension(512, 512));
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
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
