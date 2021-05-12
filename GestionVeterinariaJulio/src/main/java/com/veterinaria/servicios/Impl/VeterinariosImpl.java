package com.veterinaria.servicios.Impl;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.VeterinariosRepository;
import com.veterinaria.servicios.VeterinariosService;


@Service("veterinariosImpl")
public class VeterinariosImpl implements VeterinariosService {
	
	@Autowired
	@Qualifier("veterinariosRepository")
	private VeterinariosRepository veterinarios;
	
	@Autowired
	private BCryptPasswordEncoder encriptar;
	
	@Autowired
	private DozerBeanMapper dozerUsuarios;
	
	
	@Override
	public ModeloUsuarios aniadirVeterinario(ModeloUsuarios usuario) {
		usuario.setPassword(encriptar.encode(usuario.getPassword()));
		usuario.setActivado(true);
		usuario.setRol("ROLE_VETERINARIO");
		return dozerUsuarios.map(veterinarios.save(convertirVeterinarios(usuario)),ModeloUsuarios.class);
	}

	@Override
	public ModeloUsuarios editarVeterinario(ModeloUsuarios usuario) {
		usuario.setPassword(encriptar.encode(usuario.getPassword()));
		usuario.setActivado(true);
		usuario.setRol("ROLE_VETERINARIO");
		return dozerUsuarios.map(veterinarios.save(convertirVeterinarios(usuario)),ModeloUsuarios.class);
	}

	@Override
	public void eliminarVeterinario(Integer id) {
		veterinarios.deleteById(id);
	}

	@Override
	public Usuarios convertirVeterinarios(ModeloUsuarios usuario) {
		return dozerUsuarios.map(usuario, Usuarios.class);
	}

	@Override
	public ModeloUsuarios convertirVeterinarios(Usuarios usuario) {
		return dozerUsuarios.map(usuario, ModeloUsuarios.class);
	}
}
