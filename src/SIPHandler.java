import java.net.DatagramPacket;


public class SIPHandler extends Thread{
	private DatagramPacket receivePacket;
	
	
	
	public SIPHandler(DatagramPacket receivePacket){
		System.out.println("New thread started");
		this.receivePacket = receivePacket;
	}
	
	public void run(){
		
		
		
		 String sentence = new String(receivePacket.getData());
//		 System.out.println(sentence);
		 
		 
		
		
		
	}

}
