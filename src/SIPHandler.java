//BASIC CHECK

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.media.CannotRealizeException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.format.UnsupportedFormatException;
import javax.media.rtp.InvalidSessionAddressException;

public class SIPHandler extends Thread{
	private DatagramPacket receivePacket;
	private DatagramSocket socket;
	private SIPSession newSIPSession;

	public SIPHandler(DatagramPacket receivePacket, DatagramSocket socket, SIPSession newSIPSession){
		this.receivePacket = receivePacket;
		this.socket = socket;
		this.newSIPSession = newSIPSession;
	}

	public void run(){
		String inputData = new String(receivePacket.getData());
		SIPRequestHandler handler = new SIPRequestHandler();

		if(inputData.startsWith("INVITE") == true){ 
			System.out.println(inputData);
			String [] splitedInputData = inputData.split("\r\n|\r|\n");
			for(int i=0; i<splitedInputData.length; i++){
				if(splitedInputData[i].equals("") == false ){
					if(splitedInputData[i].startsWith("INVITE ")){
						splitedInputData[i] = "INVITE sip:" +newSIPSession.getSipUri()+ " SIP/2.0";	
					}
					if(splitedInputData[i].startsWith("To: ")){
						splitedInputData[i] = "To: <"+newSIPSession.getSipUri()+">";
					}
					handler.inputHandler(splitedInputData[i]);					
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
				/* Declare media processor to convert wav format to suitable RTP format */
				/* Datasink is our output block */
				/* Medialocator is the destination of the datasink*/

				socket.send(sendPacket);
				SIPSession wierdSessionInfo = handler.getSessionInfo();
				RTPSender newRTPSender = null;			
				try {
					try {
						newRTPSender = new RTPSender(wierdSessionInfo.getRequestingUserIpAddress(), 7078);
					} catch (InvalidSessionAddressException e) {
						e.printStackTrace();
					} catch (UnsupportedFormatException e) {
						e.printStackTrace();}

				} catch (NoProcessorException e2) {
					e2.printStackTrace();
				} catch (NoDataSourceException e2) {
					e2.printStackTrace();
				} catch (CannotRealizeException e2) {
					e2.printStackTrace();}

				newRTPSender.startRTP();
				System.out.println("Starting stream.");

				try {
					Thread.sleep(newRTPSender.getFileLengthInSeconds()+1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();}

				System.out.println("Stoping stream.");
				newRTPSender.stopRTP();
				
				String byeMessage = handler.byeMessage();
				DatagramPacket byePacket = new DatagramPacket(byeMessage.getBytes(), byeMessage.length(), receivePacket.getAddress(), receivePacket.getPort());
				try {
					socket.send(byePacket);
				} catch (IOException e) 
				{e.printStackTrace();}

			}catch (IOException e) 
			{e.printStackTrace();}
		}
	}
}
