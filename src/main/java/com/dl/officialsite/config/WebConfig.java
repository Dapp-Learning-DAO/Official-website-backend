package com.dl.officialsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.CookieGenerator;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CookieGenerator cookieGenerator() {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieDomain(".dapplearning.org");
        //cookieGenerator.setCookieName("cookieName");
        cookieGenerator.setCookieMaxAge(  2 * 60 * 60); // 7 days
        // Additional configurations
        return cookieGenerator;
    }

    // Other configurations if needed
}
