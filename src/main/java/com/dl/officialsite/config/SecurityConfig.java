package com.dl.officialsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//  @Autowired
//  private GithubClientConfig githubOauthClientConfig;

//  @Autowired
//  private OAuthResourceConfig githubOauthResourceConfig;
//
//  @Autowired
//  private OAuth2ClientContext oauth2ClientContext;

  @Autowired
  private ApplicationContext applicationContext;

//  @Value("${oauth.github.auth-url:/user/auth/github}")
//  private String githubAuthUrl;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().disable()
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .logout(l -> l
                    .logoutSuccessUrl("/").permitAll()
            )
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//            .and().addFilterBefore(oauth2ClientFilter(), RememberMeAuthenticationFilter.class);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

//  @Bean
//  public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(
//          OAuth2ClientContextFilter filter) {
//    FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
//    registration.setFilter(filter);
//    registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
//
//    return registration;
//  }

//  public Filter oauth2ClientFilter() {
//    //To receive authentication code. Send authentication code to receive access token
//    OAuth2ClientAuthenticationProcessingFilter oauth2ClientFilter = new OAuth2ClientAuthenticationProcessingFilter(
//            this.githubAuthUrl);
//    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(
//            githubOauthClientConfig,
//            oauth2ClientContext);
//    oauth2ClientFilter.setRestTemplate(restTemplate);
//    //To acquire user name by access token
//    UserInfoTokenServices tokenServices = new UserInfoTokenServices(
//            githubOauthResourceConfig.getUserInfoUri(),
//            githubOauthClientConfig.getClientId());
//    tokenServices.setRestTemplate(restTemplate);
//    oauth2ClientFilter.setTokenServices(tokenServices);
//    //register if the user is new otherwise just send remember me token through cookie
//    oauth2ClientFilter.setAuthenticationSuccessHandler(
//            new GithubOAuthAuthenticationSuccessHandler());
//    return oauth2ClientFilter;
//  }
}
