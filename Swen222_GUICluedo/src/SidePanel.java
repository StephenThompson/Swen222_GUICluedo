import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class SidePanel extends JPanel{

	public SidePanel(){
		JButton viewCards = new JButton("View Cards");
		JButton move = new JButton("Move");
		JButton guess = new JButton("Guess");
		JButton accuse = new JButton("Accuse");

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // use border layour
		add(viewCards);
		add(move);
		add(guess);
		add(accuse);
	}
}
