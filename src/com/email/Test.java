package com.email;

import com.sendgrid.Response;

public class Test {
	public static void main(String[] args) throws Exception {
		SendEmail sendEmail = new SendEmail();
		sendEmail.setFrom("suyogshah237@gmail.com");
		String[] array = { "anantkshirsagar38@gmail.com", "shubhamshinde9319@gmail.com" };
		sendEmail.setTo(array);
		sendEmail.setSubject("I am successful");
		sendEmail.setContent("I am sending mail using java");
		Response[] responce = EmailService.sendMultipleEmail(sendEmail);
		System.out.println(responce);
	}
}
