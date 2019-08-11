package com.service.textlocal.sms;

import java.util.Arrays;

/**
 * This class is used to store the sms details.
 * 
 * @author Suyog Shah
 *
 */
public class SmsDetails {
	private String message;
	private String[] numbers;
	private String sender;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getNumbers() {
		return numbers;
	}

	public void setNumbers(String... numbers) {
		this.numbers = numbers;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmsDetails [message=");
		builder.append(message);
		builder.append(", numbers=");
		builder.append(Arrays.toString(numbers));
		builder.append(", sender=");
		builder.append(sender);
		builder.append("]");
		return builder.toString();
	}

}
