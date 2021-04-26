package com.veterinaria.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;


@ConfigurationProperties("storage")
@Service
public class StorageProperties {

	private String localizacion = "mascotasImg";

	public String getLocation() {
		return localizacion;
	}

	public void setLocation(String localizacion) {
		this.localizacion = localizacion;
	}

}
