package org.formflowstartertemplate.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.filter.ForwardedHeaderFilter;

/**
 * Security Configuration
 * - setDefaultSecurityCookie - sets headers: 'Secure' and 'HttpOnly'
 * - securityFilterChain: Disables basic auth while retaining other spring security features
 * - forwardedHeaderFilter: Redirects http -> https
*/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public DefaultCookieSerializer setDefaultSecurityCookie(){
    DefaultCookieSerializer serializer = new DefaultCookieSerializer();
    serializer.setUseSecureCookie(true);
    serializer.setUseHttpOnlyCookie(true);
    return serializer;
  }
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin(AbstractHttpConfigurer::disable);
    return httpSecurity.build();
  }

  @Bean
  public ForwardedHeaderFilter forwardedHeaderFilter() {
    return new ForwardedHeaderFilter();
  }
}
