package com.veterinaria.servicios.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.IUsuariosService;


@Service("usuariosImpl")
public class UsuariosImpl implements IUsuariosService {
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuarios;
	
	@Autowired
	private DozerBeanMapper dozerUsuarios;
	
	
	@Override
	public List<ModeloUsuarios> listarUsuarios() {
		return usuarios.findAll().stream().map(u->convertirUsuarios(u)).collect(Collectors.toList());
	}
	
	@Override
	public ModeloUsuarios buscarId(int id) {
		return convertirUsuarios(usuarios.findById(id).orElse(null));
	}
	
	@Override
	public ModeloUsuarios enabledCliente(int id, ModeloUsuarios usuario) {
		usuario = convertirUsuarios(usuarios.findById(id).orElse(null));
		
		if(usuario.isActivado() == false)
			usuario.setActivado(true);
		else
			usuario.setActivado(false);
		return dozerUsuarios.map(usuarios.save(convertirUsuarios(usuario)),ModeloUsuarios.class);
	}
	
	@Override
	public ModeloUsuarios editarPerfil(ModeloUsuarios modeloCliente) {
		return dozerUsuarios.map(usuarios.save(convertirUsuarios(modeloCliente)),ModeloUsuarios.class);
	}

	@Override
	public Usuarios convertirUsuarios(ModeloUsuarios modeloCliente) {
		return dozerUsuarios.map(modeloCliente, Usuarios.class);
	}

	@Override
	public ModeloUsuarios convertirUsuarios(Usuarios cliente) {
		return dozerUsuarios.map(cliente, ModeloUsuarios.class);
	}
}
