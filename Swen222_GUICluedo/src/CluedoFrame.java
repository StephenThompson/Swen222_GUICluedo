import gameOfCluedo.GameOfCluedo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class CluedoFrame extends JFrame {
	private BoardCanvas canvas;
	private SidePanel sidepanel;
	private GameOfCluedo goc;

	public CluedoFrame() {
		super("Cluedo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new BoardCanvas();
		setLayout(new BorderLayout()); // use border layour

		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu, menu2;
		JMenuItem newGame;
		JMenuItem exitGame;
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);

		menu2 = new JMenu("Game");
		menuBar.add(menu2);
		add(menuBar, BorderLayout.NORTH);

		newGame = new JMenuItem("New Game");
		exitGame = new JMenuItem("Exit");

		menu.add(newGame);
		menu.add(exitGame);

		sidepanel = new SidePanel();
		add(sidepanel, BorderLayout.EAST);
		//
		add(canvas, BorderLayout.CENTER); // add canvas
		//setSize(640, 480);
		pack(); // pack components tightly together
		setResizable(true); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}

	public boolean showGameOver(){
		int popup = JOptionPane.showConfirmDialog(null, "Game Over!\nAll players eliminated.\n\nDo you want to play again?",
				"You Won", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(popup==JOptionPane.YES_OPTION){
			return true;
		}
		return false;
	}
}
