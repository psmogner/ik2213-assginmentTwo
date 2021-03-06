//BASIC CHECK

public class SIPSession {

	private String SIPSpeekerSIPName;
	private String SIPRequest;
	private String SIPSpeekerUserName;
	private String SIPSpeekerIpAddress;
	private String RequestingUserTag;
	private String RequestingUserName;
	private String RequestingUserSIPName;
	private String RequestingUserIpAddress;
	private String SIPSpeekerAlias;
	private String CallID;
	private String CSeq;
	private String CSeqAttribute;
	private String Allow;
	private String ContentType;
	private String MaxForwards;
	private String UserAgent;
	private String Subject;
	private String ContentLength;
	private String TimeDescription;
	private String MediaDescription;
	private String Via;
	private String Session;
	private String Contact;
	private String messageText;
	private String sipUser;
	private String sipHost = "127.0.0.1"; 
	private int sipPort = 5060;
	private String sipUri;
	private String configFileName;
	private String DefaultMessage;
	private String CurrentMessage;
	private String MessageRecieved;


	public String getConfigFileName() {
		return configFileName;
	}
	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
	public String getSipUser() {
		return sipUser;
	}
	public void setSipUser(String sipUser) {
		this.sipUser = sipUser;
	}
	public String getSipHost() {
		return sipHost;
	}
	public void setSipHost(String sipHost) {
		this.sipHost = sipHost;
	}
	public int getSipPort() {
		return sipPort;
	}
	public void setSipPort(int sipPort) {
		this.sipPort = sipPort;
	}
	public String getSipUri() {
		return sipUri;
	}
	public void setSipUri(String sipUri) {
		this.sipUri = sipUri;
	}
	
	public String getSIPSpeekerSIPName() {
		return SIPSpeekerSIPName;
	}
	public void setSIPSpeekerSIPName(String sIPSpeekerSIPName) {
		SIPSpeekerSIPName = sIPSpeekerSIPName;
	}
	public String getSIPRequest() {
		return SIPRequest;
	}
	public void setSIPRequest(String sIPRequest) {
		SIPRequest = sIPRequest;
	}
	public String getRequestingUserTag() {
		return RequestingUserTag;
	}
	public void setRequestingUserTag(String requestingUserTag) {
		RequestingUserTag = requestingUserTag;
	}
	public String getRequestingUserName() {
		return RequestingUserName;
	}
	public void setRequestingUserName(String requestingUserName) {
		RequestingUserName = requestingUserName;
	}
	public String getRequestingUserSIPName() {
		return RequestingUserSIPName;
	}
	public void setRequestingUserSIPName(String requestingUserSIPName) {
		RequestingUserSIPName = requestingUserSIPName;
	}
	public String getRequestingUserIpAddress() {
		return RequestingUserIpAddress;
	}
	public void setRequestingUserIpAddress(String requestingUserIpAddress) {
		RequestingUserIpAddress = requestingUserIpAddress;
	}
	public String getSIPSpeekerAlias() {
		return SIPSpeekerAlias;
	}
	public void setSIPSpeekerAlias(String sIPSpeekerAlias) {
		SIPSpeekerAlias = sIPSpeekerAlias;
	}
	public String getCallID() {
		return CallID;
	}
	public void setCallID(String callID) {
		CallID = callID;
	}
	public String getCSeq() {
		return CSeq;
	}
	public void setCSeq(String cSeq) {
		CSeq = cSeq;
	}
	public String getCSeqAttribute() {
		return CSeqAttribute;
	}
	public void setCSeqAttribute(String cSeqAttribute) {
		CSeqAttribute = cSeqAttribute;
	}
	public String getAllow() {
		return Allow;
	}
	public void setAllow(String allow) {
		Allow = allow;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public String getMaxForwards() {
		return MaxForwards;
	}
	public void setMaxForwards(String maxForwards) {
		MaxForwards = maxForwards;
	}
	public String getUserAgent() {
		return UserAgent;
	}
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getContentLength() {
		return ContentLength;
	}
	public void setContentLength(String contentLength) {
		ContentLength = contentLength;
	}
	public String getTimeDescription() {
		return TimeDescription;
	}
	public void setTimeDescription(String timeDescription) {
		TimeDescription = timeDescription;
	}
	public String getMediaDescription() {
		return MediaDescription;
	}
	public void setMediaDescription(String mediaDescription) {
		MediaDescription = mediaDescription;
	}
	public String getVia() {
		return Via;
	}
	public void setVia(String via) {
		Via = via;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getSIPSpeekerUserName() {
		return SIPSpeekerUserName;
	}
	public void setSIPSpeekerUserName(String sIPSpeekerUserName) {
		SIPSpeekerUserName = sIPSpeekerUserName;
	}
	public String getSIPSpeekerIpAddress() {
		return SIPSpeekerIpAddress;
	}
	public void setSIPSpeekerIpAddress(String sIPSpeekerIpAddress) {
		SIPSpeekerIpAddress = sIPSpeekerIpAddress;
	}
	public String getSession() {
		return Session;
	}
	public void setSession(String session) {
		Session = session;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getDefaultMessage() {
		return DefaultMessage;
	}
	public void setDefaultMessage(String defaultMessage) {
		DefaultMessage = defaultMessage;
	}
	public String getCurrentMessage() {
		return CurrentMessage;
	}
	public void setCurrentMessage(String currentMessage) {
		CurrentMessage = currentMessage;
	}
	public String getMessageRecieved() {
		return MessageRecieved;
	}
	public void setMessageRecieved(String messageRecieved) {
		MessageRecieved = messageRecieved;
	}

}