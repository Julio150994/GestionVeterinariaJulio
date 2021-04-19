package com.veterinaria.servicios;

import java.util.List;

import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;


public interface IUsuariosService {
	
	public abstract List<ModeloUsuarios> listarUsuarios();
	public abstract ModeloUsuarios buscarId(int id);
	public abstract ModeloUsuarios enabledCliente(int id, ModeloUsuarios usuario);
	public abstract ModeloUsuarios editarPerfil(ModeloUsuarios modeloCliente);
	
	
	public abstract Usuarios convertirUsuarios(ModeloUsuarios modeloCliente);
	public abstract ModeloUsuarios convertirUsuarios(Usuarios cliente);
}
