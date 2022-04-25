package com.danilo.cobranca;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class ProjetoCobrancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoCobrancaApplication.class, args);
	}

	
	@Configuration
	public static class ConfiguracaoSpringMvc implements WebMvcConfigurer {
		
		@Bean
		public LocaleResolver localeResolver() {
			return new FixedLocaleResolver(new Locale("pt", "BR"));
		}
		
		@Override
	    public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/", "/boletos");
	        //registry.addViewController("/home").setViewName("PesquisaBoleto");
	    }
		
		@Bean
		public MessageSource messageSource() {
		    ReloadableResourceBundleMessageSource messageSource
		      = new ReloadableResourceBundleMessageSource();
		    
		    messageSource.setBasename("classpath:messages");
		    messageSource.setDefaultEncoding("UTF-8");
		    return messageSource;
		}
		
		@Bean
		public LocalValidatorFactoryBean getValidator() {
		    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		    bean.setValidationMessageSource(messageSource());
		    return bean;
		}
	}
	
		
}
