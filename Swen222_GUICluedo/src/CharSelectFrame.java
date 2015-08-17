import gameOfCluedo.GameOfCluedo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class CharSelectFrame extends JFrame implements ActionListener{

	public CharSelectFrame() {
		super("Character Selection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<JRadioButton> btnList = new ArrayList<JRadioButton>();

		JPanel pnl_panel = new JPanel();
		for(String character: GameOfCluedo.charList){
			JRadioButton btn_Char = new JRadioButton(character);
		 	//btnMissScarlet.setMnemonic(KeyEvent.VK_S);
			btn_Char.setActionCommand(character);
			btn_Char.setSelected(true);
			btn_Char.addActionListener(this);
			btnList.add(btn_Char);
			pnl_panel.add(btn_Char);
		}

		ButtonGroup btnGroup = new ButtonGroup();
		for(JRadioButton btn: btnList){
			btnGroup.add(btn);
		}

		add(pnl_panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() + " selected");

	}

}
