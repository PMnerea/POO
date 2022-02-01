package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import manager.ConvoManager;
import services.*;

public class ChatGUI implements ActionListener{
	public final JFrame frame;
	public final JTextArea chatArea;
	public final JTextField msg;
	public final JButton sendBtn, quitBtn;
	ArrayList <Message> history;
	ConvoManager cm;
	String distantPseudo;
	public String distantIP;
	
	
	
	public ChatGUI(ConvoManager cm, String distantPseudo, ArrayList<Message> history, String distantIP) {
		this.cm = cm;
		this.distantPseudo = distantPseudo;
		this.history = history;
		this.distantIP = distantIP;
		
	    //FRAME
		frame = new JFrame ("");
		frame.setSize(800,700);
		frame.setResizable(true);
		//

		//TEXT AREA
		chatArea = new JTextArea("");
		chatArea.setEditable(true);
		chatArea.setVisible(true);
		
		// SCROLLBAR
		JScrollPane scroll = new JScrollPane (chatArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(675,400));
		scroll.setMinimumSize(new Dimension(675,400));
		scroll.setMaximumSize(new Dimension(675, 400));
		
		// TOP PANEL
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 	
		panel.add(scroll, BorderLayout.CENTER);
		panel.setBackground(new Color(60,70,70));

		
		// BOTTOM PANEL
		JPanel bottomPanel = new JPanel(); 
		bottomPanel.setPreferredSize(new Dimension(675,200));
		bottomPanel.setMinimumSize(new Dimension(675,200));
		bottomPanel.setMaximumSize(new Dimension(675, 200));
		bottomPanel.setBackground(new Color(60,70,70));
		Box bottom = Box.createHorizontalBox();
		
		// TEXT FIELD
		msg = new JTextField();
		msg.setBounds(50, 375, 500, 30);
		msg.setPreferredSize(new Dimension(500, 30));
		msg.setMinimumSize(new Dimension(500, 30));
		msg.setMaximumSize(new Dimension(500, 30));
		
		// BUTTONS
		sendBtn = new JButton("Send");
		sendBtn.setBounds(575, 375, 150, 30);
		sendBtn.setBackground(new Color(0,204,136));	
		sendBtn.setPreferredSize(new Dimension(150, 30));
		sendBtn.setMinimumSize(new Dimension(150, 30));
		sendBtn.setMaximumSize(new Dimension(150, 30));
		
		quitBtn = new JButton("Return");
		quitBtn.setBounds(575, 450, 150, 30);
		quitBtn.setBackground(new Color(250,150,50));		
		quitBtn.setPreferredSize(new Dimension(150, 30));
		quitBtn.setMinimumSize(new Dimension(150, 30));
		quitBtn.setMaximumSize(new Dimension(150, 30));
		
		sendBtn.addActionListener(this);
		quitBtn.addActionListener(this);
		
		// ADDING ELEMENTS TO FRAME
		bottom.add(msg);
		bottom.add(sendBtn);
		bottom.add(quitBtn);
		bottomPanel.add(bottom);
		bottomPanel.setVisible(true);
		sendBtn.setVisible(true);
		quitBtn.setVisible(true);
		msg.setVisible(true);
		
		JPanel center = new JPanel();
		center.setBackground(new Color(60,70,70));
		
		frame.setBackground(new Color(60,70,70));	
		frame.add(bottomPanel, BorderLayout.PAGE_END);
		frame.add(center, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.PAGE_START);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}
	
	// Showing new message received
	public void newMessage(String message) {
		this.chatArea.setText(chatArea.getText().trim() + "\n\n  " + this.distantPseudo + " : " + message);
	}
	
	// Showing history with distant user
	public void getHistory() {
		this.chatArea.setText("");
		for (Message message : this.history) {
			System.out.println(this.distantIP + "   sender : " + message.getSender());
			if (this.distantIP.toString().equals(message.getSender().toString())) {
				this.chatArea.setText(chatArea.getText().trim() + "\n\n" + message.getDateTime() + "  " + this.distantPseudo + " : " + message.getMessage());
			} else {
				this.chatArea.setText(this.chatArea.getText().trim() + "\n\n" + message.getDateTime() + "  You : " + message.getMessage());
			}
		}
	}
	
	// Show message when distant user disconnected
	public void distantUserDisconnected() {
		this.chatArea.setText(this.distantPseudo + " disconnected from the conversation.");
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.cm.closeConversation();
	}
	
	// Show message when distant user did not accept the conversation
	public void connectionRefused() {
		this.chatArea.setText(this.distantPseudo + " did not accept your request.");
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		this.cm.closeConversation();
	}
	
	public void notYetConnected() {
		this.chatArea.setText("Waiting for " + this.distantPseudo + "... ");
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == this.sendBtn) {
			String msg_out = "";
	    	msg_out = msg.getText().trim();
	    	this.chatArea.setText(this.chatArea.getText().trim() + "\n\n  You : " + msg_out);
			this.cm.sendMessage(msg_out);
			msg.setText("");
		} else if (e.getSource() == this.quitBtn) {
			this.chatArea.setText(this.chatArea.getText().trim() + "Leaving Conversation... ");
			this.cm.sendMessage("CONVO_DISCONNECTED");
			this.cm.closeConversation();	
		}
	}	
	
	public void closeConversation(){
		this.frame.setVisible(false);
	}
	
	public static void main(String[] args) {		
	} 
}
