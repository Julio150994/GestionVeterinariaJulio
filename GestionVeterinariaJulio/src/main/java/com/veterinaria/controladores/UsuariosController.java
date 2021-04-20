package com.veterinaria.controladores;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.UsuariosService;
import com.veterinaria.servicios.Impl.UsuariosImpl;


@Controller
@RequestMapping("/")
public class UsuariosController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(UsuariosController.class);
	private static final String vista_login = "login", vista_clientes = "listadoClientes";
	
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuariosImpl;
	
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuariosService;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	
	@GetMapping("/auth/login")
	public ModelAndView formLoginUser(@ModelAttribute("usuario") ModeloUsuarios modeloUsuario, @RequestParam(name="error",required=false) String mensajeError,
			@RequestParam(name="logout_user",required=false) String logoutUser, Model modelo) {
		LOG_VETERINARIA.info("Vista de login");
		
		ModelAndView mavUsuario = new ModelAndView(vista_login);
		mavUsuario.addObject("usuarios",new ModeloUsuarios());
		
		if(mensajeError != null) {
			mensajeError = "Debe introducir todos los datos para iniciar sesión";
			LOG_VETERINARIA.info(mensajeError);
			mavUsuario.addObject("error",mensajeError);
		}
		
		if(logoutUser != null) {
			logoutUser = " Ha cerrado sesión éxitosamente";
			LOG_VETERINARIA.info(logoutUser);
			modelo.addAttribute("logout_user",logoutUser);
		}
		
		return mavUsuario;
	}
	
	
	@GetMapping("/auth/loginUserPost")
	public String iniciarSesion() {
		return "redirect:/menu";
	}	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listadoClientes")
	public ModelAndView verListadoClientes(@ModelAttribute("usuario") ModeloUsuarios modeloUsuario) {
		LOG_VETERINARIA.info("Vista de activar/desactivar usuarios");
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String nombreUsuario = usuario.getUsername();
		
		ModelAndView mavUsuarios = new ModelAndView(vista_clientes);
		mavUsuarios.addObject("usuarios",usuariosImpl.listarUsuarios());
		mavUsuarios.addObject("nombre",nombreUsuario.toUpperCase());// mostramos el nombre del usuario en mayúsculas
		return mavUsuarios;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/actived/{id}")
	public String gestionarCliente(@ModelAttribute("usuario") ModeloUsuarios usuario, @PathVariable("id") int id) {
		usuariosImpl.enabledCliente(id, usuario);
		return "redirect:/"+vista_clientes;
	}
}
