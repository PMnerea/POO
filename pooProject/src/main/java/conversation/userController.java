package conversation;

import tcpConnexion.TCPclient;

public class userController {
	
	public userController() {
		// Nar app startes starte skal den her lages
		// Henter alle utilisateurs connectés
		// current port: start pa 5100 feks?  ++ nar brukt til convo
		// start convoRecieved
	}
	
	public void convoRecieved() {
		// horer alltid p port 5001 feks, etter demande de convo
		// acceptConvo() nar mottatt
	}
	
	public void changePseudo() {
		// Sjekker med db om pseudo er tatt allerede
	}
	
	public void startConvo() {
		// treng en IP, sender sin port dit og start ny thread? start convoController jaff
	}
	
	public void acceptConvo() {
		// demande utilisateur si accepte ou pas
		// convoController() si oui
		// treng en port in som den høre etter meldinga, og distant port for sending av mld
		// IP local og IP distant
		// starter en TCPclient for  sende mld og en TCPserver for a motta mld (evt motsatt)
		// TCPclient client = new TCPclient(distantUser,  localPort); // client qui écoute à localPort
	}
}
