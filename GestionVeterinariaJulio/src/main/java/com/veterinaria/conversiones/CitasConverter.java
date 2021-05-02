package com.veterinaria.conversiones;

import org.springframework.stereotype.Component;

import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;

@Component("citasConverter")
public class CitasConverter {
	private ModeloUsuarios modeloVeterinario = new ModeloUsuarios();
	private ModeloMascotas modeloMascota = new ModeloMascotas();
	private Usuarios veterinario = new Usuarios();
	private Mascotas mascota = new Mascotas();
	
	
	// Convertimos de la entidad al modelo
	public Citas convertirCitas(ModeloCitas modeloCita) {
		Citas cita = new Citas();
		cita.setId(modeloCita.getId());
		cita.setMascota(mascota);
		cita.setVeterinario(veterinario);
		cita.setFecha(modeloCita.getFecha());
		cita.setMotivo(modeloCita.getMotivo());
		cita.setInforme(modeloCita.getInforme());
		cita.setRealizada(modeloCita.isRealizada());
		return cita;
	}
	
	// Convertimos del modelo a la entidad
	public ModeloCitas convertirModeloCitas(Citas cita) {
		ModeloCitas modeloCita = new ModeloCitas();
		modeloCita.setId(cita.getId());
		modeloCita.setMascota(modeloMascota);
		modeloCita.setVeterinario(modeloVeterinario);
		modeloCita.setFecha(cita.getFecha());
		modeloCita.setMotivo(cita.getMotivo());
		modeloCita.setInforme(cita.getInforme());
		modeloCita.setRealizada(cita.isRealizada());
		return modeloCita;
	}
}
