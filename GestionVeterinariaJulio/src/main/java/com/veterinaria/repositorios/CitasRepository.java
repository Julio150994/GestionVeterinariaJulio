package com.veterinaria.repositorios;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;


@Repository("citasRepository")
public interface CitasRepository extends JpaRepository<Citas, Serializable> {
	public abstract List<Mascotas> findByMascota(Citas cita);
	public abstract List<Usuarios> findByVeterinario(Citas cita);
	
	// Para realizar el contador por fecha de la cita
	public abstract int countByFecha(Date fecha);
}
