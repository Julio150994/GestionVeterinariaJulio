package com.veterinaria.repositorios;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;


@Repository("mascotasRepository")
public interface MascotasRepository extends JpaRepository<Mascotas, Serializable> {
	public abstract List<Usuarios> findByCliente(Mascotas mascota);
}
