import gameOfCluedo.Dice;
import gameOfCluedo.GameOfCluedo;
import gameOfCluedo.GuessTuple;
import gameOfCluedo.Position;
import gameOfCluedo.cards.Card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.SoftBevelBorder;

/**
 * The Cluedo frame is the main window for the cluedo game. It acts as an interface for the game of cluedo class.
 * JButton   : btn_Name
 * JMenuBar  : mbar_Name
 * JMenu     : menu_Name
 * JMenuItem : mitem_Name
 * JPanel    : pnl_Name
 * JFrame    : frm_Name
 * JCanvas   : can_Name
 * JLayout   : lay_Name
*/
public class CluedoFrame extends JFrame {
	private JTextField txt_name;
	private BufferedImage[] myPicture = null;
	private BufferedImage backTexture = null;
	private JLabel picLabel;
	private BoardCanvas can_board;
	private GameOfCluedo goc;
	private boolean moveSelected = false;
	private JLabel lbl_dice = null;
	private JButton btn_viewCards, btn_move, btn_guess, btn_accuse;

	public CluedoFrame() {
		/**
		 * Board Settings
		 */
		super("Cluedo");
		CluedoFrame frm_this = this;
		ActionListener btnListener = new BtnListener();
		setLayout(new BorderLayout()); // use border layour
		//Set cursor
		/*BufferedImage cursorImage = null;
		try{
		cursorImage = ImageIO.read(new File("src/Images/cursor.png"));
		}catch(IOException e){System.out.println("Error loading cursor image: cursor.png");}
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), "Cursor"));*/

		/**
		 * MenuBar
		 */
		// Declare menu items
		JMenuBar mbar_mainMenu;
		JMenu menu_file, menu_game;
		JMenuItem mitem_newGame, mitem_exitGame;

		//Create the menu bar.
		mbar_mainMenu = new JMenuBar();
		add(mbar_mainMenu, BorderLayout.NORTH);

		//Add menus to the menu bar
		menu_file = new JMenu("File");
		menu_game = new JMenu("Game");

		mbar_mainMenu.add(menu_file);
		mbar_mainMenu.add(menu_game);

		// Add buttons to menu_file
		mitem_newGame = new JMenuItem("New Game");
		mitem_exitGame = new JMenuItem("Exit");

		menu_file.add(mitem_newGame);
		menu_file.add(mitem_exitGame);

		// Action listeners
		mitem_newGame.setActionCommand("NEW GAME");
		mitem_newGame.addActionListener(btnListener);

		mitem_exitGame.setActionCommand("EXIT GAME");
		mitem_exitGame.addActionListener(btnListener);

		/**
		 * Canvas
		 */
		can_board = new BoardCanvas();
		add(can_board, BorderLayout.CENTER); // add canvas
		can_board.addMouseListener(new BoardMouseListener());

		/**
		 * Side Panel
		 */
		JPanel pnl_character, pnl_option, pnl_side, pnl_dice;

		Dimension btnSize = new Dimension(170, 32);

		pnl_side = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        drawBackground(g, getWidth(), getHeight());
		    }
		};

		add(pnl_side, BorderLayout.EAST);

		/*
		 * Grid Constraint
		 */
		GridBagConstraints lay_gridConst = new GridBagConstraints();
		lay_gridConst.fill = GridBagConstraints.BOTH;
		lay_gridConst.gridx = 0;
		lay_gridConst.gridy = 0;
		lay_gridConst.insets = new Insets(5, 5, 5, 5);
		lay_gridConst.ipadx = 5;
		lay_gridConst.ipady = 5;

		/*
		 * Character Panel
		 */
		pnl_character = new JPanel();
		pnl_character.setLayout(new GridBagLayout());
		pnl_character.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		pnl_character.setBackground(new Color(0,0,0,50));

		btn_viewCards = new JButton("View Cards");
		btn_viewCards.setPreferredSize(btnSize);
		btn_viewCards.setActionCommand("CARDS");

		// Player's Character
		/**
		 * Player's Character Image
		 */
		try {
			myPicture = new BufferedImage[]{
					ImageIO.read(new File("src/Images/Scarlett.jpg")), ImageIO.read(new File("src/Images/Mustard.jpg")),
					ImageIO.read(new File("src/Images/White.jpg")), ImageIO.read(new File("src/Images/Green.jpg")),
					ImageIO.read(new File("src/Images/Peacock.jpg")), ImageIO.read(new File("src/Images/Plum.jpg")),
					ImageIO.read(new File("src/Images/Blank.jpg"))};
			ImageIcon img = new ImageIcon(myPicture[myPicture.length-1]);
			picLabel = new JLabel(img);
			pnl_character.add(picLabel, lay_gridConst);
		} catch (IOException e) {
			e.printStackTrace();
		}

		lay_gridConst.gridy = 1;

		txt_name = new JTextField("Dr. Black");
		txt_name.setEditable(false);
		pnl_character.add(txt_name, lay_gridConst);

		lay_gridConst.gridy = 2;
		pnl_character.add(btn_viewCards, lay_gridConst);

		/*
		 * Option panel
		 */
		pnl_option = new JPanel();
		pnl_option.setLayout(new GridBagLayout());
		pnl_option.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		pnl_option.setBackground(new Color(0,0,0,50));

		// Buttons
		btn_viewCards.addActionListener(btnListener);
		btn_viewCards.setMnemonic(KeyEvent.VK_V);

 	    btn_move = new JButton("Move");
 	    btn_move.setPreferredSize(btnSize);
 	    btn_move.setActionCommand("MOVE");
 	    btn_move.addActionListener(btnListener);
 	    btn_move.setMnemonic(KeyEvent.VK_M);

		btn_guess = new JButton("Guess");
		btn_guess.setPreferredSize(btnSize);
		btn_guess.setActionCommand("GUESS");
		btn_guess.addActionListener(btnListener);
		btn_guess.setMnemonic(KeyEvent.VK_G);

		btn_accuse = new JButton("Accuse");
		btn_accuse.setPreferredSize(btnSize);
		btn_accuse.setActionCommand("ACCUSE");
		btn_accuse.addActionListener(btnListener);
		btn_accuse.setMnemonic(KeyEvent.VK_A);

		//Initilise btns as disabled
		btn_viewCards.setEnabled(false);
		btn_move.setVisible(false);
		btn_guess.setVisible(false);
		btn_accuse.setVisible(false);

		//
		lay_gridConst.gridy = 0;
		pnl_option.add(btn_move, lay_gridConst);

		lay_gridConst.gridy = 1;
		pnl_option.add(btn_guess, lay_gridConst);

		lay_gridConst.gridy = 2;
		pnl_option.add(btn_accuse, lay_gridConst);

		lay_gridConst.gridy = 3;
		lay_gridConst.weighty = 1.0;
		pnl_option.add(new JLabel(), lay_gridConst);

		/*
		 * Dice panel
		 */

		pnl_dice = new JPanel();
		pnl_dice.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		pnl_dice.setBackground(new Color(255,255,255,255));
		lbl_dice = new JLabel();
		lbl_dice.setIcon(new ImageIcon("src/Images/1.png"));
		pnl_dice.add(lbl_dice);

		// Add stuff to main panel
		pnl_side.setLayout(new GridBagLayout());

		lay_gridConst.weightx = 0.5;
		lay_gridConst.weighty = 0.0;
		lay_gridConst.insets = new Insets(15, 15, 15, 15);
		lay_gridConst.gridy = 0;
		pnl_side.add(pnl_character, lay_gridConst);

		lay_gridConst.gridy = 1;
		lay_gridConst.weighty = 1;
		pnl_side.add(pnl_option, lay_gridConst);

		lay_gridConst.weightx = 0;
		lay_gridConst.weighty = 0;
		lay_gridConst.gridy = 2;
		pnl_side.add(pnl_dice, lay_gridConst);

		// Background
		try {
			backTexture = ImageIO.read(new File("src/Images/MenuTexture.jpg"));
		} catch (IOException e) {
		}

		pack();
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // XXX
		setMinimumSize(new Dimension(800, 600));
	}

	/**
	 * Show buttons
	 */
	private void showButtons(){
		btn_viewCards.setVisible(true);
		btn_move.setVisible(true);
		btn_guess.setVisible(true);
		btn_accuse.setVisible(true);
	}

	/**
	 * Hide buttons
	 */
	private void hideButtons(){
		btn_move.setVisible(false);
		btn_guess.setVisible(false);
		btn_accuse.setVisible(false);
	}

	/**
	 * Enable buttons
	 */
	private void enableButtons(){
		btn_viewCards.setEnabled(true);
		btn_move.setEnabled(true);
		btn_guess.setEnabled(true);
		btn_accuse.setEnabled(true);

	}

	/**
	 * disable buttons
	 */
	private void disableButtons(){
		btn_viewCards.setEnabled(false);
		btn_move.setEnabled(false);
		btn_guess.setEnabled(false);
		btn_accuse.setEnabled(false);
	}

	private class BtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			case "MOVE":
				movePressed();
				break;
			case "ACCUSE":
				System.out.println("Accuse Selected");
				accusePressed();
				break;
			case "GUESS":
				System.out.println("Guess Selected");
				guessPressed();
				break;
			case "CARDS":
				System.out.println("Cards Selected");
				showCards();
				break;

			case "NEW GAME":
				newGame();
				break;
			case "EXIT GAME":
				exitGame();
				break;
			}
		}
	}

	private void movePressed(){
		if(!moveSelected && goc.canMove()){

			goc.die.roll();
			lbl_dice.setIcon(goc.die.getDiceIcon());
			if (goc.highlightValidMoves()){
				btn_guess.setEnabled(false);
				btn_accuse.setEnabled(false);
				moveSelected = true;
				can_board.setBoard(goc.getBoard());
			} else {
				btn_move.setEnabled(false);
				JOptionPane.showMessageDialog(this, " There are no valid moves");
				if (!goc.getPlayerPos().isRoom()){
					endTurn();
				}
			}
		}
	}

	private void accusePressed(){
		GuessDialog ac = new GuessDialog();
		if (goc.accuse(ac.getGuess("Accuse"))){
			System.out.println("You win");
			goc.setWinner(goc.getCurrentPlayer());//Correct set winner and end game
			endTurn();
		} else {
			System.out.println("You lost");
			String playerName = goc.getCurrentPlayer().getName();
			goc.playerLost(goc.getCurrentPlayer());//Wrong remove player from play

			endTurn();
			if (goc != null && !goc.checkGameOver()){
				can_board.setBoard(goc.getBoard());
				JOptionPane.showMessageDialog(this, playerName + " has been eliminated");
			}
		}
	}

	private void guessPressed(){
		GuessDialog gs = new GuessDialog();
		assert goc.getPlayerPos().isRoom();
		Card shownCard = goc.guess(gs.getGuess("Guess", goc.getPlayerPos().getRoom().getName()));
		if(shownCard!=null){
			System.out.println("show card");
			List<Card> cardList = new ArrayList<Card>();
			cardList.add(shownCard);
			new ShowCardsFrame(cardList, "You were shown the " + shownCard.getTitle() + " card!",
					"You were shown the " + shownCard.getTitle() + " card!\n"
							+ "This card cannot be in the envelope!");
		}
		endTurn();
	}

	private void newGame(){
		NewGameDialog ngd = new NewGameDialog();
		goc = new GameOfCluedo();
		goc.startGame(ngd.getPlayers("New Game"));
		can_board.setBoard(goc.getBoard());
		showButtons();
		enableButtons();
		btn_guess.setEnabled(false);

		ImageIcon img = new ImageIcon(myPicture[goc.getCurrentPlayer().getCharacter().ordinal()]);
		picLabel.setIcon(img);
		txt_name.setText(goc.getCurrentPlayer().getName());
		//JOptionPane.showMessageDialog(this, goc.getCurrentPlayer().getName() + "'s Turn");
		repaint();
	}


	private void exitGame(){
		this.setVisible(false);
		this.dispose();
	}

	private void showCards(){
		new ShowCardsFrame(goc.getCurrentPlayer().gethand(), goc.getCurrentPlayer().getName() + "'s Hand!");
	}

	private class BoardMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(e.toString());
			if(moveSelected){
				Position moveTo = can_board.getBoardPosition(e.getX(), e.getY());
				if(goc.validMove(moveTo)){
					goc.move(moveTo);
					moveSelected=false;
					can_board.setBoard(goc.getBoard());
					btn_accuse.setEnabled(true);
					if(!goc.getPlayerPos().isRoom()){
						endTurn();
					}else{
						btn_move.setEnabled(false);
						btn_guess.setEnabled(true);
					}
				}
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// do nothing
		}
	}

	private void endTurn(){
		if (goc.checkGameOver()){
			showGameOver();
			return;
		}

		goc.endTurn();
		can_board.setBoard(goc.getBoard());

		ImageIcon img = new ImageIcon(myPicture[goc.getCurrentPlayer().getCharacter().ordinal()]);
		picLabel.setIcon(img);
		txt_name.setText(goc.getCurrentPlayer().getName());

		//if new player not in room disable guess
		if(!goc.getPlayerPos().isRoom()){
			btn_guess.setEnabled(false);
		}else{
			btn_guess.setEnabled(true);
		}
		btn_move.setEnabled(true);
		//JOptionPane.showMessageDialog(this, goc.getCurrentPlayer().getName() + "'s Turn");
	}

	public boolean showGameOver(){
		int popup = 0;
		if (goc.getWinner() == null){
			popup = JOptionPane.showConfirmDialog(null, "Game Over!\nAll players eliminated.\n\nDo you want to play again?",
				"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		} else {
			popup = JOptionPane.showConfirmDialog(null, "Game Over!\n"+ goc.getWinner().getName() +" has guessed the envelope.\n\nDo you want to play again?",
					goc.getWinner().getName() +" Won", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}

		if(popup==JOptionPane.YES_OPTION){
			newGame();
			return true;
		} else {
			goc = null;
			can_board.setBoard(null);
			picLabel.setIcon(new ImageIcon(myPicture[myPicture.length-1]));
			txt_name.setText("Dr. Black");
			disableButtons();
			hideButtons();
			repaint();
		}
		return false;
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
