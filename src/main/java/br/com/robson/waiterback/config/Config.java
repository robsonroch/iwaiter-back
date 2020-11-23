package br.com.robson.waiterback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.robson.waiterback.services.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class Config {
	@Bean
	public AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}
}
