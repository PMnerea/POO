package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manager.RequestManager; 

public class AcceptRequest implements ActionListener{
	private JFrame requestFrame;
	private JPanel requestPanel; 
	private final JButton accept = new JButton("Accept");
	private final JButton deny = new JButton("Deny");
	private RequestManager rm;
	private final JLabel request = new JLabel();
	
	public AcceptRequest(RequestManager rm, String pseudo) {
		this.rm = rm;
		
		// Creation de la fenetre
		requestFrame = new JFrame("New conversation request");
		requestPanel = new JPanel();
		request.setBounds(70, 50, 300, 70);
		request.setText(pseudo + " wants to start a conversation");
		requestPanel.setBackground(new Color(60,70,70));
		requestFrame.setSize(600, 450);
		
		// Configuring buttons
		this.accept.addActionListener(this);
		this.deny.addActionListener(this);
		accept.setBounds(20, 100, 150, 40);
		deny.setBounds(200, 100, 150, 40);
		
		
		// Adding the elements to the frame
		requestFrame.add(request);
		requestFrame.add(accept);
		requestFrame.add(deny);
		
		requestFrame.setLayout(null);
		requestFrame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Reading response from user and sending to RequestManager
		if (e.getSource() == accept) {
			this.rm.setResponse(1);
		} else if (e.getSource() == deny) {
			this.rm.setResponse(-1);
		} else {
			System.out.println("[AcceptRequest] Trouble getting response on request");
		}
	}
	
	public void closeWindow() {
		requestFrame.setVisible(false);
	}
	
	
}
