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
	public abstract List<Usuarios> findByUsuario(Citas cita);
	
	
	@Query("select count(c) from Citas c, Usuarios u where c.fecha = :fecha and c.realizada = :realizada and c.usuario = u.id and u.id = :id")
	public abstract int countByCitasPendientesAndVeterinario(@Param("fecha") Date fecha, @Param("realizada") boolean realizada, @Param("id") int idVeterinario);
	
	@Query("select count(c) from Citas c, Mascotas m where c.mascota = m.id and m.nombre= :nombre order by c.fecha desc")
	public abstract int countByMascotasWithCita(@Param("nombre") String nombre);
	
	@Query("select c from Citas c, Mascotas m where c.mascota = m.id and m.nombre= :nombre order by c.fecha desc")
	public abstract List<Citas> fetchByCitasWithNombre(@Param("nombre") String nombre);
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.usuario = u.id and u.id = :id and c.realizada = :realizada order by c.fecha desc")
	public abstract List<Citas> fetchFechasCita(@Param("id") int id, @Param("realizada") boolean realizada);
	
	public abstract List<Citas> findByFecha(Date fecha);// buscar las citas a través de la fecha seleccionada
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and c.usuario = u.id and u.id = :id group by m.nombre")
	public abstract List<Citas> findMascotasByVeterinario(@Param("id") int id);
	
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and c.usuario = u.id and u.id = :id and m.nombre = :nombre and c.realizada = :realizada")
	public abstract List<Citas> listHistorialCitasMascota(@Param("id") int id, @Param("nombre") String nombre, @Param("realizada") boolean realizada);
	
	@Query("select count(c) from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and c.usuario = u.id and u.id = :id and m.nombre = :nombre and c.realizada = :realizada")
	public abstract int countByCitasRealizadas(@Param("id") int id, @Param("nombre") String nombre, @Param("realizada") boolean realizada);
	
		
	@Query("select c from Citas c, Usuarios u where c.fecha = :fecha and c.usuario = u.id and u.id = :id")
	public abstract List<Citas> listarCitasDiaActual(@Param("fecha") Date fecha, @Param("id") int id);
	
	
	@Query("select c from Citas c, Usuarios u where c.fecha > :fecha and c.realizada = :realizada and c.usuario = u.id and u.id = :id")
	public abstract List<Citas> listarCitasDiasPosteriores(@Param("fecha") Date fecha, @Param("realizada") boolean realizada, @Param("id") int id);
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.usuario = u.id and c.usuario = :usuario group by u.username")
	public abstract List<Citas> listarClientesByVeterinario(@Param("usuario") Usuarios cliente);
	
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.usuario = u.id and u.id = :id")
	public abstract List<Citas> findMascotasWithCitasByUsuario(@Param("id") int id);
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.usuario = u.id and u.id = :id and c.realizada = :realizada group by m.nombre")
	public abstract List<Citas> findMascotasByCitasRealizadas(@Param("id") int id, @Param("realizada") boolean realizada);
	
	@Query("select c from Citas c, Mascotas m, Usuarios u where c.mascota = m.id and m.usuario = u.id and m.nombre = :nombre and c.fecha <= :fecha and c.realizada = :realizada")
	public abstract List<Citas> findCitasByMascotaCliente(@Param("nombre") String nombre, @Param("fecha") Date fecha, @Param("realizada") boolean realizada);
}
