package com.veterinaria.repositorios;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Citas;


@Repository("citasRepository")
public interface CitasRepository extends JpaRepository<Citas, Serializable> {
	
}
