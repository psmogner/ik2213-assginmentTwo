
public class SIPRequestHandler {

	private SIPSession currentSIPSession;

	public SIPRequestHandler(){
		currentSIPSession = new SIPSession();
	}

	public String inputHandler(String inputString) {

		if(inputString != null){

			if(inputString.startsWith("INVITE")){
				System.out.println("<< Incoming Invite");
				
			}
			else if(inputString.startsWith("")){

			}
			else if(inputString.startsWith("")){

			}
			else if(inputString.startsWith("")){

			}
			else if(inputString.startsWith("")){

			}
		}
		return "";
	}

	public String outputHandler(String inputString) {
		return "";
	}


}
