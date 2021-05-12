package com.veterinaria.servicios.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.repositorios.ClientesRepository;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.servicios.MascotasService;
import com.veterinaria.storage.StorageService;


@Service("mascotasImpl")
public class MascotasImpl implements MascotasService {
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotas;
	
	@Autowired
	@Qualifier("clientesRepository")
	private ClientesRepository clientes;
	
	@Autowired
	StorageService storage;
	
	
	@Override
	public List<Mascotas> listarMascotas() {
		return mascotas.findAll();
	}
	
	@Override
	public Page<Mascotas> paginacionMascotas(Pageable mascota) {
		return mascotas.findAll(mascota);
	}

	@Override
	public Mascotas buscarIdMascota(Integer id) {
		return mascotas.findById(id).orElse(null);
	}
	
	@Override
	public Mascotas aniadirMascota(Mascotas modeloMascota) {
		modeloMascota.setFoto(modeloMascota.getFoto());
		
		return mascotas.save(modeloMascota);
	}

	@Override
	public Mascotas editarMascota(Mascotas modeloMascota) {
		modeloMascota.setFoto(modeloMascota.getFoto());
		
		return mascotas.save(modeloMascota);
	}

	@Override
	public void eliminarMascota(Integer id) {
		Mascotas mascota = mascotas.findById(id).orElse(null);
		
		if(mascota.getFoto() != null)
			storage.delete(mascota.getFoto());
		
		mascotas.deleteById(id);
	}
}
