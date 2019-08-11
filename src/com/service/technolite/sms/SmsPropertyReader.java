package com.service.technolite.sms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * SmsPropertyReader reads email properties from .properties files
 * 
 * @author Anant Kshirsagar
 *
 */
public class SmsPropertyReader {
	private File file;
	private static final Logger LOG = Logger.getLogger(SmsPropertyReader.class.getName());

	public SmsPropertyReader(File file) {
		this.file = file;
	}

	/**
	 * This method is used to read sms properties from .properties file
	 * 
	 * @return SmsProperty
	 * @throws IOException
	 */
	public SmsProperty getSmsProperties() throws IOException {
		SmsProperty smsProperty = new SmsProperty();
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		LOG.info("Sms properties file loading...");
		smsProperty.setDelimiter(properties.getProperty("sms.technolite.delimiter").trim());
		smsProperty.setFormat(properties.getProperty("sms.technolite.format").trim());
		smsProperty.setMessageType(properties.getProperty("sms.technolite.msgType").trim());
		smsProperty.setUsername(properties.getProperty("sms.technolite.username").trim());
		smsProperty.setPassword(properties.getProperty("sms.technolite.password").trim());
		smsProperty.setRequestId(properties.getProperty("sms.technolite.reqId").trim());
		smsProperty.setSender(properties.getProperty("sms.technolite.sender").trim());
		smsProperty.setUrl(properties.getProperty("sms.technolite.url").trim());
		return smsProperty;
	}
}
