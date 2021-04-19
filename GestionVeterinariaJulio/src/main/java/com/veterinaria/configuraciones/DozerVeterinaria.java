package com.veterinaria.configuraciones;


import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DozerVeterinaria {
	
	@Bean
	public DozerBeanMapper converDozer() {
		return new DozerBeanMapper();
	}
}
