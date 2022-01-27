package gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.*;
import Manager.Manager;

public class Login {
	
	private JFrame loginFrame;
	private JPanel loginPanel;
	
	private final JLabel pseudoLbl = new JLabel("<html><font size='4' color=white>Enter pseudo : </font></html>");
	private JTextField pseudo;
	
	private final JButton submitPseudo = new JButton("Submit");
	
	private final JLabel notValidFormat = new JLabel();
	private final JLabel tooLongFormat = new JLabel();
	private final JLabel alreadyUsed = new JLabel();
	
	public Login() {
		// Creation de la fenetre
		loginFrame = new JFrame("Messagerie | Login page");
		loginPanel = new JPanel();
		loginPanel.setBackground(new Color(60,70,70));
		loginFrame.setSize(700, 500);
		loginFrame.add(loginPanel);
		
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
            			    		Manager.initApp(pseudoText);
            			    		new HomePage();
            			    		loginFrame.setVisible(false);
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
		loginPanel.setBorder(BorderFactory.createEmptyBorder(150, 200, 150, 200));
		loginPanel.setLayout(null);
		loginPanel.add(pseudoLbl);
		loginPanel.add(pseudo);
		loginPanel.add(submitPseudo);
		loginPanel.add(tooLongFormat);
		loginPanel.add(notValidFormat);
		loginPanel.add(alreadyUsed);
		
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Login();
	}

}
