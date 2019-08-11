package test;

import java.io.File;
import java.io.IOException;

import com.service.textlocal.sms.SmsDetails;
import com.service.textlocal.sms.SmsService;

public class TestSms {
	public static void main(String[] args) throws IOException {
		sendSms();
	}

	public static void sendSms() throws IOException {
		SmsDetails smsDetails = new SmsDetails();
		smsDetails.setMessage("This message is send from textlocal sms api.");
		smsDetails.setNumbers("Put your mobile no here.");
		smsDetails.setSender("TXTLCL");

		SmsService smsService = new SmsService();
		smsService.load(new File("resources/sms.properties"));
		smsService.sendSms(smsDetails);
	}
}