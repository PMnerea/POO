package gui;

import javax.swing.*;
import java.awt.*;

public class TestScroll {
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JScrollPane scroll = new JScrollPane(panel);
	
	private final JLabel title = new JLabel("<html><font size='4' color=white>Chat with other users : </font></html>");
	
	private final JButton changePseudo = new JButton("Change pseudo");
	private final JButton deconnexion = new JButton("Quit");
	
	public TestScroll() {
		panel.setLayout(new GridLayout(20,20));
		frame.setSize(700, 500);
		frame.add(panel);
		
		int n = 0;
		for(int i=0; i<20; i++) {
			for(int j=0; j<20; j++) {
				panel.add(new JButton("Bouton" + n));
				n++;
			}
		}
		
		panel.add(scroll);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new TestScroll();
	}

}
