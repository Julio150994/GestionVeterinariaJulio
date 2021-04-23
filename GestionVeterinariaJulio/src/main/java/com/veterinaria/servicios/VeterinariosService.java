package com.veterinaria.servicios;

import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;


public interface VeterinariosService {
	
	public abstract ModeloUsuarios aniadirVeterinario(ModeloUsuarios veterinario);
	public abstract ModeloUsuarios editarVeterinario(ModeloUsuarios veterinario);
	public abstract void eliminarVeterinario(int id);
	
	public abstract Usuarios convertirVeterinarios(ModeloUsuarios veterinario);
	public abstract ModeloUsuarios convertirVeterinarios(Usuarios veterinario);
}
