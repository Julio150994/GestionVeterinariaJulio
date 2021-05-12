package com.veterinaria.servicios.Impl;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private BCryptPasswordEncoder encriptar;
	
	@Autowired
	private DozerBeanMapper dozerUsuarios;
	
	
	@Override
	public ModeloUsuarios aniadirCliente(ModeloUsuarios usuario) {
		usuario.setPassword(encriptar.encode(usuario.getPassword()));
		usuario.setActivado(false);
		usuario.setRol("ROLE_CLIENTE");
		return dozerUsuarios.map(clientes.save(convertirClientes(usuario)), ModeloUsuarios.class);
	}

	@Override
	public ModeloUsuarios editarCliente(ModeloUsuarios usuario) {
		usuario.setPassword(encriptar.encode(usuario.getPassword()));
		usuario.setActivado(false);
		usuario.setRol("ROLE_CLIENTE");
		return dozerUsuarios.map(clientes.save(convertirClientes(usuario)), ModeloUsuarios.class);
	}

	@Override
	public void eliminarCliente(Integer id) {
		clientes.deleteById(id);
	}

	@Override
	public Usuarios convertirClientes(ModeloUsuarios usuario) {
		return dozerUsuarios.map(usuario, Usuarios.class);
	}

	@Override
	public ModeloUsuarios convertirClientes(Usuarios usuario) {
		return dozerUsuarios.map(usuario, ModeloUsuarios.class);
	}
}
