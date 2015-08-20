import gameOfCluedo.GameOfCluedo;
import gameOfCluedo.Player;

import java.awt.Color;
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

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.SoftBevelBorder;


public class GuessFrame extends JFrame{
	private final JPanel pnl_main;
	private final JRadioButton rad_character[];
	private final JRadioButton rad_weapon[];
	private final JRadioButton rad_room[];
	private BufferedImage backTexture = null;

	public GuessFrame(){
		super("Guess");
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
		 * Character Radio
		 */
		JPanel pnl_charRadio = new JPanel();
		pnl_charRadio.setLayout(new GridLayout(0,1));
		pnl_charRadio.setOpaque(false);
		ButtonGroup groupChar = new ButtonGroup();

		
		JLabel lbl_char = new JLabel("Character");
		lbl_char.setForeground(new Color(225,250,255,255));
		pnl_charRadio.add(lbl_char);
		pnl_charRadio.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		
		rad_character = new JRadioButton[GameOfCluedo.charList.length];
		int i = 0;
		for (String c : GameOfCluedo.charList){
			rad_character[i] = new JRadioButton(c);
			rad_character[i].setOpaque(false);
			rad_character[i].setForeground(new Color(225,250,255,255));

			groupChar.add(rad_character[i]);
			pnl_charRadio.add(rad_character[i++]);
		}
		pnl_main.add(pnl_charRadio, lay_gridConst);

		/**
		 * Weapon
		 */
		JPanel pnl_weaponRadio = new JPanel();
		pnl_weaponRadio.setLayout(new GridLayout(0,1));
		pnl_weaponRadio.setOpaque(false);
		ButtonGroup groupWeapon = new ButtonGroup();
		
		JLabel lbl_weapon = new JLabel("Weapon");
		lbl_weapon.setForeground(new Color(225,250,255,255));
		pnl_weaponRadio.add(lbl_weapon);
		pnl_weaponRadio.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		
		lay_gridConst.gridx = 1;
		rad_weapon = new JRadioButton[GameOfCluedo.weaponList.length];
		i = 0;
		for (String c : GameOfCluedo.weaponList){
			rad_weapon[i] = new JRadioButton(c);
			rad_weapon[i].setOpaque(false);
			rad_weapon[i].setForeground(new Color(225,250,255,255));

		    groupWeapon.add(rad_weapon[i]);
		    pnl_weaponRadio.add(rad_weapon[i++]);
		}

		pnl_main.add(pnl_weaponRadio, lay_gridConst);


		/**
		 * Room
		 */
		JPanel pnl_roomRadio = new JPanel();
		pnl_roomRadio.setLayout(new GridLayout(0,1));
		pnl_roomRadio.setOpaque(false);
		ButtonGroup groupRoom = new ButtonGroup();

		JLabel lbl_room = new JLabel("Room");
		lbl_room.setForeground(new Color(225,250,255,255));
		pnl_roomRadio.add(lbl_room);
		pnl_roomRadio.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		lay_gridConst.gridx = 2;
		rad_room = new JRadioButton[GameOfCluedo.roomList.length];
		i = 0;
		for (String c : GameOfCluedo.roomList){
			rad_room[i] = new JRadioButton(c);
			rad_room[i].setOpaque(false);
			rad_room[i].setForeground(new Color(225,250,255,255));

		    groupRoom.add(rad_room[i]);
		    pnl_roomRadio.add(rad_room[i++]);
		}

		pnl_main.add(pnl_roomRadio, lay_gridConst);

		/**
		 * Button
		 */
		JButton btn_done = new JButton("Done");
		lay_gridConst.gridy = 1;
		lay_gridConst.insets = new Insets(15, 15, 15, 15);
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
