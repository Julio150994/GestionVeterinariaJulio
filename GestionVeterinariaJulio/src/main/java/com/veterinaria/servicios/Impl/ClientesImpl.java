package com.veterinaria.servicios.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.ClientesRepository;
import com.veterinaria.servicios.ClientesService;


@Service("clientesImpl")
public class ClientesImpl implements ClientesService {
	
	@Autowired
	@Qualifier("clientesRepository")
	private ClientesRepository clientes;
	
	@Autowired
	private DozerBeanMapper dozerUsuarios;
	
	
	@Override
	public List<ModeloUsuarios> listarClientes() {
		return clientes.findAll().stream().map(c->convertirClientes(c)).collect(Collectors.toList());
	}

	@Override
	public ModeloUsuarios buscarIdCliente(ModeloUsuarios usuario, int id) {
		if(usuario.getRol() == "ROLE_CLIENTE")
			return convertirClientes(clientes.findById(id).orElse(null));
		else
			return null;
	}

	@Override
	public ModeloUsuarios aniadirCliente(ModeloUsuarios usuario) {
		return dozerUsuarios.map(clientes.save(convertirClientes(usuario)),ModeloUsuarios.class);
	}

	@Override
	public ModeloUsuarios editarCliente(ModeloUsuarios usuario) {
		return dozerUsuarios.map(clientes.save(convertirClientes(usuario)),ModeloUsuarios.class);
	}

	@Override
	public void eliminarCliente(ModeloUsuarios usuario, int id) {
		if(usuario.getRol() == "ROLE_CLIENTE")
			clientes.deleteById(id);
	}

	@Override
	public Usuarios convertirClientes(ModeloUsuarios usuario) {
		if(usuario.getRol() == "ROLE_CLIENTE")
			return dozerUsuarios.map(usuario, Usuarios.class);
		else
			return null;
	}

	@Override
	public ModeloUsuarios convertirClientes(Usuarios usuario) {
		if(usuario.getRol() == "ROLE_VETERINARIO")
			return dozerUsuarios.map(usuario, ModeloUsuarios.class);
		else
			return null;
	}
}
