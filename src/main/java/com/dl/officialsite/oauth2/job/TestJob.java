package com.dl.officialsite.oauth2.job;

import com.dl.officialsite.oauth2.config.OAuthConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements ApplicationRunner {

    @Autowired
    private OAuthConfig oAuthConfig;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(oAuthConfig.getRegistrations().get("github").getClient().getClientSecret());
    }
}
