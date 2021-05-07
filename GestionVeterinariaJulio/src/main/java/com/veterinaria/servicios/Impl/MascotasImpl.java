package com.veterinaria.servicios.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.conversiones.MascotasConverter;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.ClientesRepository;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.servicios.MascotasService;
import com.veterinaria.storage.StorageService;


@Service("mascotasImpl")
public class MascotasImpl implements MascotasService {
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotas;
	
	@Autowired
	@Qualifier("clientesRepository")
	private ClientesRepository clientes;
	
	@Autowired
	@Qualifier("mascotasConverter")
	private MascotasConverter conversorMascotas;
	
	
	@Autowired
	private DozerBeanMapper dozerMascotas;
	
	@Autowired
	StorageService storage;
	
	
	@Override
	public List<ModeloMascotas> listarMascotas() {
		return mascotas.findAll().stream().map(m->convertirMascotas(m)).collect(Collectors.toList());	
	}
	
	@Override
	public Page<Mascotas> paginacionMascotas(Pageable mascota) {
		return mascotas.findAll(mascota);
	}

	@Override
	public ModeloMascotas buscarIdMascota(int id) {
		return convertirMascotas(mascotas.findById(id).orElse(null));
	}
	
	@Override
	public ModeloMascotas aniadirMascota(ModeloMascotas modeloMascota, int idCliente) {		
		modeloMascota.setFoto(modeloMascota.getFoto());
		modeloMascota.setIdCliente(idCliente);
		
		return dozerMascotas.map(mascotas.save(convertirMascotas(modeloMascota)),ModeloMascotas.class);
	}

	@Override
	public ModeloMascotas editarMascota(ModeloMascotas modeloMascota) {
		modeloMascota.setFoto(modeloMascota.getFoto());
		//modeloMascota.setCliente(modeloMascota.getCliente());
		
		return dozerMascotas.map(mascotas.save(convertirMascotas(modeloMascota)),ModeloMascotas.class);
	}

	@Override
	public void eliminarMascota(int id) {
		Mascotas mascota = mascotas.findById(id).orElse(null);
		
		if(mascota.getFoto() != null)
			storage.delete(mascota.getFoto());
		
		mascotas.deleteById(id);
	}
	
	@Override
	public List<ModeloUsuarios> listarClientes(ModeloMascotas mascota) {
		return mascotas.findByCliente(conversorMascotas.convertirMascotas(mascota))
				.stream().map(c->dozerMascotas.map(c,ModeloUsuarios.class)).collect(Collectors.toList());
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
