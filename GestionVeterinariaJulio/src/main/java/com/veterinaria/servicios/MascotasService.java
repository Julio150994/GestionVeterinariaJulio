package com.veterinaria.servicios;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;


public interface MascotasService {
	
	public abstract List<ModeloMascotas> listarMascotas();
	public abstract Page<Mascotas> paginacionMascotas(Pageable mascota);
	
	public abstract ModeloMascotas buscarIdMascota(int id);
	public abstract ModeloMascotas aniadirMascota(ModeloMascotas mascota);
	public abstract ModeloMascotas editarMascota(ModeloMascotas mascota);
	public abstract void eliminarMascota(int id);
	
	public abstract List<ModeloUsuarios> listarClientes(ModeloMascotas mascota);
	
	public abstract Mascotas convertirMascotas(ModeloMascotas modeloMascota);
	public abstract ModeloMascotas convertirMascotas(Mascotas mascota);
}
