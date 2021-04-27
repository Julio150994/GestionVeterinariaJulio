package com.veterinaria.servicios.Impl;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.servicios.CitasService;


@Service("citasImpl")
public class CitasImpl implements CitasService {

	@Autowired
	@Qualifier("citasRepository")
	private CitasRepository citas;
	
	@Autowired
	private DozerBeanMapper dozerCitas;
	
	
	@Override
	public ModeloCitas pedirCita(ModeloCitas modeloCita, ModeloMascotas modeloMascota, ModeloUsuarios modeloVeterinario) {
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
