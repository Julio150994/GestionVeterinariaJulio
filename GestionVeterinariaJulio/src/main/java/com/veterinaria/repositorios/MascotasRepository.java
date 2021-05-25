package com.veterinaria.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;


@Repository("mascotasRepository")
public interface MascotasRepository extends JpaRepository<Mascotas, Integer> {
	public abstract List<Usuarios> findByUsuario(Mascotas mascota);
	
	@Query("select m from Mascotas m where m.usuario =  :usuario group by m.id")
	public abstract List<Mascotas> findByMascotasIdUsuario(@Param("usuario") Usuarios usuario);
	
	@Query("select m from Mascotas m where m.usuario =  :usuario group by m.nombre")
	public abstract List<Mascotas> findByIdUsuario(@Param("usuario") Usuarios usuario);
	
	// SELECT * FROM mascotas WHERE nombre IS NULL
	@Query("select m from Mascotas m where m.nombre is null")
	public abstract List<Mascotas> findMascotasByNullValue(@Param("nombre") String nombre);
}
