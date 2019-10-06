package test;

import java.io.File;

import com.commons.util.ContentTypes;
import com.service.email.EmailDetails;
import com.service.email.EmailService;

public class TestEmail {
	public static void main(String[] args) throws Exception {
//		SendEmail sendEmail = new SendEmail();
//		sendEmail.setFrom("suyogshah237@gmail.com");
//		String[] array = { "anantkshirsagar38@gmail.com", "shubhamshinde9319@gmail.com" };
//		sendEmail.setTo(array);
//		sendEmail.setSubject("I am successful");
//		sendEmail.setContent("I am sending mail using java");
//		Response[] responce = EmailService.sendMultipleEmail(sendEmail);
//		System.out.println(responce);
		sendEmail();
	}

	public static void sendEmail() throws Exception {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFrom("anantkshirsagar38@gmail.com");
		emailDetails
				.setBody("<html><font color=red> This mail is sent from send grid api. Hurry I am done!</font></html>");
		emailDetails.setSubject("Send Grid Test Mail");
		// emailDetails.setTo("anantkshirsagar38@gmail.com", "icbm.iot@gmail.com");
		emailDetails.setTo("icbm.iot@gmail.com");
		emailDetails.setContentType(ContentTypes.TEXT_HTML);

		EmailService emailService = new EmailService();
		emailService.load(new File("resources/email.properties"));
		// Response[] sendEmail = emailService.sendMultipleEmail(emailDetails);
		emailService.sendEmail(emailDetails);
		// System.out.println(sendEmail);
	}
}
