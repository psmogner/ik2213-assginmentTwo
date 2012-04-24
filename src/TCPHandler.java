import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPHandler extends Thread{

	private TCPSession newTCPSession;
	private Socket clientSocket = null;
	private ServerSocket serverSocket = null;
	private String nameOfConfigurationFile;

	public TCPHandler(TCPSession newTCPSession, String nameOfTheConfigurationFile){
		this.nameOfConfigurationFile = nameOfTheConfigurationFile;
		this.newTCPSession = newTCPSession;
	}

	public void run(){

		try {
			serverSocket = new ServerSocket(newTCPSession.getHTTPPort());
		} catch (IOException e) {
			e.printStackTrace();}

		while(true){
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();}
			
			(new WEBHandler(clientSocket, nameOfConfigurationFile)).start();
		}		
	}
}
