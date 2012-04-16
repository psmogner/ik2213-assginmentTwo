import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class SIPHandler extends Thread{
	private DatagramPacket receivePacket;
	private DatagramSocket socket;



	public SIPHandler(DatagramPacket receivePacket, DatagramSocket socket){
		System.out.println("New thread started");
		this.receivePacket = receivePacket;
		this.socket = socket;
	}

	public void run(){



		String inputData = new String(receivePacket.getData());
		SIPRequestHandler handler = new SIPRequestHandler();
		
		// Store the length of the data for later use.
//		System.out.println(inputData + ".\n");
		
		if(inputData.startsWith("INVITE") == true  ){ // Fyll på med alla commands
			System.out.println("Command check");
			System.out.println(inputData);
			
			
			String [] splitInputData = inputData.split("\r\n|\r|\n");
			
			for(int i=0; i<splitInputData.length; i++){
				if(splitInputData[i].equals("") == false){
					handler.inputHandler(splitInputData[i]);					
				}
//				System.out.println(splitInputData[i]);
			}
			String outputData = handler.outputHandler();
			
			String ringingMessage = handler.RingingResponse();
			String okMessage = handler.okMessage();
			
		
			
			DatagramPacket sendPacket = new DatagramPacket(ringingMessage.getBytes(), ringingMessage.length(), receivePacket.getAddress(), receivePacket.getPort());
			try {
				socket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sendPacket = new DatagramPacket(okMessage.getBytes(), okMessage.length(), receivePacket.getAddress(), receivePacket.getPort());
			try {
				socket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
					
		}else{
			System.out.println("I else i slutet do nothing!");
		}
		

	}

}
