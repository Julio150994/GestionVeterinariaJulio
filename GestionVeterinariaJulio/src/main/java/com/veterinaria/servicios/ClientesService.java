package com.veterinaria.servicios;

import java.util.List;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;


public interface ClientesService {
	
	public abstract List<ModeloUsuarios> listarClientes();
	public abstract ModeloUsuarios buscarIdCliente(ModeloUsuarios usuario, int id);
	public abstract ModeloUsuarios aniadirCliente(ModeloUsuarios usuario);
	public abstract ModeloUsuarios editarCliente(ModeloUsuarios usuario);
	public abstract void eliminarCliente(ModeloUsuarios usuario, int id);
	
	public abstract Usuarios convertirClientes(ModeloUsuarios usuario);
	public abstract ModeloUsuarios convertirClientes(Usuarios usuario);
}
