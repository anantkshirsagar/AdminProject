package com.service.textlocal.sms;

/**
 * This class stores the confidential certificates which are required to send
 * sms.
 * 
 * @author Suyog Shah
 *
 */
public class SmsProperty {
	private String apiKey;
	private String textLocalURL;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getTextLocalURL() {
		return textLocalURL;
	}

	public void setTextLocalURL(String textLocalURL) {
		this.textLocalURL = textLocalURL;
	}
}
