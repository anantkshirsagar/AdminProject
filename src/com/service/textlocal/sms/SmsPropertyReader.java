package com.service.textlocal.sms;

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
		smsProperty.setApiKey(properties.getProperty("sms.textlocal.apiKey").trim());
		smsProperty.setTextLocalURL(properties.getProperty("sms.textlocal.url").trim());
		return smsProperty;
	}
}
