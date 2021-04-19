package com.veterinaria.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class MenuVeterinariaController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(MenuVeterinariaController.class);
	private static final String menu = "frontend/gestion_veterinaria";
	
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuariosImpl;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	
	@GetMapping("/")
	public String redirectVeterinaria() {
		return "redirect:/menu";
	}
	
	@GetMapping("/menu")
	public String showMenuView(@ModelAttribute("usuario") ModeloUsuarios modeloUsuario, Model modelo) {		
		LOG_VETERINARIA.info("Menú de GestionVeterinaria");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			modelo.addAttribute("usuario",usuario.getId());
			
			String login = "Usuario "+usuario.getUsername()+" logueado éxitosamente";
			LOG_VETERINARIA.info(login);
			modelo.addAttribute("login",login);
		}
		
		return menu;
	}
}
