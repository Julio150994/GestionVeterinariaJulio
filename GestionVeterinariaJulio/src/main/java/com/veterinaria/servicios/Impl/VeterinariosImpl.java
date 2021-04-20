package com.veterinaria.servicios.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	private DozerBeanMapper dozerUsuarios;
	
	
	@Override
	public List<ModeloUsuarios> listarVeterinarios() {
		return veterinarios.findAll().stream().map(v->convertirVeterinarios(v)).collect(Collectors.toList());
	}
	
	@Override
	public ModeloUsuarios buscarIdVeterinario(ModeloUsuarios usuario, int id) {
		if(usuario.getRol() == "ROLE_VETERINARIO")
			return convertirVeterinarios(veterinarios.findById(id).orElse(null));
		else
			return null;
	}
	
	@Override
	public ModeloUsuarios aniadirVeterinario(ModeloUsuarios usuario) {
		usuario.setRol("ROLE_VETERINARIO");
		return dozerUsuarios.map(veterinarios.save(convertirVeterinarios(usuario)),ModeloUsuarios.class);
	}

	@Override
	public ModeloUsuarios editarVeterinario(ModeloUsuarios usuario) {
		usuario.setRol("ROLE_VETERINARIO");
		return dozerUsuarios.map(veterinarios.save(convertirVeterinarios(usuario)),ModeloUsuarios.class);
	}

	@Override
	public void eliminarVeterinario(ModeloUsuarios usuario, int id) {
		if(usuario.getRol() == "ROLE_VETERINARIO")
			veterinarios.deleteById(id);
	}

	@Override
	public Usuarios convertirVeterinarios(ModeloUsuarios usuario) {
		if(usuario.getRol() == "ROLE_VETERINARIO")
			return dozerUsuarios.map(usuario, Usuarios.class);
		else
			return null;
	}

	@Override
	public ModeloUsuarios convertirVeterinarios(Usuarios usuario) {
		if(usuario.getRol() == "ROLE_VETERINARIO")
			return dozerUsuarios.map(usuario, ModeloUsuarios.class);
		else
			return null;
	}
}
