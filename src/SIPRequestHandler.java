
public class SIPRequestHandler {

	private SIPSession currentSIPSession;
	private String temporaryString, secondTemporaryString, ringingResponse, okMessage;
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
				System.out.println("<< Gathered info from STATUS string");
			} else if(inputString.startsWith("Via:")){
				inputString = inputString.replace("rport", "rport=5060");
				currentSIPSession.setVia(inputString);
				System.out.println("<< Gathered info from VIA string");
			} else if(inputString.startsWith("From:")){
				stringArray = inputString.split(" ");
				temporaryString = stringArray[1];
				currentSIPSession.setFrom(temporaryString.split(";")[0]);
				currentSIPSession.setFromTag(temporaryString.split(";")[1]);
				System.out.println("<< Gathered info from FROM string");
				temporaryString = null;
				stringArray = null;
			} else if(inputString.startsWith("Call-ID:")){
				currentSIPSession.setCallID(inputString);
				System.out.println("<< Gathered info from CALL-ID string");
			} else if(inputString.startsWith("CSeq:")){
				stringArray = inputString.split(" ");
				temporaryString = stringArray[0] +" "+ stringArray[1];
				currentSIPSession.setCSeq(temporaryString);
				currentSIPSession.setCSeqAttribute(stringArray[2]);
				System.out.println("<< Gathered info from CSEQ string");
				temporaryString = null;
				stringArray = null;
			} else if(inputString.startsWith("Content-Type: ")){
				temporaryString = inputString.split(" ")[1];
				currentSIPSession.setContentType(temporaryString);
				System.out.println("<< Gathered info from CONTENT-TYPE string");
				temporaryString = null;
			} else if(inputString.startsWith("Allow: ")){
				currentSIPSession.setAllow(inputString);
				System.out.println("<< Gathered info from ALLOW string");
			} else if(inputString.startsWith("Contact: ")){
				currentSIPSession.setContact(inputString);
				System.out.println("<< Gathered info from CONTACT string");
			} else if(inputString.startsWith("User-Agent: ")){
				currentSIPSession.setUserAgent(inputString);
				System.out.println("<< Gathered info from USER-AGENT string");
			} else if(inputString.startsWith("Content-Length:")){
				currentSIPSession.setContentLength(inputString);
				System.out.println("<< Gathered info from CONTENT-LENGTH string");
			} else if(inputString.startsWith("o=")){
				currentSIPSession.setOwner(inputString);
			} else if(inputString.startsWith("c=")){
				currentSIPSession.setConnection(inputString);
			} else if(inputString.startsWith("t=")){
				currentSIPSession.setTimeDescription(inputString);
			} else if(inputString.startsWith("m=")){
				currentSIPSession.setMediaDescription(inputString);
			}
		}
		return "";
	}

	public String outputHandler() {
		if(currentSIPSession.getStatus().equalsIgnoreCase("INVITE")){
			RingingResponse();
			return ringingResponse;
			
		}
		return "";
	}

	public String RingingResponse(){
		ringingResponse = "SIP/2.0 180 Ringing\n";
		ringingResponse += currentSIPSession.getVia()+"\n";
		ringingResponse += "From: "+currentSIPSession.getFrom()+";"+currentSIPSession.getFromTag()+"\n";
		ringingResponse += "To: <"+currentSIPSession.getTo()+">\n";
		ringingResponse += currentSIPSession.getCallID()+"\n";
		ringingResponse += currentSIPSession.getCSeq()+" "+currentSIPSession.getCSeqAttribute()+"\n";
		ringingResponse += currentSIPSession.getContact()+"\n";
		ringingResponse += currentSIPSession.getUserAgent()+"\n";
		ringingResponse += "Content-Length: "+currentSIPSession.getContentLength()+"\n\n";

		System.out.println("\n<< RINGING response created = \n\n"+ ringingResponse);
		return ringingResponse;
		
	}

	public String okMessage(){
		okMessage = "SIP/2.0 200 OK\n";
		okMessage += currentSIPSession.getVia()+"\n";
		okMessage += "From: <"+currentSIPSession.getFrom()+">;tag=6477225198\n";
		okMessage += "To: "+currentSIPSession.getTo()+";"+currentSIPSession.getFromTag()+"\n";
		okMessage += currentSIPSession.getCallID()+"\n";
//		okMessage += currentSIPSession.getCSeq() +" "+currentSIPSession.getCSeqAttribute()+"\n";
		okMessage += currentSIPSession.getCSeq() +" OPTIONS\n";
		okMessage += currentSIPSession.getAllow()+"\n";
		okMessage += "Accept: "+currentSIPSession.getContentType()+"\n";
		okMessage += currentSIPSession.getUserAgent()+"\n";
		okMessage += currentSIPSession.getContentLength()+"\n\n";
		okMessage += "v=0\n";
		okMessage += currentSIPSession.getOwner()+"\n";
		okMessage += "s=Talk\n";
		okMessage += currentSIPSession.getConnection()+"\n";
		okMessage += currentSIPSession.getTimeDescription()+"\n";
		okMessage += currentSIPSession.getMediaDescription()+"\n";
		okMessage += "a=rtpmap:112 speex/32000\n";
		okMessage += "a=fmtp:112 vbr=on\n";
		okMessage += "a=rtpmap:111 speex/16000\n";
		okMessage += "a=fmtp:111 vbr=on\n";
		okMessage += "a=rtpmap:110 speex/8000\n";
		okMessage += "a=fmtp:110 vbr=on\n";
		okMessage += "a=rtpmap:101 telephone-event/8000\n";
		okMessage += "a=fmtp:101 0-11\n";
		
		System.out.println("\n<< OK response created = \n\n"+ okMessage);
		return okMessage; 
	}

}
