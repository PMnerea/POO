package conversation;

import user.User;
import gui.*;
import message.Message;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import bddDistante.BddDistante;

// On ouvre un nouvel fenetre pour chaque convo ? 
public class Conversation {
	
	public Conversation(ArrayList<Message> history) { // User localUser, User distantUser, BddDistante bddDistante
		ConvoGui guiconvo = new ConvoGui(history); 
		
		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("waiting...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("did not wait 5 sec");
			e.printStackTrace();
		}
		
		Message newMessage = new Message("2022-01-12 00:31:55", "Tester new message", "12", "11");
		newMessage.printMessage();
		guiconvo.updateTable(newMessage);
	}
	
	public static void main (String[] args) {
		BddDistante bdd = new BddDistante();
		ArrayList<Message> history= bdd.getHistory("192.168.1.2", "192.168.2.3");
		Conversation convo = new Conversation(history);
		
		
	}
}
