package org.formflowstartertemplate.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.ForwardedHeaderFilter;

/**
 * Security Configuration
 * - securityFilterChain: Disables basic auth while retaining other spring security features
 * - forwardedHeaderFilter: Redirects http -> https
*/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin().disable();
    return httpSecurity.build();
  }

  @Bean
  public ForwardedHeaderFilter forwardedHeaderFilter() {
    return new ForwardedHeaderFilter();
  }
}
