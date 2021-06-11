package com.veterinaria.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ConfiguraCorsCliente {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry cliente) {
				cliente.addMapping("/apiVeterinaria/**")
				.allowedOrigins("http://localhost:8080")
				.allowedMethods("GET","POST").maxAge(3600);
			}
		};
	}
}
