package com.veterinaria.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	private String localizacion = "mascotasImg";

	public String getLocation() {
		return localizacion;
	}

	public void setLocation(String localizacion) {
		this.localizacion = localizacion;
	}

}
