package com.veterinaria.configuraciones;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.veterinaria.storage.StorageProperties;


@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class ConfiguracionMascotas {
	
}
