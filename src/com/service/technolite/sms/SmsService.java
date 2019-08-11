package com.service.technolite.sms;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

/**
 * SmsService class is used to send the sms using Technolite API, you have to
 * provide .properties file which contains the configuration details of sms API
 * 
 * @author Anant Kshirsagar
 *
 */
public class SmsService {
	private static final Logger LOG = Logger.getLogger(SmsService.class.getName());
	private static final String SPACE = " ";

	private SmsPropertyReader smsPropertyReader;
	private SmsProperty smsProperty;
	private SmsService smsService;

	/**
	 * This method loads the configuration of the sms API using .properties file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void load(File file) throws IOException {
		setSmsPropertyReader(new SmsPropertyReader(file));
		setSmsProperty(getSmsPropertyReader().getSmsProperties());
	}

	/**
	 * This method accepts SmsDetails which contains message and receivers
	 * number and sends sms
	 * 
	 * @param smsDetails
	 * @return String
	 * @throws IOException
	 */
	public String sendSms(SmsDetails smsDetails) throws IOException {
		String smsUrl = buildURL(smsDetails);
		URL url = new URL(smsUrl);
		InputStream inputStream = url.openConnection().getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder response = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		LOG.info("Sms send successfully on number: [" + smsDetails.getNumber() + "].");
		return response.toString();
	}

	private String buildURL(SmsDetails smsDetails) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(smsProperty.getUrl());
		urlBuilder.append("username=");
		urlBuilder.append(smsProperty.getUsername());
		urlBuilder.append("&password=");
		urlBuilder.append(smsProperty.getPassword());
		urlBuilder.append("&sender=");
		urlBuilder.append(smsProperty.getSender());
		urlBuilder.append("&to=");
		urlBuilder.append(smsDetails.getNumber());
		urlBuilder.append("&message=");
		urlBuilder.append(smsDetails.getMessage().replaceAll(SPACE, smsProperty.getDelimiter()));
		urlBuilder.append("&reqId=");
		urlBuilder.append(smsProperty.getRequestId());
		urlBuilder.append("&format=");
		urlBuilder.append(smsProperty.getFormat());
		urlBuilder.append("&msgtype=");
		urlBuilder.append(smsProperty.getMessageType());
		return urlBuilder.toString();
	}

	public SmsProperty getSmsProperty() {
		return smsProperty;
	}

	public void setSmsProperty(SmsProperty smsProperty) {
		this.smsProperty = smsProperty;
	}

	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public SmsPropertyReader getSmsPropertyReader() {
		return smsPropertyReader;
	}

	public void setSmsPropertyReader(SmsPropertyReader smsPropertyReader) {
		this.smsPropertyReader = smsPropertyReader;
	}
}
