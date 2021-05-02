package com.veterinaria.conversiones;

import org.springframework.stereotype.Component;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;


@Component("mascotasConverter")
public class MascotasConverter {
	private ModeloUsuarios modeloUsuario = new ModeloUsuarios();
	private Usuarios usuario = new Usuarios();
	
	// Convertimos de la entidad al modelo
	public Mascotas convertirMascotas(ModeloMascotas modeloMascota) {
		Mascotas mascota = new Mascotas();		
		mascota.setId(modeloMascota.getId());
		mascota.setNombre(modeloMascota.getNombre());
		mascota.setTipo(modeloMascota.getTipo());
		mascota.setRaza(modeloMascota.getRaza());
		mascota.setFechaNacimiento(modeloMascota.getFechaNacimiento());
		mascota.setFoto(modeloMascota.getFoto());
		mascota.setCliente(usuario);
		return mascota;
	}
	
	// Convertimos del modelo a la entidad
	public ModeloMascotas convertirModeloMascotas(Mascotas mascota) {
		ModeloMascotas modeloMascota = new ModeloMascotas();
		modeloMascota.setId(mascota.getId());
		modeloMascota.setNombre(mascota.getNombre());
		modeloMascota.setTipo(mascota.getTipo());
		modeloMascota.setRaza(mascota.getRaza());
		modeloMascota.setFechaNacimiento(mascota.getFechaNacimiento());
		modeloMascota.setFoto(mascota.getFoto());
		modeloMascota.setCliente(modeloUsuario);
		return modeloMascota;
	}
}
