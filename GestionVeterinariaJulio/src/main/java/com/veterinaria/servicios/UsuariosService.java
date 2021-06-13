package com.veterinaria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.repositorios.UsuariosRepository;


@Service("usuariosService")
public class UsuariosService implements UserDetailsService {
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuarios;
	
	@Autowired
	private BCryptPasswordEncoder encriptar;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.veterinaria.entidades.Usuarios usuario = usuarios.findByUsername(username);
		
		UserBuilder usuarioCliente = null;
		
		if(usuario != null) {
			usuarioCliente = User.withUsername(username);
			usuarioCliente.password(usuario.getPassword());
			usuarioCliente.authorities(new SimpleGrantedAuthority(usuario.getRol()));
		}
		else
			throw new UsernameNotFoundException("Cliente no encontrado en GestionVeterinaria");
		
		return usuarioCliente.build();
	}
	
	public Usuarios registrarUsuario(Usuarios usuario) {
		usuario.setNombre(usuario.getNombre());
		usuario.setApellidos(usuario.getApellidos());
		usuario.setTelefono(usuario.getTelefono());
		usuario.setUsername(usuario.getUsername());
		usuario.setPassword(encriptar.encode(usuario.getPassword()));
		usuario.setActivado(false);
		
		if(usuario.getRol() == "ROLE_ADMIN")
			usuario.setRol("ROLE_ADMIN");
		else if(usuario.getRol() == "ROLE_VETERINARIO")
			usuario.setRol("ROLE_VETERINARIO");
		else if(usuario.getRol() == "ROLE_CLIENTE")
			usuario.setRol("ROLE_CLIENTE");
		return usuarios.save(usuario);
	}
	
}
