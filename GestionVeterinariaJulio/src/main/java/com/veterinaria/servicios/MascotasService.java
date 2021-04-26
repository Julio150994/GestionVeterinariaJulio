package com.veterinaria.servicios;

import java.util.List;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.modelos.ModeloMascotas;


public interface MascotasService {
	
	public abstract List<ModeloMascotas> listarMascotas();
	public abstract ModeloMascotas buscarIdMascota(int id);
	public abstract ModeloMascotas aniadirMascota(ModeloMascotas mascota);
	public abstract ModeloMascotas editarMascota(ModeloMascotas mascota);
	public abstract void eliminarMascota(int id);
	
	public abstract Mascotas convertirMascotas(ModeloMascotas modeloMascota);
	public abstract ModeloMascotas convertirMascotas(Mascotas mascota);
}
