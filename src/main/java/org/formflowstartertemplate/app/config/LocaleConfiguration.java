package org.formflowstartertemplate.app.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieHttpOnly(true);
		localeResolver.setCookieSecure(true);
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		// setBasenames() will do three things:
		// 1) It will override any previous setting of basenames. Only these names/paths exist now.
		// 2) It will look for messages in sequential order and if something is found in a file, it stops looking there.
		//    Order matters and if you want to provide overrides, make sure they are in the earlier
		//    files (like 'messages', rather than 'messages-form-flow').
		// 3) For i18n this will look for files with the basename + "_[lang]", like messages_en.properties,
		//    automatically for you.
		//
		messageSource.setBasenames("messages", "messages-form-flow");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}
}