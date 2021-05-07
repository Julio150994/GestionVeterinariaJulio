package com.veterinaria.repositorios;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;


@Repository("citasRepository")
public interface CitasRepository extends JpaRepository<Citas, Integer> {
	public abstract List<Mascotas> findByMascota(Citas cita);
	public abstract List<Usuarios> findByVeterinario(Citas cita);
	
	public abstract int countByFecha(Date fecha);// realizar el contador por fecha de la cita
	
	@Query("select c from Citas c, Mascotas m where c.mascota = m.id and m.nombre= :nombre")
	public abstract List<Citas> fetchByCitasWithNombre(@Param("nombre") String nombre);
	
	@Query("select distinct c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.cliente = u.id and u.id = :id and c.realizada = :realizada")
	public abstract List<Citas> fetchFechasCita(@Param("id") int id, @Param("realizada") boolean realizada);
	
	public abstract List<Citas> findByFecha(Date fecha);// buscar las citas a través de la fecha seleccionada
	
	@Query("select c from Citas c, Usuarios u where c.veterinario = u.id and u.id = :id")
	public abstract List<Citas> findByIdVeterinario(@Param("id") int idVeterinario);
}
