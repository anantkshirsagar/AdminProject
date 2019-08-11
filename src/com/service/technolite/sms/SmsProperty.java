package com.service.technolite.sms;

/**
 * This class contains properties of the SMS which can be set through
 * .properties file
 * 
 * @author Anant Kshirsagar
 *
 */
public class SmsProperty {
	private String url;
	private String username;
	private String password;
	private String sender;
	private String format;
	private String messageType;
	private String requestId;
	private String delimiter;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmsProperty [url=");
		builder.append(url);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", format=");
		builder.append(format);
		builder.append(", messageType=");
		builder.append(messageType);
		builder.append(", requestId=");
		builder.append(requestId);
		builder.append(", delimiter=");
		builder.append(delimiter);
		builder.append("]");
		return builder.toString();
	}
}
