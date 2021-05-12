package com.veterinaria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Usuarios;


@Repository("usuariosRepository")
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
	
	public abstract Usuarios findByUsername(String username);
}
