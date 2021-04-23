package com.veterinaria.servicios;

import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;


public interface ClientesService {
	
	public abstract ModeloUsuarios aniadirCliente(ModeloUsuarios usuario);
	public abstract ModeloUsuarios editarCliente(ModeloUsuarios usuario);
	public abstract void eliminarCliente(int id);
	
	public abstract Usuarios convertirClientes(ModeloUsuarios usuario);
	public abstract ModeloUsuarios convertirClientes(Usuarios usuario);
}
