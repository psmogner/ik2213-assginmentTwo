
public class SIPSession {

	private String status;
	private String via;
	private String from;
	private String fromTag;
	private String to;
	private String CallID;
	private String CSeq;
	private String Contact;
	private String ContentType;
	private String allow;
	private String userAgent;
	private String CSeqAttribute;
	private String contentLength;
	private String Owner;
	private String Connection;
	private String TimeDescription;
	private String MediaDescription;

	public SIPSession(){
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public String getFromTag() {
		return fromTag;
	}

	public void setFromTag(String fromTag) {
		this.fromTag = fromTag;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCSeqAttribute() {
		return CSeqAttribute;
	}

	public void setCSeqAttribute(String cSeqAttribute) {
		CSeqAttribute = cSeqAttribute;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getOwner() {
		return Owner;
	}

	public void setOwner(String owner) {
		Owner = owner;
	}

	public String getConnection() {
		return Connection;
	}

	public void setConnection(String connection) {
		Connection = connection;
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
}