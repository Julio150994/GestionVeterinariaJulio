package com.veterinaria.servicios;

import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;


public interface CitasService {
	public abstract List<ModeloCitas> buscarCitas(Date fecha);
	
	public abstract Citas pedirCita(Citas cita);
	public abstract Page<Citas> paginacionCitas(Pageable cita);
	public abstract ModeloCitas realizarCita(ModeloCitas cita, Integer idCita);
	
	public abstract Citas convertirCitas(ModeloCitas cita);
	public abstract ModeloCitas convertirCitas(Citas cita);
}
