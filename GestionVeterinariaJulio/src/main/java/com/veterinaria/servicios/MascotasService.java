package com.veterinaria.servicios;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.veterinaria.entidades.Mascotas;

public interface MascotasService {
	
	public abstract List<Mascotas> listarMascotas();
	public abstract Page<Mascotas> paginacionMascotas(Pageable mascota);
	
	public abstract Mascotas buscarIdMascota(Integer id);
	public abstract Mascotas aniadirMascota(Mascotas mascota);
	public abstract Mascotas editarMascota(Mascotas mascota);
	
	public abstract void eliminarMascota(Integer id);
}
