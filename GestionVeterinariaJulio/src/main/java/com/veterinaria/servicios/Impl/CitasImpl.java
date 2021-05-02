package com.veterinaria.servicios.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.veterinaria.conversiones.CitasConverter;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
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
	@Qualifier("citasConverter")
	private CitasConverter conversorCitas;
	
	
	
	@Autowired
	private DozerBeanMapper dozerCitas;
	
	
	@Override
	public ModeloCitas pedirCita(ModeloCitas modeloCita) {
		modeloCita.setRealizada(false);// establecemos la cita para saber que inicialmente no se ha realizado
		return dozerCitas.map(citas.save(convertirCitas(modeloCita)), ModeloCitas.class);
	}
	
	@Override
	public List<ModeloUsuarios> listarVeterinarios(ModeloCitas modeloCita) {
		return citas.findByVeterinario(conversorCitas.convertirCitas(modeloCita))
				.stream().map(v->dozerCitas.map(v, ModeloUsuarios.class)).collect(Collectors.toList());
	}

	@Override
	public List<ModeloMascotas> listarMascotas(ModeloCitas modeloCita) {
		return citas.findByMascota(conversorCitas.convertirCitas(modeloCita))
				.stream().map(m->dozerCitas.map(m, ModeloMascotas.class)).collect(Collectors.toList());
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
