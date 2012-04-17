import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class SIPHandler extends Thread{
	private DatagramPacket receivePacket;
	private DatagramSocket socket;

	public SIPHandler(DatagramPacket receivePacket, DatagramSocket socket){
		this.receivePacket = receivePacket;
		this.socket = socket;
	}

	public void run(){
		String inputData = new String(receivePacket.getData());
		SIPRequestHandler handler = new SIPRequestHandler();

		if(inputData.startsWith("INVITE") == true){ // Fyll på med alla commands
			System.out.println(inputData);
			String [] splitInputData = inputData.split("\r\n|\r|\n");
			for(int i=0; i<splitInputData.length; i++){
				if(splitInputData[i].equals("") == false){
					handler.inputHandler(splitInputData[i]);					
				}
			}

			String outputData = handler.outputHandler();
			String ringingMessage = handler.RingingResponse();
			String okMessage = handler.okMessage();

			DatagramPacket sendPacket = new DatagramPacket(ringingMessage.getBytes(), ringingMessage.length(), receivePacket.getAddress(), receivePacket.getPort());

			try {socket.send(sendPacket);} 
			catch (IOException e) 
			{e.printStackTrace();}

			try {Thread.sleep(1500);} 
			catch (InterruptedException e1) 
			{e1.printStackTrace();}

			sendPacket = new DatagramPacket(okMessage.getBytes(), okMessage.length(), receivePacket.getAddress(), receivePacket.getPort());

			try {
				
				socket.send(sendPacket);
				/* Wait and send wav */

				try {Thread.sleep(5000);} 
				catch (InterruptedException e1) 
				{e1.printStackTrace();}

				
				/* Send Bye msg */
				String byeMessage = handler.byeMessage();
				DatagramPacket byePacket = new DatagramPacket(byeMessage.getBytes(), byeMessage.length(), receivePacket.getAddress(), receivePacket.getPort());
				try {socket.send(byePacket);} 
				catch (IOException e) 
				{e.printStackTrace();}
			
			}catch (IOException e) 
			{e.printStackTrace();}

		}
	}
}
