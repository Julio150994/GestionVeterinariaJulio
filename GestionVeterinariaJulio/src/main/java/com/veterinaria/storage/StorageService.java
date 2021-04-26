package com.veterinaria.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();
	
	void store(MultipartFile file);
	
	//String store(MultipartFile file, int id);
	
	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);
	
	void deleteImage(String imagen);
	
	void deleteAll();

}
