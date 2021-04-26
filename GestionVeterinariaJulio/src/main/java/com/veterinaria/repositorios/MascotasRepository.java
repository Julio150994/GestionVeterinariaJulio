package com.veterinaria.repositorios;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Mascotas;


@Repository("mascotasRepository")
public interface MascotasRepository extends JpaRepository<Mascotas, Serializable> {
	
}
