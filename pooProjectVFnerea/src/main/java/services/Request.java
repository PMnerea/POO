package services;

public class Request {
	public String IPaddress;
	public int port;
	
	public Request() {
		this.IPaddress = "";
		this.port = 0;
	}
	
	public Request(String IPaddress, int port) {
		this.IPaddress = IPaddress;
		this.port = port;
	}
	
	public void setIPaddress(String IPaddress) {
		this.IPaddress = IPaddress;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String createString() {
		return this.IPaddress + " " + Integer.valueOf(this.port);
	}
}
