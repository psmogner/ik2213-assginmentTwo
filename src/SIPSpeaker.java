import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class SIPSpeaker{

	public static void main(String[] args){
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(7070);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		byte [] receiveData = new byte[2048];


		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			new SIPHandler(receivePacket, serverSocket).start();
		}		
	}
}
