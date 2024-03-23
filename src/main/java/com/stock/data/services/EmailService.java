package com.stock.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

		@Autowired
		private JavaMailSender mailSender;
		
		public void sendEmail(String to,String sub,Double b)
		{
			SimpleMailMessage m=new SimpleMailMessage();
			m.setFrom("ayushdikshitsamsung@gmail.com");
			m.setTo(to);
			m.setText(String.valueOf(b));
			m.setSubject(sub);
			
			mailSender.send(m);
			System.out.println("MAIL SENT");
		}
		
		

}