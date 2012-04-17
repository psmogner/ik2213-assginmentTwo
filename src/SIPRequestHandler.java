//BASIC CHECK

public class SIPRequestHandler {

	private SIPSession currentSIPSession;
	private String ringingResponse, okMessage, byeMessage;
	private String[] stringArray;
	private final static String CRLF = "\r\n";

	public SIPRequestHandler(){
		currentSIPSession = new SIPSession();
	}

	public String inputHandler(String inputString) {

		if(inputString != null){
			if(inputString.startsWith("INVITE")){
				currentSIPSession.setSIPRequest("INVITE");
				inputString = inputString.split(" ")[1];
				stringArray = inputString.split(":");
				currentSIPSession.setSIPSpeekerSIPName(stringArray[0]+":"+stringArray[1]);
				inputString = stringArray[1];
				currentSIPSession.setSIPSpeekerIpAddress(inputString.split("@")[1]);
				currentSIPSession.setSIPSpeekerUserName(inputString.split("@")[0]);
				System.out.println("<< Gathered info from the incoming STATUS line" 
						+"\n-------------------------------------------------------"
						+"\nwhere SIPRequest is:\t" + currentSIPSession.getSIPRequest() 
						+"\nand SIPSpeekerSIPName is:\t"+currentSIPSession.getSIPSpeekerSIPName()
						+"\nand SIPSpeekerIpAddress is:\t"+currentSIPSession.getSIPSpeekerIpAddress()
						+"\n-------------------------------------------------------");
				stringArray = null;
			} else if(inputString.startsWith("Via:")){
				inputString = inputString.replace("rport", "rport=5060");
				currentSIPSession.setVia(inputString);
				System.out.println("<< Gathered info from the incoming VIA line"
						+"\n-------------------------------------------------------"
						+"\nwhere the whole VIA line is:\t"+ currentSIPSession.getVia()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("From:")){
				inputString = inputString.split(" ")[1];
				currentSIPSession.setRequestingUserTag(inputString.split(";")[1]);
				inputString = inputString.split(";")[0];
				inputString = inputString.replace("<", "");
				inputString = inputString.replace(">", "");
				currentSIPSession.setRequestingUserSIPName(inputString);
				inputString = inputString.split(":")[1];
				stringArray = inputString.split("@");
				currentSIPSession.setRequestingUserName(stringArray[0]);
				currentSIPSession.setRequestingUserIpAddress(stringArray[1]);
				System.out.println("<< Gathered info from the incoming FROM line"
						+"\n-------------------------------------------------------"
						+"\nwhere RequestingUserTag is:\t"+ currentSIPSession.getRequestingUserTag()
						+"\nand RequestingUserName is:\t"+ currentSIPSession.getRequestingUserName()
						+"\nand RequestingUserIpAddress is:\t"+ currentSIPSession.getRequestingUserIpAddress()
						+"\nand RequestingUserSIPName is:\t"+ currentSIPSession.getRequestingUserSIPName()
						+"\n-------------------------------------------------------");
				stringArray = null;
			} else if(inputString.startsWith("To:")){
				currentSIPSession.setContact(inputString.split(" ")[1]);
				System.out.println("<< Gathered info from the incoming TO line"
						+"\n-------------------------------------------------------"
						+"\nand where the whole Contact is:\t"+ currentSIPSession.getContact()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Call-ID:")){
				currentSIPSession.setCallID(inputString.split(" ")[1]);
				System.out.println("<< Gathered info from incoming CALL-ID line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Call-ID is only:\t"+ currentSIPSession.getCallID()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("CSeq:")){
				currentSIPSession.setCSeq(inputString.split(" ")[1]);
				currentSIPSession.setCSeqAttribute(inputString.split(" ")[2]);
				System.out.println("<< Gathered info from the incoming CSEQ line"
						+"\n-------------------------------------------------------"
						+"\nwhere the sequence number is:\t"+ currentSIPSession.getCSeq()
						+"\nand the sequence attribute is:\t"+ currentSIPSession.getCSeqAttribute()
						+"\n-------------------------------------------------------");
			}else if(inputString.startsWith("Content-Type: ")){
				inputString = inputString.split(" ")[1];
				currentSIPSession.setContentType(inputString);
				System.out.println("<< Gathered info from the incoming CONTENT-TYPE line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Content-Type only is:\t"+ currentSIPSession.getContentType()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Allow: ")){
				currentSIPSession.setAllow(inputString);
				System.out.println("<< Gathered info from the incoming ALLOW line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Allow is:\t"+currentSIPSession.getAllow()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Max-Forwards:")){
				currentSIPSession.setMaxForwards(inputString.split(" ")[1]);
				System.out.println("<< Gathered info from the incoming MAX-FORWARDS line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Max-Forwards is:\t"+currentSIPSession.getMaxForwards()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("User-Agent: ")){
				stringArray = inputString.split(" ");
				currentSIPSession.setUserAgent(stringArray[1] + " " + stringArray[2]);
				System.out.println("<< Gathered info from the incoming USER-AGENT line"
						+"\n-------------------------------------------------------"
						+"\nwhere the User-Agent is:\t" + currentSIPSession.getUserAgent()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Subject:")){
				currentSIPSession.setSubject(inputString.split(":")[1]);
				System.out.println("<< Gathered info from the incoming SUBJECT line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Subject is:\t" + currentSIPSession.getSubject()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Content-Length:")){
				currentSIPSession.setContentLength(inputString);
				System.out.println("<< Gathered info from the incoming CONTENT-LENGTH string"
						+"\n-------------------------------------------------------"
						+"\nwhere the Content-Length is:\t"+ currentSIPSession.getContentLength()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("o=")){
				stringArray = inputString.split(" ");
				currentSIPSession.setSession(stringArray[1]);
				System.out.println("<< Gathered info from MEDIA DESCRIPTION line (SDP)"
						+"\n-------------------------------------------------------"
						+"\nwhere the Session is:\t"+ currentSIPSession.getSession()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("m=")){
				currentSIPSession.setMediaDescription(inputString);
				System.out.println("<< Gathered info from MEDIA DESCRIPTION line (SDP)"
						+"\n-------------------------------------------------------"
						+"\nwhere the Media Description is: "+ currentSIPSession.getMediaDescription()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("t=")){
				currentSIPSession.setTimeDescription(inputString);
				System.out.println("<< Gathered info from the incoming TIME DESCRIPTION line (SDP)"
						+"\n-------------------------------------------------------"
						+"\nwhere the Time Description is: "+ currentSIPSession.getTimeDescription()
						+"\n-------------------------------------------------------");
			}
		}
		return "";
	}

	public String outputHandler() {
		//		if(currentSIPSession.getSIPRequest().equalsIgnoreCase("INVITE")){
		//			RingingResponse();
		//			return ringingResponse;
		//		}
		return "";
	}

	public String RingingResponse(){
		//=============================SIP=================================
		ringingResponse = "SIP/2.0 180 Ringing" + CRLF
				+currentSIPSession.getVia()+ CRLF
				+ "From: <" +currentSIPSession.getRequestingUserSIPName()+ ">;" +currentSIPSession.getRequestingUserTag()+ CRLF
				+ "To: <" +currentSIPSession.getSIPSpeekerSIPName()+ ">;tag=3516567874" + CRLF
				+ "Call-ID: "+currentSIPSession.getCallID()+ CRLF
				+ "CSeq: "+currentSIPSession.getCSeq()+ " " +currentSIPSession.getCSeqAttribute()+ CRLF
				+ "Contact: <" +currentSIPSession.getSIPSpeekerSIPName()+ ":5060>" + CRLF
				+ "User-Agent: " +currentSIPSession.getUserAgent()+ CRLF
				+ "Content-Length: 0" + CRLF + CRLF;

		System.out.println(CRLF + "<< RINGING response created..." + CRLF + ringingResponse);
		return ringingResponse;

	}

	public String okMessage(){

		//=============================SDP=================================		
		String SDP = "v=0" + CRLF
				+ "o="+currentSIPSession.getSIPSpeekerUserName()+ " "+currentSIPSession.getSession()+" "+currentSIPSession.getSession()+" IN IP4 " +currentSIPSession.getSIPSpeekerIpAddress()+ CRLF
				+ "s=Talk" + CRLF
				+ "c=IN IP4 " +currentSIPSession.getSIPSpeekerIpAddress()+ CRLF
				+currentSIPSession.getTimeDescription()+ CRLF
				//	+ "m=audio 7070 RTP/AVP 0" +CRLF
				//	+ "a=rtpmap:0 ULAW/8000";
				+currentSIPSession.getMediaDescription()+ CRLF
				+ "a=rtpmap:112 speex/32000"+ CRLF
				+ "a=fmtp:112 vbr=on" + CRLF
				+ "a=rtpmap:111 speex/16000" + CRLF
				+ "a=fmtp:111 vbr=on" + CRLF
				+ "a=rtpmap:110 speex/8000" + CRLF
				+ "a=fmtp:110 vbr=on" + CRLF
				+ "a=rtpmap:101 telephone-event/8000" + CRLF
				+ "a=fmtp:101 0-11" + CRLF;

		//=============================SIP=================================
		okMessage = "SIP/2.0 200 OK" + CRLF
				+currentSIPSession.getVia()+ CRLF 
				+ "From: <" +currentSIPSession.getRequestingUserSIPName()+ ">;" +currentSIPSession.getRequestingUserTag() + CRLF
				+ "To: <" +currentSIPSession.getSIPSpeekerSIPName()+ ">;tag=3516567874" + CRLF 
				+ "Call-ID: " +currentSIPSession.getCallID()+ CRLF
				+ "CSeq: " +currentSIPSession.getCSeq()+ " " +currentSIPSession.getCSeqAttribute()+ CRLF
				+ "Contact: <" +currentSIPSession.getSIPSpeekerSIPName()+">"+ CRLF
				+ "Content-Type: " +currentSIPSession.getContentType()+ CRLF
				+ "User-Agent: " +currentSIPSession.getUserAgent()+ CRLF
				+ "Content-Length: "+ SDP.length() + CRLF + CRLF + SDP;
		
		System.out.println(CRLF+"<< 200 OK response created..." + CRLF + okMessage);
		
		return okMessage; 
	}

	public String byeMessage(){
		byeMessage = "BYE " +currentSIPSession.getRequestingUserSIPName()+ " SIP/2.0" + CRLF
				+currentSIPSession.getVia()+ CRLF
				+ "From: <" +currentSIPSession.getSIPSpeekerSIPName()+ ">;tag=3516567874" + CRLF
				+ "To: <" +currentSIPSession.getRequestingUserSIPName()+ ">;" +currentSIPSession.getRequestingUserTag()+ CRLF
				+ "Call-ID: " +currentSIPSession.getCallID()+ CRLF
				+ "CSeq: 2 BYE" + CRLF
				+ "Contact: " +currentSIPSession.getContact().replace(":7070>", ">")+ CRLF
				+ "Max-Forwards: " +currentSIPSession.getMaxForwards()+ CRLF
				+ "User-Agent: " +currentSIPSession.getUserAgent()+ CRLF
				+ "Content-Length: 0" + CRLF + CRLF;

		System.out.println(CRLF+"<< BYE response created..." + CRLF + byeMessage);

		return byeMessage;
	}
}