package com.magnus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.magnus.*"})
@ComponentScan("com.magnus.*")
@EntityScan("com.magnus.entities")
@EnableJpaRepositories("com.magnus.repositories")
public class MagnusItApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MagnusItApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MagnusItApplication.class, args);
	}
}
