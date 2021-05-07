package com.veterinaria.servicios;

import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;


public interface CitasService {
	public abstract List<ModeloCitas> buscarCitas(Date fecha);
	
	public abstract ModeloCitas pedirCita(ModeloCitas cita);
	public abstract Page<Citas> paginacionCitas(Pageable cita);
	public abstract ModeloCitas realizarCita(ModeloCitas cita, int idCita);
	
	public abstract List<ModeloUsuarios> listarVeterinarios(ModeloCitas cita);
	public abstract List<ModeloMascotas> listarMascotas(ModeloCitas cita);
	
	public abstract Citas convertirCitas(ModeloCitas cita);
	public abstract ModeloCitas convertirCitas(Citas cita);
}
