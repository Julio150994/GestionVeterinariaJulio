package com.veterinaria.servicios;

import java.util.List;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;


public interface CitasService {
	public abstract ModeloCitas pedirCita(ModeloCitas cita);
	
	public abstract List<ModeloUsuarios> listarVeterinarios(ModeloCitas cita);
	public abstract List<ModeloMascotas> listarMascotas(ModeloCitas cita);
	
	public abstract Citas convertirCitas(ModeloCitas cita);
	public abstract ModeloCitas convertirCitas(Citas cita);
}
