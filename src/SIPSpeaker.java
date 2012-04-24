//BASIC CHECK

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Properties;

public class SIPSpeaker extends Thread{

	public static void main(String[] args){
		boolean alreadyRead = false;
		SIPSession newSIPSession = new SIPSession();
		TCPSession newTCPSession = new TCPSession();
		VoiceCreator newVoiceMessage = new VoiceCreator();
		DatagramSocket serverSocket = null;
		String nameOfConfigurationFile = "sipspeaker.cfg";
		String sipHost =""; 
		String sipUri = "";

		if(!args.equals(null)){
			for(int i = 0 ; i<args.length ; i++){
				if(args[i].equalsIgnoreCase("-c")){
					if(!args[i+1].equalsIgnoreCase("-user")){
						nameOfConfigurationFile = args[i+1];
						System.out.println("The name of the new configuration file is: " +args[i+1]);
						try {
							readOrWriteConfig(nameOfConfigurationFile, newSIPSession, newTCPSession, newVoiceMessage);
							alreadyRead = true;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();}
					}
				}
				if(args[i].equalsIgnoreCase("-user")){
					if(!args[i+1].equalsIgnoreCase("-http")){
						sipUri = args[i+1];
						newSIPSession.setSipUser(sipUri.split("@")[0]);
						sipHost = sipUri.split("@")[1];
						newSIPSession.setSipHost(sipHost.split(":")[0]);
						newSIPSession.setSipPort(Integer.parseInt(sipHost.split(":")[1]));
					}
					newSIPSession.setSipUri(newSIPSession.getSipUser()+"@"+newSIPSession.getSipHost()+":"+newSIPSession.getSipPort());
					System.out.println("The name of the new sip uri is: " +newSIPSession.getSipUri());
				}
				if(args[i].equalsIgnoreCase("-c")){
					if(!(args[i].length() == args.length)){
						newTCPSession.setHTTPInterface(args[i+1].split(":")[0]);
						newTCPSession.setHTTPPort(Integer.parseInt(args[i+1].split(":")[1]));
						System.out.println("The web server is at: " +args[i+1]);
					}
				}
			}
		}

		if(alreadyRead == false){
			try {
				readOrWriteConfig(nameOfConfigurationFile, newSIPSession, newTCPSession, newVoiceMessage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();}
		}

		(new TCPHandler(newTCPSession, nameOfConfigurationFile)).start();

		try {
			serverSocket = new DatagramSocket(newSIPSession.getSipPort());
		} catch (SocketException e) {
			e.printStackTrace();}
		
		byte [] receiveData = new byte[2048];

		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();}
			
			new SIPHandler(receivePacket, serverSocket, newSIPSession).start();
		}
	}

	public static void readOrWriteConfig(String nameOfConfigurationFile, SIPSession newSIPSession, TCPSession newTCPSession, VoiceCreator newVoiceMessage) throws FileNotFoundException, IOException{
		Properties configArguments = new Properties();
		String hostname = "";
		try {
			configArguments.load(new FileInputStream(nameOfConfigurationFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		newSIPSession.setDefaultMessage(configArguments.getProperty("default_message"));
		newSIPSession.setCurrentMessage(configArguments.getProperty("message_wav"));
		//================================================================================
		newSIPSession.setMessageText(configArguments.getProperty("message_text"));
		newVoiceMessage.createVoiceFile(newSIPSession.getMessageText());
		//================================================================================
		newSIPSession.setSipPort(Integer.parseInt(configArguments.getProperty("sip_port")));
		newSIPSession.setSipUser(configArguments.getProperty("sip_user"));
		newSIPSession.setSipHost(configArguments.getProperty("sip_interface"));
		
		try {
		    InetAddress addr = InetAddress.getLocalHost();
		    hostname = addr.toString();
		} catch (UnknownHostException e) {
		}
		System.out.println(hostname);
		newSIPSession.setSipHost(hostname.split("/")[1]);
		newSIPSession.setSipUri(newSIPSession.getSipUser()+"@"+newSIPSession.getSipHost()+":"+newSIPSession.getSipPort());
		System.out.println("SIPURI: "+newSIPSession.getSipUri());
		
		//================================================================================
		newTCPSession.setHTTPInterface(configArguments.getProperty("http_interface"));
		newTCPSession.setHTTPPort(Integer.parseInt(configArguments.getProperty("http_port")));

		configArguments.store(new FileOutputStream(nameOfConfigurationFile), null);

		return ;
	}
}
