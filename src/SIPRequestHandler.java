import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//BASIC CHECK

public class SIPRequestHandler {

	private SIPSession newSIPSession;
	private String ringingResponse, okMessage, byeMessage;
	private String[] stringArray;
	private final static String CRLF = "\r\n";

	public SIPRequestHandler(){
		this.newSIPSession = new SIPSession();
	}

	public String inputHandler(String inputString) {

		if(inputString != null){
			if(inputString.startsWith("INVITE")){
				newSIPSession.setSIPRequest("INVITE");
				inputString = inputString.split(" ")[1];
				stringArray = inputString.split(":");
				newSIPSession.setSIPSpeekerSIPName(stringArray[0]+":"+stringArray[1]);
				inputString = stringArray[1];
				newSIPSession.setSIPSpeekerIpAddress(inputString.split("@")[1]);
				newSIPSession.setSIPSpeekerUserName(inputString.split("@")[0]);
				System.out.println("<< Gathered info from the incoming STATUS line" 
						+"\n-------------------------------------------------------"
						+"\nwhere SIPRequest is:\t" + newSIPSession.getSIPRequest() 
						+"\nand SIPSpeekerSIPName is:\t"+newSIPSession.getSIPSpeekerSIPName()
						+"\nand SIPSpeekerIpAddress is:\t"+newSIPSession.getSIPSpeekerIpAddress()
						+"\n-------------------------------------------------------");
				stringArray = null;
			} else if(inputString.startsWith("Via:")){
				inputString = inputString.replace("rport", "rport=5060");
				newSIPSession.setVia(inputString);
				System.out.println("<< Gathered info from the incoming VIA line"
						+"\n-------------------------------------------------------"
						+"\nwhere the whole VIA line is:\t"+ newSIPSession.getVia()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("From:")){
				inputString = inputString.split(" ")[1];
				newSIPSession.setRequestingUserTag(inputString.split(";")[1]);
				inputString = inputString.split(";")[0];
				inputString = inputString.replace("<", "");
				inputString = inputString.replace(">", "");
				newSIPSession.setRequestingUserSIPName(inputString);
				inputString = inputString.split(":")[1];
				stringArray = inputString.split("@");
				newSIPSession.setRequestingUserName(stringArray[0]);
				newSIPSession.setRequestingUserIpAddress(stringArray[1]);
				System.out.println("<< Gathered info from the incoming FROM line"
						+"\n-------------------------------------------------------"
						+"\nwhere RequestingUserTag is:\t"+ newSIPSession.getRequestingUserTag()
						+"\nand RequestingUserName is:\t"+ newSIPSession.getRequestingUserName()
						+"\nand RequestingUserIpAddress is:\t"+ newSIPSession.getRequestingUserIpAddress()
						+"\nand RequestingUserSIPName is:\t"+ newSIPSession.getRequestingUserSIPName()
						+"\n-------------------------------------------------------");
				stringArray = null;
			} else if(inputString.startsWith("To:")){
				newSIPSession.setContact(inputString.split(" ")[1]);
				System.out.println("<< Gathered info from the incoming TO line"
						+"\n-------------------------------------------------------"
						+"\nand where the whole Contact is:\t"+ newSIPSession.getContact()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Call-ID:")){
				newSIPSession.setCallID(inputString.split(" ")[1]);
				System.out.println("<< Gathered info from incoming CALL-ID line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Call-ID is only:\t"+ newSIPSession.getCallID()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("CSeq:")){
				newSIPSession.setCSeq(inputString.split(" ")[1]);
				newSIPSession.setCSeqAttribute(inputString.split(" ")[2]);
				System.out.println("<< Gathered info from the incoming CSEQ line"
						+"\n-------------------------------------------------------"
						+"\nwhere the sequence number is:\t"+ newSIPSession.getCSeq()
						+"\nand the sequence attribute is:\t"+ newSIPSession.getCSeqAttribute()
						+"\n-------------------------------------------------------");
			}else if(inputString.startsWith("Content-Type: ")){
				inputString = inputString.split(" ")[1];
				newSIPSession.setContentType(inputString);
				System.out.println("<< Gathered info from the incoming CONTENT-TYPE line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Content-Type only is:\t"+ newSIPSession.getContentType()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Allow: ")){
				newSIPSession.setAllow(inputString);
				System.out.println("<< Gathered info from the incoming ALLOW line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Allow is:\t"+newSIPSession.getAllow()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Max-Forwards:")){
				newSIPSession.setMaxForwards(inputString.split(" ")[1]);
				System.out.println("<< Gathered info from the incoming MAX-FORWARDS line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Max-Forwards is:\t"+newSIPSession.getMaxForwards()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("User-Agent: ")){
				stringArray = inputString.split(" ");
				newSIPSession.setUserAgent(stringArray[1] + " " + stringArray[2]);
				System.out.println("<< Gathered info from the incoming USER-AGENT line"
						+"\n-------------------------------------------------------"
						+"\nwhere the User-Agent is:\t" + newSIPSession.getUserAgent()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Subject:")){
				newSIPSession.setSubject(inputString.split(":")[1]);
				System.out.println("<< Gathered info from the incoming SUBJECT line"
						+"\n-------------------------------------------------------"
						+"\nwhere the Subject is:\t" + newSIPSession.getSubject()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("Content-Length:")){
				newSIPSession.setContentLength(inputString);
				System.out.println("<< Gathered info from the incoming CONTENT-LENGTH string"
						+"\n-------------------------------------------------------"
						+"\nwhere the Content-Length is:\t"+ newSIPSession.getContentLength()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("o=")){
				stringArray = inputString.split(" ");
				newSIPSession.setSession(stringArray[1]);
				System.out.println("<< Gathered info from MEDIA DESCRIPTION line (SDP)"
						+"\n-------------------------------------------------------"
						+"\nwhere the Session is:\t"+ newSIPSession.getSession()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("m=")){
				newSIPSession.setMediaDescription(inputString);
				System.out.println("<< Gathered info from MEDIA DESCRIPTION line (SDP)"
						+"\n-------------------------------------------------------"
						+"\nwhere the Media Description is: "+ newSIPSession.getMediaDescription()
						+"\n-------------------------------------------------------");
			} else if(inputString.startsWith("t=")){
				newSIPSession.setTimeDescription(inputString);
				System.out.println("<< Gathered info from the incoming TIME DESCRIPTION line (SDP)"
						+"\n-------------------------------------------------------"
						+"\nwhere the Time Description is: "+ newSIPSession.getTimeDescription()
						+"\n-------------------------------------------------------");
			}
		}
		return "";
	}

	public String outputHandler(){
		return "";
	}

	public SIPSession getSessionInfo(){
		return newSIPSession;
	}
	
	public String RingingResponse(){
		//=============================SIP=================================
		ringingResponse = "SIP/2.0 180 Ringing" + CRLF
				+newSIPSession.getVia()+ CRLF
				+ "From: <" +newSIPSession.getRequestingUserSIPName()+ ">;" +newSIPSession.getRequestingUserTag()+ CRLF
				+ "To: <" +newSIPSession.getSIPSpeekerSIPName()+ ">;tag=3516567874" + CRLF
				+ "Call-ID: "+newSIPSession.getCallID()+ CRLF
				+ "CSeq: "+newSIPSession.getCSeq()+ " " +newSIPSession.getCSeqAttribute()+ CRLF
				+ "Contact: <" +newSIPSession.getSIPSpeekerSIPName()+ ":5060>" + CRLF
				+ "User-Agent: " +newSIPSession.getUserAgent()+ CRLF
				+ "Content-Length: 0" + CRLF + CRLF;

		System.out.println(CRLF + "<< RINGING response created..." + CRLF + ringingResponse);
		return ringingResponse;

	}

	public String okMessage(){

		//=============================SDP=================================		
		String SDP = "v=0" + CRLF
				+ "o="+newSIPSession.getSIPSpeekerUserName()+ " "+newSIPSession.getSession()+" "+newSIPSession.getSession()+" IN IP4 " +newSIPSession.getSIPSpeekerIpAddress()+ CRLF
				+ "s=Talk" + CRLF
				+ "c=IN IP4 " +newSIPSession.getSIPSpeekerIpAddress()+ CRLF
				+newSIPSession.getTimeDescription()+ CRLF
				+ "m=audio 7078 RTP/AVP 0 3" + CRLF
				//	+ "m=audio 7070 RTP/AVP 0" +CRLF
				//	+ "a=rtpmap:0 ULAW/8000";
				//  +currentSIPSession.getMediaDescription()+ CRLF
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
				+newSIPSession.getVia()+ CRLF 
				+ "From: <" +newSIPSession.getRequestingUserSIPName()+ ">;" +newSIPSession.getRequestingUserTag() + CRLF
				+ "To: <" +newSIPSession.getSIPSpeekerSIPName()+ ">;tag=3516567874" + CRLF 
				+ "Call-ID: " +newSIPSession.getCallID()+ CRLF
				+ "CSeq: " +newSIPSession.getCSeq()+ " " +newSIPSession.getCSeqAttribute()+ CRLF
				+ "Contact: <" +newSIPSession.getSIPSpeekerSIPName()+">"+ CRLF
				+ "Content-Type: " +newSIPSession.getContentType()+ CRLF
				+ "User-Agent: " +newSIPSession.getUserAgent()+ CRLF
				+ "Content-Length: "+ SDP.length() + CRLF + CRLF + SDP;

		System.out.println(CRLF+"<< 200 OK response created..." + CRLF + okMessage);

		return okMessage; 
	}

	public String byeMessage(){
		byeMessage = "BYE " +newSIPSession.getRequestingUserSIPName()+ " SIP/2.0" + CRLF
				+newSIPSession.getVia()+ CRLF
				+ "From: <" +newSIPSession.getSIPSpeekerSIPName()+ ">;tag=3516567874" + CRLF
				+ "To: <" +newSIPSession.getRequestingUserSIPName()+ ">;" +newSIPSession.getRequestingUserTag()+ CRLF
				+ "Call-ID: " +newSIPSession.getCallID()+ CRLF
				+ "CSeq: 21 BYE" + CRLF
				+ "Contact: " +newSIPSession.getContact().replace(":7070>", ">")+ CRLF
				+ "Max-Forwards: " +newSIPSession.getMaxForwards()+ CRLF
				+ "User-Agent: " +newSIPSession.getUserAgent()+ CRLF
				+ "Content-Length: 0" + CRLF + CRLF;

		System.out.println(CRLF+"<< BYE response created..." + CRLF + byeMessage);

		return byeMessage;
	}

}