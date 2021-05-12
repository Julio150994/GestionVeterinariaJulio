package com.veterinaria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veterinaria.entidades.Usuarios;


@Repository("clientesRepository")
public interface ClientesRepository extends JpaRepository<Usuarios, Integer> {

}
