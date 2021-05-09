package com.veterinaria.servicios.Impl;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.repositorios.VeterinariosRepository;
import com.veterinaria.servicios.CitasService;


@Service("citasImpl")
public class CitasImpl implements CitasService {

	@Autowired
	@Qualifier("citasRepository")
	private CitasRepository citas;
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotas;
	
	@Autowired
	@Qualifier("veterinariosRepository")
	private VeterinariosRepository veterinarios;	
		
	@Autowired
	private DozerBeanMapper dozerCitas;
	
	@Override
	public List<ModeloCitas> buscarCitas(Date fecha) {
		return citas.findByFecha(fecha).stream().map(c->convertirCitas(c)).collect(Collectors.toList());
	}
	
	@Override
	public Page<Citas> paginacionCitas(Pageable cita) {
		return citas.findAll(cita);
	}
	
	@Override
	public ModeloCitas realizarCita(ModeloCitas cita, int id) {
		cita = convertirCitas(citas.findById(id).orElse(null));
		
		if(cita.isRealizada() == false)
			cita.setRealizada(true);
		else
			cita.setRealizada(false);
		
		return dozerCitas.map(citas.save(convertirCitas(cita)), ModeloCitas.class);
	}
	
	@Override
	public ModeloCitas pedirCita(ModeloCitas modeloCita) {
		modeloCita.setRealizada(false);// establecemos la cita para saber que inicialmente no se ha realizado
		return dozerCitas.map(citas.save(convertirCitas(modeloCita)), ModeloCitas.class);
	}
	

	@Override
	public Citas convertirCitas(ModeloCitas modeloCita) {
		return dozerCitas.map(modeloCita, Citas.class);
	}

	@Override
	public ModeloCitas convertirCitas(Citas cita) {
		return dozerCitas.map(cita, ModeloCitas.class);
	}
}
