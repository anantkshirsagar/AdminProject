package com.service.email;

import java.util.Arrays;

import com.commons.util.ContentTypes;

/**
 * This class is used to store the details of the sender, receiver, to, subject,
 * body and contentType.
 * 
 * @author Anant Kshirsagar
 *
 */
public class EmailDetails {
	private String from;
	private String[] to;
	private String subject;
	private String body;
	private ContentTypes contentType;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String... to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ContentTypes getContentType() {
		return contentType;
	}

	public void setContentType(ContentTypes contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailDetails [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(Arrays.toString(to));
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", body=");
		builder.append(body);
		builder.append(", contentType=");
		builder.append(contentType);
		builder.append("]");
		return builder.toString();
	}

}
