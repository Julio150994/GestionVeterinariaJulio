package com.veterinaria.controladores;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.servicios.UsuariosService;
import com.veterinaria.servicios.Impl.UsuariosImpl;


@Controller
@RequestMapping("/")
public class UsuariosController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(UsuariosController.class);
	private static final String vista_login = "login";
	
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuarios;
	
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuariosService;
	
	
	@GetMapping("/auth/login")
	public ModelAndView formLoginUser(@ModelAttribute("usuario") ModeloUsuarios modeloUsuario, @RequestParam(name="error",required=false) String mensajeError,
			@RequestParam(name="logout_user",required=false) String logoutUser) {
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
			mavUsuario.addObject("logout_user",logoutUser);
		}
		
		return mavUsuario;
	}
	
	
	@GetMapping("/auth/loginUserPost")
	public String iniciarSesion() {
		return "redirect:/menu";
	}
}
