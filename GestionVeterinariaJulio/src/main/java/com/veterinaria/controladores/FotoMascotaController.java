package com.veterinaria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.veterinaria.storage.StorageService;


@Controller
@RequestMapping("/")
public class FotoMascotaController {
	
	@Autowired
	private StorageService storageMascotas;
	
	@GetMapping("/mascotasImg/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> servidorMascota(@PathVariable("foto") String foto) {
		Resource fotoMascota = storageMascotas.loadAsResource(foto);
		return ResponseEntity.ok().body(fotoMascota);
	}
}
