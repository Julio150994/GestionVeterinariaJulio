package com.veterinaria.servicios;

import java.util.List;

import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;


public interface VeterinariosService {
	
	//-------------Métodos para el veterinario------------------------
	public abstract List<ModeloUsuarios> listarVeterinarios();
	public abstract ModeloUsuarios aniadirVeterinario(ModeloUsuarios veterinario);
	public abstract ModeloUsuarios buscarIdVeterinario(ModeloUsuarios veterinario, int id);
	public abstract ModeloUsuarios editarVeterinario(ModeloUsuarios veterinario);
	public abstract void eliminarVeterinario(ModeloUsuarios veterinario, int id);
	
	
	public abstract Usuarios convertirVeterinarios(ModeloUsuarios veterinario);
	public abstract ModeloUsuarios convertirVeterinarios(Usuarios veterinario);
}
