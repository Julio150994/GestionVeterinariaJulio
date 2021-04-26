package com.veterinaria.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootImagenMascota;
	
	@Autowired
	StorageService mascotasService;
	
	
	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootImagenMascota = Paths.get(properties.getLocation());
	}

	@Override
	public String store(MultipartFile fileMascota, int idMascota) {
		String nombreImagen = StringUtils.cleanPath(fileMascota.getOriginalFilename());// para el nombre de la ruta de imágen
		String extensionImg = StringUtils.getFilenameExtension(nombreImagen);// para la extensión de la imágen
		String mascotaStore = nombreImagen+"."+extensionImg;
		
		try {
			if (fileMascota.isEmpty())
				throw new StorageException("Error. Debe subir una imágen");
			
			if(nombreImagen.contains(".."))
				throw new StorageException("No debe almacenar esta imágen fuera del directorio "+fileMascota);
			
			/* Para subir el archivo al servidor */
			try (InputStream inputStream = fileMascota.getInputStream()) {
				Files.copy(inputStream,this.rootImagenMascota.resolve(mascotaStore), StandardCopyOption.REPLACE_EXISTING);
				return mascotaStore;
			}
		}
		catch (IOException e) {
			throw new StorageException("Error al almacenar la imágen.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootImagenMascota, 1)
				.filter(path -> !path.equals(this.rootImagenMascota))
				.map(this.rootImagenMascota::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootImagenMascota.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("No se ha podido leer el archivo: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootImagenMascota.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootImagenMascota);
		}
		catch (IOException e) {
			throw new StorageException("Error al iniciar el almacenamiento de la imágen", e);
		}
	}

	@Override
	public void deleteImage(String imagen) {
		mascotasService.deleteImage(imagen);
	}
}
