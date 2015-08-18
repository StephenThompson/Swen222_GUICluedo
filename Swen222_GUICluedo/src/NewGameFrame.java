import gameOfCluedo.GameOfCluedo;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class NewGameFrame extends JFrame {
	private final int minPlayers = 3;
	private final int maxPlayers = 6;

	private final JPanel pnl_main;
	private final JTextField txt_name;
	private final DefaultListModel listModel;
	private final JRadioButton rad_character[];
	private final JList list_players;
	private JLabel picLabel = null;

	private BufferedImage[] myPicture = null;
	private BufferedImage backTexture = null;
	private int selectedIndex;

	public NewGameFrame(GameOfCluedo goc){
		super("New Game");

		/**
		 * Main Panel
		 */
		pnl_main = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        drawBackground(g, getWidth(), getHeight());
		    }
		};

		// Load background texture
		try {
			backTexture = ImageIO.read(new File("src/Images/MenuTexture.jpg"));
		} catch (IOException e) {
		}

		/**
		 * Grid Layout
		 */
		GridBagConstraints lay_gridConst = new GridBagConstraints();
		pnl_main.setLayout(new GridBagLayout());
		lay_gridConst.gridx = 0;
		lay_gridConst.gridy = 0;

		lay_gridConst.insets = new Insets(15, 15, 5, 15);
		lay_gridConst.ipadx = 5;
		lay_gridConst.ipady = 5;
		lay_gridConst.fill = lay_gridConst.BOTH;

		/**
		 * Player's Character Image
		 */
		try {
			myPicture = new BufferedImage[]{
					ImageIO.read(new File("src/Images/Scarlett.jpg")), ImageIO.read(new File("src/Images/Mustard.jpg")),
					ImageIO.read(new File("src/Images/White.jpg")), ImageIO.read(new File("src/Images/Green.jpg")),
					ImageIO.read(new File("src/Images/Peacock.jpg")), ImageIO.read(new File("src/Images/Plum.jpg"))};
			ImageIcon img = new ImageIcon(myPicture[0]);
			picLabel = new JLabel(img);
			pnl_main.add(picLabel, lay_gridConst);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Player's Name
		 */
		lay_gridConst.insets = new Insets(5, 15, 5, 15);
		lay_gridConst.gridy = 1;
		txt_name = new JTextField("Player 1");
		pnl_main.add(txt_name, lay_gridConst);

		//Listener
		txt_name.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				setPlayerName(txt_name.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {}
		});
		/**
		 * Character Radio Buttons
		 */
		JPanel pnl_radio = new JPanel();
		ButtonGroup group = new ButtonGroup();

		lay_gridConst.gridy = 1;

		//
		pnl_radio.setLayout(new GridLayout(6,1));
		pnl_radio.setOpaque(false);

		rad_character = new JRadioButton[Player.Character.values().length];
		for (Player.Character c : Player.Character.values()){
			rad_character[c.ordinal()] = new JRadioButton(c.name().replace('_', ' '));
			rad_character[c.ordinal()].addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					setPlayerCharacter(c);
				}
			});

			rad_character[c.ordinal()].setOpaque(false);
			rad_character[c.ordinal()].setForeground(new Color(225,250,255,255));

		    group.add(rad_character[c.ordinal()]);
		    pnl_radio.add(rad_character[c.ordinal()]);
		}
		//rad_character[Player.Character.s].setSelected(true);
		lay_gridConst.gridy = 2;
		lay_gridConst.insets = new Insets(5, 15, 15, 15);
		pnl_main.add(pnl_radio, lay_gridConst);

		/**
		 * List of players
		 */
		lay_gridConst.gridx = 1;
		lay_gridConst.gridy = 0;
		lay_gridConst.insets = new Insets(15, 15, 5, 15);
		lay_gridConst.gridwidth = 2;

		// Defines items in list
		listModel = new DefaultListModel();
		for (int i = 0; i < 3; ++i){
			rad_character[i].setEnabled(false);
			listModel.addElement(new Player(Player.Character.values()[i], "Player " + (i+1)));
		}

		// Add list to the main panel
		list_players = new JList(listModel);
		list_players.setSelectedIndex(0);
		rad_character[0].setEnabled(true);
		rad_character[0].setSelected(true);
		selectedIndex = list_players.getSelectedIndex();
		list_players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pnl_main.add(list_players, lay_gridConst);


		// Listener
		list_players.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setPlayer((Player)list_players.getSelectedValue());
			}
		});
		/**
		 * The "Add"/"Delete" player Buttons and the "Done" button
		 */
		JButton btn_add, btn_delete, btn_done;
		btn_add = new JButton("Add");
		btn_delete = new JButton("Delete");
		btn_done = new JButton("Done");

		Dimension btnSize = new Dimension(140,30);
		btn_add.setPreferredSize(btnSize);
		btn_delete.setPreferredSize(btnSize);
		btn_done.setPreferredSize(btnSize);

		// Listeners
		btn_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPlayer();
			}
		});

		btn_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletePlayer();
			}
		});

		// Adds the "add player" button
		lay_gridConst.insets = new Insets(5, 15, 15, 0);
		lay_gridConst.gridwidth = 1;
		lay_gridConst.gridx = 1;
		lay_gridConst.gridy = 1;
		pnl_main.add(btn_add, lay_gridConst);

		// Adds the "delete player" button
		lay_gridConst.insets = new Insets(5, 0, 15, 15);
		lay_gridConst.gridx = 2;
		pnl_main.add(btn_delete, lay_gridConst);

		// Adds the "Done" button
		lay_gridConst.gridx = 2;
		lay_gridConst.gridy = 2;
		lay_gridConst.insets = new Insets(5, 15, 25, 25);
		lay_gridConst.fill = lay_gridConst.HORIZONTAL;
		lay_gridConst.anchor = lay_gridConst.SOUTHEAST;
		pnl_main.add(btn_done, lay_gridConst);

		/**
		 * Main Window
		 */
		add(pnl_main);
		pack();
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Set Methods
	 */
	private void addPlayer(){
		if (listModel.size() == maxPlayers){
			JOptionPane.showMessageDialog(this, "You have reached the maximum number of players.",
					"Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		listModel.addElement(new Player(Player.Character.values()[list_players.getLastVisibleIndex()+1], 
															"Player " + (list_players.getLastVisibleIndex()+2)));
	}

	private void deletePlayer(){
		if (listModel.size() == minPlayers){
			JOptionPane.showMessageDialog(this, "You must have at least " + minPlayers + " players.",
					"Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}

		int selected = list_players.getSelectedIndex();
		listModel.remove(list_players.getSelectedIndex());
	}

	private void setPlayerName(String newName){
		listModel.set(list_players.getSelectedIndex(), new Player(
				((Player)list_players.getSelectedValue()).getCharacter(), newName)); //XXX Need to set a limit on the name length
	}

	private void setPlayerCharacter(Player.Character c){
		listModel.set(list_players.getSelectedIndex(), new Player( c,
									((Player)list_players.getSelectedValue()).getName())); //XXX Need to set a limit on the name length

		ImageIcon img = new ImageIcon(myPicture[c.ordinal()]);
		picLabel.setIcon(img);
		//XXX
	}

	private void setPlayer(Player p){
		if (p == null) return;
		txt_name.setText(p.getName());
		rad_character[selectedIndex].setEnabled(false);
		selectedIndex = list_players.getSelectedIndex();
		rad_character[selectedIndex].setEnabled(true);
		rad_character[((Player)list_players.getSelectedValue()).getCharacter().ordinal()].setSelected(true);
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
