import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


public class ProtocolHandler {

	String htmlfile="";
	String Request;
	String newMessageText;
	String nameOfConfigurationFile;

	public ProtocolHandler(String nameOfConfigurationFile){
		this.nameOfConfigurationFile = nameOfConfigurationFile;
	}

	public String inputHandler(String inputString){
		if(inputString != null){
			if(inputString.startsWith("GET")){
				Request = "GET";
			}else if(inputString.startsWith("POST")){
				Request = "POST";
			}else if(inputString.startsWith("message=")){
				inputString = inputString.replace("+", " ");
				newMessageText = inputString.split("=")[1];
			}
		}
		return "";
	}

	public String outputHandler() throws IOException{
		String response ="";


		if(Request.equalsIgnoreCase("GET")){
			readOrWriteConfig(nameOfConfigurationFile, true);
			reLoadHTML(newMessageText);

		} else if(Request.equalsIgnoreCase("POST")){
			System.out.println("newMessageText: " +newMessageText);
			readOrWriteConfig(nameOfConfigurationFile, false);
			System.out.println("nameOfConfigurationFile: " +nameOfConfigurationFile);
			reLoadHTML(newMessageText);
		}

		response = "HTTP/1.0 200 OK\r\n";
		response += "Content-Type: text/html\r\n";
		response += "Content-Length " + htmlfile.length() + "\r\n";
		response += "Server: IK2213A2 Server \r\n";
		response += "Connection: Close \r\n";
		response += "\r\n";
		response += htmlfile;

		return response;
	}

	private String reLoadHTML(String counterMeasure){
		BufferedReader output = null;
		String line;
		htmlfile = "";

		try {
			output = new BufferedReader(new FileReader("index.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();}

		try {
			while((line = output.readLine()) != null){
				if(line.contains("</form>")){
					line = "</form><center>" +counterMeasure+ "</center>";
				}
				htmlfile += line + "\r\n";
			}
		} catch (IOException e) {
			e.printStackTrace();}

		return "";
	}

	public void readOrWriteConfig(String nameOfConfigurationFile, boolean readFrom) throws IOException{
		Properties arguments = new Properties();

		try {
			arguments.load(new FileInputStream(nameOfConfigurationFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(readFrom == true){
			newMessageText = arguments.getProperty("message_text");
		} else {
			arguments.setProperty("message_text", newMessageText);
			VoiceCreator temporaryCreation = new VoiceCreator();
			temporaryCreation.createVoiceFile(newMessageText);
		}

		arguments.store(new FileOutputStream(nameOfConfigurationFile), null);
		return ;
	}
}
