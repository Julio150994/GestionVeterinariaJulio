package com.veterinaria.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.veterinaria.servicios.UsuariosService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityVeterinaria extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuarios;
	
	
	//--------------Configuración de seguridad para el api rest---------------------
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder authSecurity) throws Exception {
		authSecurity.userDetailsService(usuarios).passwordEncoder(encriptarContrasenia());
	}
	
	@Bean
	public BCryptPasswordEncoder encriptarContrasenia() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity gestionVeterinaria) throws Exception {		
		gestionVeterinaria
		.cors()
		.and()
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/","/auth/**","/build/**","/css/**","/error/**","/clientes/**","/veterinarios/**","/frontend/**","/images/**","/mascotasImg/**","/js/**","/maps/**","/vendors/**","/webjars/**").permitAll()
			.antMatchers("/menu","/registrarCliente","/auth/registrarCliente","/auth/login").permitAll()
			.antMatchers("clientes/listadoClientes","clientes/formCliente","clientes/formCliente/{id}","/clientes/saveCliente",
					"clientes/eliminarCliente/{id}","clientes/mostrarCliente").access("hasRole('ROLE_ADMIN')")
			.antMatchers("veterinarios/listadoVeterinarios","veterinarios/formVeterinario","veterinarios/formVeterinario/{id}","veterinarios/saveVeterinario",
					"veterinarios/eliminarVeterinario/{id}","veterinarios/mostrarVeterinario").access("hasRole('ROLE_ADMIN')")
			.antMatchers("actived/{id}").access("hasRole('ROLE_ADMIN')")
			.antMatchers("perfil_cliente/{id}","editarPerfil").access("hasRole('ROLE_CLIENTE')")
			.antMatchers("mascotas/listadoMascotas").access("hasRole('ROLE_CLIENTE') or hasRole('ROLE_VETERINARIO')")
			.antMatchers("mascotas/formMascota","mascotas/formMascota/{id}","mascotas/saveMascota",
					"mascotas/eliminarMascota/{id}","mascotas/mostrarMascota").access("hasRole('ROLE_CLIENTE')")
			.antMatchers("citas/formCita","citas/saveCita","citas/listadoCitas","citas/citasMascota","citas/citasPendientes","citas/formCita/{id}",
					"citas/anularCita/{id}","citas/datosCliente/{id}","citas/datosCliente/pdf/{id}").access("hasRole('ROLE_CLIENTE')")
			.antMatchers("citas/historialMascota/{id}","citas/nombreMascota","citas/citaDia/{id}","realizada/{id}","citas/citasPosteriores/{id}").access("hasRole('ROLE_VETERINARIO')")
			.antMatchers(HttpMethod.POST,"apiVeterinaria/login").permitAll()
			.antMatchers(HttpMethod.GET,"apiVeterinaria/cliente/citas").access("hasRole('ROLE_CLIENTE')")
			.antMatchers(HttpMethod.POST,"apiVeterinaria/logout").access("hasRole('ROLE_CLIENTE')")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/auth/login")
			.defaultSuccessUrl("/menu",true)
			.loginProcessingUrl("/auth/loginUserPost")
			.permitAll()
			.and()
		.logout()
			.logoutUrl("/logout_user")
			.logoutSuccessUrl("/auth/login?logout_user")
			.permitAll();
	}
}
