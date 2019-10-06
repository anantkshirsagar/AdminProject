package test;

import java.io.File;
import java.io.IOException;

import com.service.technolite.sms.SmsDetails;
import com.service.technolite.sms.SmsService;

public class TestTechnoliteSms {
	public static void main(String[] args) throws IOException {
		SmsService smsService = new SmsService();
		// Please set the properties from the following path
		String filePath = "resources/technolite-dummy-sms.properties";
		smsService.load(new File(filePath));
		SmsDetails smsDetails = new SmsDetails();
		smsDetails.setMessage("Yeah! We are successfully running our API! NSG team success!");
		smsDetails.setNumber("80870xxxxx");
		String response = smsService.sendSms(smsDetails);
		System.out.println(response);
	}
}
