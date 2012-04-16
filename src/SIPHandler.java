import java.net.DatagramPacket;


public class SIPHandler extends Thread{
	private DatagramPacket receivePacket;



	public SIPHandler(DatagramPacket receivePacket){
		System.out.println("New thread started");
		this.receivePacket = receivePacket;
	}

	public void run(){



		String inputData = new String(receivePacket.getData());
		//		 System.out.println(sentence);

		SIPRequestHandler handler = new SIPRequestHandler();

		System.out.println(inputData + "\n");
		
		// Store the length of the data for later use.
//		int	lengthOfData = inputData.split("\r\n|\r|\n").length;
		System.out.println(inputData + ".\n");
		inputData = inputData.replace("\n\n", "hej");
//		System.out.println("NY:" + inputData + ".\n");
		String [] splitInputData = inputData.split("\r\n|\r|\n");
		
		
		System.out.println(splitInputData.length);
		
		
		int real_length = 0;
		
		for(int i=0; i<splitInputData.length;i++){
			if(splitInputData[i].equals("") == true){
				real_length = i;
				break;
			}
		}			
		for(int i=0; i<=real_length; i++){
			handler.inputHandler(splitInputData[i]);
			System.out.println(splitInputData[i]);
		}
		handler.outputHandler("");
	}

}
