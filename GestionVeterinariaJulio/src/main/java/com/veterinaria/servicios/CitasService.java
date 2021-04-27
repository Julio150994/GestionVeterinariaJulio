package com.veterinaria.servicios;

import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;


public interface CitasService {
	
	public abstract ModeloCitas pedirCita(ModeloCitas cita, ModeloMascotas mascota, ModeloUsuarios veterinario);
	
	public abstract Citas convertirCitas(ModeloCitas cita);
	public abstract ModeloCitas convertirCitas(Citas cita);
}
