import gameOfCluedo.cards.Card;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ShowCardsFrame extends JFrame {

	public ShowCardsFrame(List<Card> cards, String title){
		setTitle(title);
		setLayout(new BorderLayout());
		//Create Card panels
		JPanel pnl_cards = new JPanel();
		pnl_cards.setLayout(new GridLayout(1,10));
		for(Card c: cards){
			JLabel cardLabel = new JLabel();
			System.out.println(c.getTitle());
			cardLabel.setIcon(new ImageIcon(c.getImage()));
			pnl_cards.add(cardLabel);
		}

		//Create OK btn
		JButton btn_ok = new JButton("OK");
		btn_ok.setPreferredSize(new Dimension(150, 30));
		btn_ok.setActionCommand("OK");
		btn_ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(pnl_cards, BorderLayout.WEST);
		add(btn_ok, BorderLayout.EAST);

		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		System.out.println("Show cards created");
	}

}
