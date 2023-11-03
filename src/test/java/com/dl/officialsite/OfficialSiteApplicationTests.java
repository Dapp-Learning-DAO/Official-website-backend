package com.dl.officialsite;

import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.MemberController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OfficialSiteApplicationTests {
	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	EmailService emailService;
	@Test
	void contextLoads() {
	}



	@Test
	public  void mailTest() {

	//Get the mailer instance


	//Send a composed mail
		emailService.sendSimpleMessage("411497616@qq.com", "Test Subject1", "Testing body11");

	//Send a pre-configured mail
		//emailService.sendPreConfiguredMail("Exception occurred everywhere.. where are you ????");
	}



}
