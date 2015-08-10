import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CluedoFrame extends JFrame {
	private BoardCanvas canvas;
	private SidePanel sidepanel;
	
	public CluedoFrame() {
		super("Cluedo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new BoardCanvas();
		setLayout(new BorderLayout()); // use border layour

		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu, menu2;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);
		
		menu2 = new JMenu("Game");
		menuBar.add(menu2);
		add(menuBar, BorderLayout.NORTH);
		
		sidepanel = new SidePanel();
		add(sidepanel, BorderLayout.EAST);
		//
		add(canvas, BorderLayout.CENTER); // add canvas
		setSize(640, 480);
		//pack(); // pack components tightly together
		setResizable(true); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		}
}
