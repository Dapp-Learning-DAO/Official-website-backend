package com.dl.officialsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Properties;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
public class OfficialSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficialSiteApplication.class, args);
	}


	@Bean
	public Credentials getQueryCredentials()   {
		return Credentials.create("1");
	}
}


