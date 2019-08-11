package com.service.textlocal.sms;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import com.commons.util.Encoding;

/**
 * This class is used to send sms using TextLocal API
 * 
 * @author ANANT KSHIRSAGAR
 *
 */
public class SmsService {
	private static final Logger LOG = Logger.getLogger(SmsService.class.getName());

	private static final String METHOD = "POST";
	private static final String CONTENT_LENGTH = "Content-Length";

	private SmsPropertyReader smsPropertyReader;
	private SmsProperty smsProperty;

	public void load(File file) throws IOException {
		setSmsPropertyReader(new SmsPropertyReader(file));
		setSmsProperty(getSmsPropertyReader().getSmsProperties());
	}

	/**
	 * This method actually sends sms to the specified mobile number(s) and it
	 * returns the response string, which contains json object.
	 * 
	 * @param smsDetails
	 * @return String
	 * @throws IOException
	 */
	public String sendSms(SmsDetails smsDetails) throws IOException {
		if (smsDetails == null) {
			throw new NullPointerException("SmsDetails cannot be empty");
		}
		HttpURLConnection connection = (HttpURLConnection) new URL(getSmsProperty().getTextLocalURL()).openConnection();
		String data = buildURL(smsDetails);
		LOG.info("Data: " + data);
		connection.setDoOutput(true);
		connection.setRequestMethod(METHOD);
		LOG.info("Data length: " + data.length());
		connection.setRequestProperty(CONTENT_LENGTH, Integer.toString(data.length()));
		connection.getOutputStream().write(data.getBytes(Encoding.UTF_8.getEncoding()));
		final BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		final StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = rd.readLine()) != null) {
			stringBuffer.append(line);
		}
		rd.close();
		LOG.info("Sms send successfully");
		return stringBuffer.toString();

	}

	/**
	 * This method is used to set api key and build the whole sms sending url to
	 * send sms.
	 * 
	 * @param smsDetails
	 * @return
	 */
	private String buildURL(SmsDetails smsDetails) {
		String numbers = buildNumberString(smsDetails.getNumbers());
		return new StringBuilder("apikey=").append(getSmsProperty().getApiKey()).append(numbers).append("&message=")
				.append(smsDetails.getMessage()).append("&sender=").append(smsDetails.getSender()).toString();
	}

	/**
	 * This method is used to build a string of comma separated numbers.
	 * 
	 * @param numbers
	 * @return String
	 */
	private static String buildNumberString(String[] numbers) {
		if (numbers == null) {
			throw new NullPointerException("Numbers cannot be null! Please provide at least one number");
		}

		StringBuilder numberString = new StringBuilder("&numbers=");
		for (String number : numbers) {
			numberString.append(number).append(",");
		}
		numberString.setLength(numberString.length() - 1);
		LOG.info("Numbers string: " + numberString);
		return numberString.toString();
	}

	public SmsPropertyReader getSmsPropertyReader() {
		return smsPropertyReader;
	}

	public void setSmsPropertyReader(SmsPropertyReader smsPropertyReader) {
		this.smsPropertyReader = smsPropertyReader;
	}

	public SmsProperty getSmsProperty() {
		return smsProperty;
	}

	public void setSmsProperty(SmsProperty smsProperty) {
		this.smsProperty = smsProperty;
	}
}
