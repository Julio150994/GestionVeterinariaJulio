package com.veterinaria.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		.authorizeRequests()
			.antMatchers("/","/auth/**","/build/**","/css/**","/error/**","/clientes/**","/veterinarios/**","/frontend/**","/images/**","/mascotasImg/**","/js/**","/maps/**","/vendors/**","/webjars/**").permitAll()
			.antMatchers("/menu","/registrarCliente","/auth/registrarCliente","/auth/login").permitAll()
			.antMatchers("/clientes/listadoClientes","/clientes/formCliente","/clientes/formCliente/{id}","/clientes/saveCliente",
					"/clientes/eliminarCliente/{id}","/clientes/mostrarCliente").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/veterinarios/listadoVeterinarios","/veterinarios/formVeterinario","/veterinarios/formVeterinario/{id}","/veterinarios/saveVeterinario",
					"/veterinarios/eliminarVeterinario/{id}","/veterinarios/mostrarVeterinario").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/actived/{id}").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/perfil_cliente/{id}","/editarPerfil").access("hasRole('ROLE_CLIENTE')")
			.antMatchers("/mascotas/listadoMascotas","/mascotas/formMascota","/mascotas/formMascota/{id}","/mascotas/saveMascota",
					"/mascotas/eliminarMascota/{id}","/mascotas/mostrarMascota").access("hasRole('ROLE_CLIENTE')")
			.antMatchers("/citas/formCita","/citas/saveCita").access("hasRole('ROLE_CLIENTE')")
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
