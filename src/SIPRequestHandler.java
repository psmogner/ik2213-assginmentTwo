
public class SIPRequestHandler {

	private SIPSession currentSIPSession;
	private String temporaryString, ringingResponse, okMessage;
	private String[] stringArray;

	public SIPRequestHandler(){
		currentSIPSession = new SIPSession();
	}

	public String inputHandler(String inputString) {

		if(inputString != null){

			if(inputString.startsWith("INVITE")){
				currentSIPSession.setStatus("INVITE");
				stringArray = inputString.split(" ");
				currentSIPSession.setTo(stringArray[1]);
				stringArray = null;
			}
			else if(inputString.startsWith("Via:")){
				currentSIPSession.setVia(inputString);
			}
			else if(inputString.startsWith("From:")){
				stringArray = inputString.split(" ");
				temporaryString = stringArray[1];
				currentSIPSession.setFrom(temporaryString.split(";")[0]);
				currentSIPSession.setFromTag(temporaryString.split(";")[1]);
				temporaryString = null;
				stringArray = null;
			}
			else if(inputString.startsWith("Call-ID:")){
				currentSIPSession.setCallID(inputString);
			}
			else if(inputString.startsWith("CSeq:")){
				stringArray = temporaryString.split(" ");
				temporaryString = stringArray[0] +" "+ stringArray[1];
				currentSIPSession.setCSeq(temporaryString);
				currentSIPSession.setCSeqAttribute(stringArray[2]);
				temporaryString = null;
				stringArray = null;
			}
			else if(inputString.startsWith("Content-Type: ")){
				temporaryString = inputString.split(" ")[1];
				currentSIPSession.setContentType(temporaryString);
				temporaryString = null;
			}
			else if(inputString.startsWith("Allow: ")){
				currentSIPSession.setAllow(inputString);
			}
			else if(inputString.startsWith("Contact: ")){
				currentSIPSession.setContact(inputString);
			}
			else if(inputString.startsWith("UserAgent: ")){
				currentSIPSession.setUserAgent(inputString);
			}
		}
		return "";
	}

	public String outputHandler() {
		if(currentSIPSession.equals("INVITE")){
			RingingResponse();
			return ringingResponse;
			
		}
		return "";
	}

	public String RingingResponse(){
		ringingResponse = null;
		
		ringingResponse += "STP/2.0 180 Ringing\n";
		ringingResponse += currentSIPSession.getVia()+"\n";
		ringingResponse += "From: "+currentSIPSession.getFrom()+";"+currentSIPSession.getFromTag()+"\n";
		ringingResponse += "To: <"+currentSIPSession.getTo()+">\n";
		ringingResponse += currentSIPSession.getCallID()+"\n";
		ringingResponse += currentSIPSession.getCSeq()+"\n";
		ringingResponse += currentSIPSession.getContact()+"\n";
		ringingResponse += currentSIPSession.getUserAgent()+"\n";
		ringingResponse += "Content-Length: 0\n";

		System.out.println("<< RINGING response created = \n"+ ringingResponse);
		return ringingResponse;

	}

	public String okMessage(){
		okMessage = null;
		
		okMessage += "SIP/2.0 200 OK\n";
		okMessage += currentSIPSession.getVia()+"\n";
		okMessage += "From: "+currentSIPSession.getTo()+">\n";
		okMessage += "To: <"+currentSIPSession.getFrom()+";"+currentSIPSession.getFromTag()+"\n";
		okMessage += currentSIPSession.getCallID()+"\n";
		okMessage += currentSIPSession.getCSeq() +" "+currentSIPSession.getCSeqAttribute()+"\n";
		okMessage += currentSIPSession.getAllow()+"\n";
		okMessage += "Accept: "+currentSIPSession.getContentType()+"\n";
		okMessage += currentSIPSession.getUserAgent()+"\n";
		okMessage += "Content-Length: 0\n";

		System.out.println("<< OK response created = \n"+ okMessage);
		return okMessage; 
	}

}
