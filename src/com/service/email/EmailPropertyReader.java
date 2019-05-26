package com.service.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * EmailPropertyReader reads email properties from .properties files
 * 
 * @author Anant Kshirsagar
 *
 */
public class EmailPropertyReader {
	private File file;
	private static final Logger LOG = Logger.getLogger(EmailPropertyReader.class.getName());

	public EmailPropertyReader(File file) {
		this.file = file;
	}

	/**
	 * This method is used to read email properties from .properties file
	 * 
	 * @return EmailProperty
	 * @throws IOException
	 */
	public EmailProperty getEmailProperties() throws IOException {
		EmailProperty emailProperty = new EmailProperty();
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		LOG.info("Email properties file loading...");
		emailProperty.setApiKey(properties.getProperty("email.sendgrid.apiKey"));
		LOG.info(emailProperty.toString());
		return emailProperty;
	}
}
