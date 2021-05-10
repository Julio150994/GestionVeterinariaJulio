package com.veterinaria.repositorios;

import java.io.Serializable;
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
public interface CitasRepository extends JpaRepository<Citas, Serializable> {
	public abstract List<Mascotas> findByMascota(Citas cita);
	public abstract List<Usuarios> findByUsuario(Citas cita);
	
	public abstract int countByFecha(Date fecha);// realizar el contador por fecha de la cita e idVeterinario
	
	@Query("select c from Citas c, Mascotas m where c.mascota = m.id and m.nombre= :nombre")
	public abstract List<Citas> fetchByCitasWithNombre(@Param("nombre") String nombre);
	
	@Query("select distinct c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.usuario = u.id and u.id = :id and c.realizada = :realizada order by c.fecha asc")
	public abstract List<Citas> fetchFechasCita(@Param("id") int id, @Param("realizada") boolean realizada);
	
	public abstract List<Citas> findByFecha(Date fecha);// buscar las citas a través de la fecha seleccionada
	
	@Query("select c from Citas c, Usuarios u where c.usuario = u.id and u.id = :id")
	public abstract List<Citas> findByIdVeterinario(@Param("id") int idVeterinario);
	
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and c.usuario = u.id and u.id = :id")
	public abstract List<Citas> findMascotasByVeterinario(@Param("id") int id);
	
	
	@Query("select c from Citas c, Usuarios u, Mascotas m where c.usuario = u.id and c.mascota = m.id and u.id = :id and m.nombre = :nombre")
	public abstract List<Citas> listHistorialCitasMascota(@Param("id") int id, @Param("nombre") String nombre);
}
