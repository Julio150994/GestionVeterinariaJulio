package com.veterinaria.servicios.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.servicios.MascotasService;


@Service("mascotasImpl")
public class MascotasImpl implements MascotasService {
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotas;
	
	@Autowired
	private DozerBeanMapper dozerMascotas;
	
	
	@Override
	public List<ModeloMascotas> listarMascotas() {
		return mascotas.findAll().stream().map(m->convertirMascotas(m)).collect(Collectors.toList());
	}

	@Override
	public ModeloMascotas buscarIdMascota(int id) {
		return convertirMascotas(mascotas.findById(id).orElse(null));
	}
	
	@Override
	public ModeloMascotas aniadirMascota(ModeloMascotas mascota, ModeloUsuarios cliente) {
		mascota.setIdCliente(cliente);
		return dozerMascotas.map(mascotas.save(convertirMascotas(mascota)),ModeloMascotas.class);
	}

	@Override
	public ModeloMascotas editarMascota(ModeloMascotas mascota, ModeloUsuarios cliente) {
		mascota.setIdCliente(cliente);
		return dozerMascotas.map(mascotas.save(convertirMascotas(mascota)),ModeloMascotas.class);
	}

	@Override
	public void eliminarMascota(int id) {
		mascotas.deleteById(id);
	}

	@Override
	public Mascotas convertirMascotas(ModeloMascotas modeloMascota) {
		return dozerMascotas.map(modeloMascota, Mascotas.class);
	}

	@Override
	public ModeloMascotas convertirMascotas(Mascotas mascota) {
		return dozerMascotas.map(mascota, ModeloMascotas.class);
	}
}
