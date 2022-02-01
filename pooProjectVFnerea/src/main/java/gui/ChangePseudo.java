package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manager.Manager;

public class ChangePseudo {

	private JFrame changePseudoFrame;
	private JPanel changePseudoPanel;
	
	private final JLabel pseudoLbl = new JLabel("<html><font size='4' color=white>Enter pseudo : </font></html>");
	private JTextField pseudo;
	
	private final JButton submitPseudo = new JButton("Submit");
	
	private final JLabel notValidFormat = new JLabel();
	private final JLabel tooLongFormat = new JLabel();
	private final JLabel alreadyUsed = new JLabel();
	
	public ChangePseudo() {
		// Creation de la fenetre
		changePseudoFrame = new JFrame("Messagerie | changePseudo page");
		changePseudoPanel = new JPanel();
		changePseudoPanel.setBackground(new Color(60,70,70));
		changePseudoFrame.setSize(700, 500);
		changePseudoFrame.add(changePseudoPanel);
		
		// Setup lable
		pseudoLbl.setBounds(250, 10, 200, 30);

		// set up the lables
		notValidFormat.setBounds(70, 100, 400, 70);
		tooLongFormat.setBounds(70, 100, 400, 70);
		alreadyUsed.setBounds(70, 100, 400, 70);
		
		// Setup text field
		pseudo = new JTextField();
		pseudo.setBounds(120, 70, 300, 30);
		
		// Setup the button 
		submitPseudo.setBounds(430, 70, 100, 30);
		submitPseudo.setBackground(new Color(0,204,136));
		submitPseudo.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				ArrayList<String> allUsers = Manager.getAllUsersConnected();
        				String pseudoText = pseudo.getText();
        				
        				Pattern p = Pattern.compile("[^A-Za-z0-9_]");
        			    Matcher m = p.matcher(pseudoText);
        			    
        			    if (pseudoText.length() <= 15) {
            			    if (!m.find()) {
            			    	if (!allUsers.contains(pseudoText)) {
            			    		Manager.updatePseudo(Manager.currentPseudo(), pseudoText);
            			    		Manager.updtPseudo(pseudoText);
            			    		new HomePage();
            			    		changePseudoFrame.setVisible(false);
            			    	}
            			    	else {
            			    		alreadyUsed.setText("<html><body><font color='red'>This pseudo is already used by an other user...</body></html>");
            			    	}
            			    }
            			    else {
            			    	notValidFormat.setText("<html><body><font color='red'>The pseudo contains not allowed characters...</body></html>");
            			    }
        			    }
        			    else {
        			    	tooLongFormat.setText("<html><body><font color='red'>The pseudo is too long, the maximum is 15 characters...</body></html>");
        			    }
        			}
				});
		
		// Add all objects and setup the frame
		changePseudoPanel.setBorder(BorderFactory.createEmptyBorder(150, 200, 150, 200));
		changePseudoPanel.setLayout(null);
		changePseudoPanel.add(pseudoLbl);
		changePseudoPanel.add(pseudo);
		changePseudoPanel.add(submitPseudo);
		changePseudoPanel.add(tooLongFormat);
		changePseudoPanel.add(notValidFormat);
		changePseudoPanel.add(alreadyUsed);
		
		changePseudoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		changePseudoFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
	}
}
