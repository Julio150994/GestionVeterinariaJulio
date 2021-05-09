package com.veterinaria.repositorios;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Usuarios;


@Repository("usuariosRepository")
public interface UsuariosRepository extends JpaRepository<Usuarios, Serializable> {
	
	public abstract Usuarios findByUsername(String username);
}
