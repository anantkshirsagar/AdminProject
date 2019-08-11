package com.service.technolite.sms;

/**
 * SmsDetails class contains the actual text message and receivers number
 * 
 * @author Anant Kshirsagar
 */

public class SmsDetails {
	private String message;
	private String number;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmsDetails [message=");
		builder.append(message);
		builder.append(", number=");
		builder.append(number);
		builder.append("]");
		return builder.toString();
	}

}
