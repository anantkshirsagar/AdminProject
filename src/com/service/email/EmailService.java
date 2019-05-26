package com.service.email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

/**
 * EmailService class is responsible to read email properties from .properties
 * file and sets the api key. And methods of this class are used send mail.
 * 
 * @author Suyog Shah
 *
 */
public class EmailService {
	private static final Logger LOG = Logger.getLogger(EmailPropertyReader.class.getName());
	private static EmailPropertyReader emailPropertyReader;
	private static EmailProperty emailProperty;
	private static EmailService emailService;

	private EmailService(File file) throws IOException {
		setEmailPropertyReader(new EmailPropertyReader(file));
		setEmailProperty(emailPropertyReader.getEmailProperties());
	}

	private enum Endpoint {
		MAIL_SEND("mail/send");

		private String endPoint;

		private Endpoint(String endPoint) {
			this.endPoint = endPoint;
		}

		public String getEndPoint() {
			return this.endPoint;
		}
	}

	/**
	 * This method is used to getInstance of EmailService and it takes file which is
	 * email.properties
	 * 
	 * @param file
	 * @return EmailService
	 * @throws IOException
	 */
	public static EmailService getInstance(File file) throws IOException {
		emailService = new EmailService(file);
		return emailService;
	}

	/**
	 * This method is used to send a single mail.
	 * 
	 * @param emailDetails
	 * @return com.sendgrid.Response
	 * @throws Exception
	 */
	public Response sendEmail(EmailDetails emailDetails) throws Exception {
		LOG.info("Inside sendEmail method");
		Request request = new Request();
		Email from = new Email(emailDetails.getFrom());
		Content content = new Content(emailDetails.getContentType().TEXT_HTML.getContentType(), emailDetails.getBody());

		LOG.info("Setting API Key");
		SendGrid sendGrid = new SendGrid(emailProperty.getApiKey());
		LOG.info("API Key: " + emailProperty.getApiKey());

		Email to = new Email(emailDetails.getTo()[0]);
		Mail mail = new Mail(from, emailDetails.getSubject(), to, content);
		request.setMethod(Method.POST);
		request.setEndpoint(Endpoint.MAIL_SEND.getEndPoint());

		request.setBody(mail.build());
		return sendGrid.api(request);
	}

	public Response[] sendMultipleEmail(EmailDetails emailDetails) throws Exception {
		LOG.info("Inside sendMultipleEmail method");
		Request request = new Request();
		Email from = new Email(emailDetails.getFrom());
		Content content = new Content(emailDetails.getContentType().TEXT_HTML.getContentType(), emailDetails.getBody());
		SendGrid sg = new SendGrid(getEmailProperty().getApiKey());
		Email to = new Email();
		String[] toEmailAddressList = emailDetails.getTo();
		List<Response> responses = new ArrayList<Response>();
		LOG.info("To email addresses count: " + toEmailAddressList.length);

		for (String toEmailAddress : toEmailAddressList) {
			to.setEmail(toEmailAddress);
			Mail mail = new Mail(from, emailDetails.getSubject(), to, content);
			request.setMethod(Method.POST);
			request.setEndpoint(Endpoint.MAIL_SEND.getEndPoint());
			request.setBody(mail.build());
			responses.add(sg.api(request));
		}
		return responses.toArray(new Response[0]);
	}

	public static EmailPropertyReader getEmailPropertyReader() {
		return emailPropertyReader;
	}

	public static void setEmailPropertyReader(EmailPropertyReader emailPropertyReader) {
		EmailService.emailPropertyReader = emailPropertyReader;
	}

	public static EmailProperty getEmailProperty() {
		return emailProperty;
	}

	public static void setEmailProperty(EmailProperty emailProperty) {
		EmailService.emailProperty = emailProperty;
	}
}
