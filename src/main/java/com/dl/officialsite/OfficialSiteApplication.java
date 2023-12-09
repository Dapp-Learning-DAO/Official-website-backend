package com.dl.officialsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.web3j.crypto.Credentials;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class OfficialSiteApplication {

	public static void main(String[] args) {

		SpringApplication.run(OfficialSiteApplication.class, args);
	}


	@Bean
	public Credentials getQueryCredentials()   {
		return Credentials.create("1");
	}
}


