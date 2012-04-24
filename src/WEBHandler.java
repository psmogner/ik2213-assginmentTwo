import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class WEBHandler extends Thread {
	public String nameOfConfigurationFile;
	private Socket clientSocket;
	private ProtocolHandler newHandler;

	public WEBHandler(Socket clientSocket, String nameOfConfigurationFile) {
		this.clientSocket = clientSocket;
		this.nameOfConfigurationFile = nameOfConfigurationFile;
	}

	public void run(){
		BufferedReader in = null;
		PrintWriter out = null;
		char[] incomingData = new char[5000];
		newHandler = new ProtocolHandler(nameOfConfigurationFile);
		
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			int numberOfCharsInData=0, lengthOfData;
			String inputOfData="";
			String outputOfData, linesRead;

			// While we have something to read from the clients browser request we do so and store it.
			while(in.ready() != false){
				numberOfCharsInData = in.read(incomingData, 0, 5000);
				inputOfData = inputOfData + String.valueOf(incomingData, 0, numberOfCharsInData);
			}
			// Store the length of the data for later use.
			lengthOfData = inputOfData.split("\r\n|\r|\n").length;

			// First a security check to make sure that no variable is null and responsible to a nullpoiner exception.
			if(inputOfData != null && (inputOfData.startsWith("GET") == true || inputOfData.startsWith("POST") == true)){

				// For the data earlier read we read it linewise and send it to our ProtocolHandler.
				for(int i=0; i<lengthOfData; i++){
					linesRead = inputOfData.split("\r\n|\r|\n")[i];
					newHandler.inputHandler(linesRead);
				}

				// We get the appropriate response from the protocol handler after that.
				outputOfData = newHandler.outputHandler();
				out.print(outputOfData);
			}else{
				System.out.println("<There is no data to read.");
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}	
		//Closing the socket
		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
