import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class SIPSpeaker implements Runnable{

	public static void main(String[] args){
		System.out.println("The server is now running!");
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(7070);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		byte [] receiveData = new byte[1024];
//		byte[] sendData = new byte[1024];
			
		
       while(true)
          {
             DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
             try {
            	// System.out.println("Innan receive");
				serverSocket.receive(receivePacket);
			//	System.out.println("Efter receive");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("packet" + receivePacket.getLength());
			
			new SIPHandler(receivePacket, serverSocket).start();
			
			
			
			
			
			
//             String sentence = new String( receivePacket.getData());
//             System.out.println("RECEIVED: " + sentence);
//             InetAddress IPAddress = receivePacket.getAddress();
//             int port = receivePacket.getPort();
//             String capitalizedSentence = sentence.toUpperCase();
//             sendData = capitalizedSentence.getBytes();
             
             
//             DatagramPacket sendPacket =
//             new DatagramPacket(sendData, sendData.length, IPAddress, port);
//             serverSocket.send(sendPacket);
          }
		
		
		
	}

	@Override
	public void run() {
		
	}
}

//							UDPSERVER
//===============================================================
//import java.io.*;
//import java.net.*;
//
//class UDPServer
//{
//   public static void main(String args[]) throws Exception
//      {
//         DatagramSocket serverSocket = new DatagramSocket(9876);
//            byte[] receiveData = new byte[1024];
//            byte[] sendData = new byte[1024];
//            while(true)
//               {
//                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//                  serverSocket.receive(receivePacket);
//                  String sentence = new String( receivePacket.getData());
//                  System.out.println("RECEIVED: " + sentence);
//                  InetAddress IPAddress = receivePacket.getAddress();
//                  int port = receivePacket.getPort();
//                  String capitalizedSentence = sentence.toUpperCase();
//                  sendData = capitalizedSentence.getBytes();
//                  DatagramPacket sendPacket =
//                  new DatagramPacket(sendData, sendData.length, IPAddress, port);
//                  serverSocket.send(sendPacket);
//               }
//      }
//}