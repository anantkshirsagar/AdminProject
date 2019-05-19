package com.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class EmailService {
	public static Response sendEmail(SendEmail sendEmail) throws Exception {
		Response response = null;
		Request request = new Request();
		Email from = new Email(sendEmail.getFrom());
		Content content = new Content(sendEmail.getContentType(), sendEmail.getContent());
		SendGrid sg = new SendGrid(new DatabaseService().getSendGridApiKey());
		Email to = new Email(sendEmail.getTo()[0]);
		Mail mail = new Mail(from, sendEmail.getSubject(), to, content);
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());
		response = sg.api(request);
		return response;
	}

	public static Response[] sendMultipleEmail(SendEmail sendEmail) throws Exception {
		Request request = new Request();
		Email from = new Email(sendEmail.getFrom());
		Content content = new Content(sendEmail.getContentType(), sendEmail.getContent());
		SendGrid sg = new SendGrid(new DatabaseService().getSendGridApiKey());
		Email to = new Email();
		String[] toList = sendEmail.getTo();
		List<Response> responses = new ArrayList<>();
		for (String str : toList) {
			to.setEmail(str);
			Mail mail = new Mail(from, sendEmail.getSubject(), to, content);
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			responses.add(sg.api(request));
		}
		return responses.stream().toArray(Response[]::new);
	}
}
