package services;

public class Message {
	
	private String dateTime;
	private String message;
	private String senderIP; 
	private String receiverIP; 
	
	public Message() {
		this.dateTime = "";
		this.message = "";
		this.senderIP = "";
		this.receiverIP = "";
	}
	
	public Message(String dateTime, String message, String senderIP, String receiverIP) {
		this.dateTime = dateTime;
		this.message = message;
		this.senderIP = senderIP;
		this.receiverIP = receiverIP;
	}
	
	
	public void setMessage(String message) {
		this.message = message; 
	}
	
	public void setSender(String senderIP) {
		this.senderIP = senderIP; 
	}
	
	public void setReceiver(String receiverIP) {
		this.receiverIP = receiverIP; 
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime; 
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getSender() {
		return this.senderIP;
	}
	
	public String getDateTime() {
		return this.dateTime;
	}
	
	public void printMessage() {
		System.out.println(this.dateTime);
		System.out.println("FROM: " + this.senderIP);
		System.out.println("TO: " + this.receiverIP);
		System.out.println(this.message);
		
	}
}
