package com.sms;

public class Test {
	public static void main(String[] args) {
		SendSms sendSms = new SendSms();
		sendSms.setMessage("ABCD");
		sendSms.setNumbers("9028877194");
		sendSms.setSender("TXTLCL");
		String responce = new SmsService().sendSms(sendSms);
		System.out.println("=="+responce);
		// API doc
		// http://api.textlocal.in/docs/sendsms
	}
}