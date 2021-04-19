package com.veterinaria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.servicios.UsuariosService;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Controller
@RequestMapping("/")
public class ClientesController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(ClientesController.class);
	private static final String menu = "menu", formulario = "registrarCliente", vista_perfil = "perfil_cliente";
	
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuarios;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl clientes;
	
	
	@GetMapping("/registrarCliente")
	public ModelAndView verFormularioRegistar() {
		LOG_VETERINARIA.info("Formulario de registrar cliente");
		ModelAndView mavCliente = new ModelAndView(formulario);
		mavCliente.addObject("usuarios",new Usuarios());
		return mavCliente;
	}

	@PostMapping("/auth/registrarCliente")
	public String registrarCliente(@Valid @ModelAttribute("usuarios") Usuarios usuario,
			BindingResult validacionCliente, RedirectAttributes mensajeFlash) {
		String error = "Error al registar nuevo cliente", registrado = usuario.getUsername()+" registrado correctamente";
		
		if(validacionCliente.hasErrors()) {
			LOG_VETERINARIA.info(error);
			mensajeFlash.addFlashAttribute("errorCliente",error);
			return formulario;
		}
		else {
			usuarios.registrarUsuario(usuario);
			LOG_VETERINARIA.info(registrado);
			mensajeFlash.addFlashAttribute("registrar",registrado);
			return "redirect:/"+menu;
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@RequestMapping(value="/perfil_cliente/{id}",method=RequestMethod.GET)
	public ModelAndView verPerfilCliente(@ModelAttribute("usuario") ModeloUsuarios cliente, @PathVariable("id") int id) {		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usuario.getUsername();
		
		ModelAndView mavCliente = new ModelAndView(vista_perfil);
		mavCliente.addObject("cliente",username.toUpperCase());
		mavCliente.addObject("usuario",clientes.buscarId(id));
		return mavCliente;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/editarPerfil")
	public String editarPerfilCliente(@Valid @ModelAttribute("usuario") ModeloUsuarios cliente,
			BindingResult validacionPerfil, RedirectAttributes mensajeFlash) {
		String error = "Error al registar nuevo cliente";
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String nombreUsuario = usuario.getUsername();
		
		if(validacionPerfil.hasErrors()) {
			LOG_VETERINARIA.info(error);
			mensajeFlash.addFlashAttribute("errorPerfil",error);
			return vista_perfil;
		}
		else {
			clientes.editarPerfil(cliente);
			
			String perfil = "Perfil de "+nombreUsuario+" editado correctamente";
			LOG_VETERINARIA.info(perfil);
			mensajeFlash.addFlashAttribute("editado",perfil);
			
			return "redirect:/"+menu;
		}
	}
}
