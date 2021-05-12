package com.veterinaria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Usuarios;


@Repository("veterinariosRepository")
public interface VeterinariosRepository extends JpaRepository<Usuarios, Integer> {
	
	public abstract Usuarios findByUsername(String username);
}
