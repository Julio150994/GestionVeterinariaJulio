package com.veterinaria.controladores;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.UsuariosImpl;


@Controller
@RequestMapping("/")
public class ClientesPDFController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(ClientesPDFController.class);
	private static final String datosClienteActual = "/citas/datosCliente";
		
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuariosImpl;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	
	// Método para mostrar los datos del cliente actual
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/datosCliente/{id}")
	public ModelAndView mostrarDatosCliente(@ModelAttribute("usuario") ModeloUsuarios cliente) {
		LOG_VETERINARIA.info("Datos personales del cliente");
		ModelAndView mavDatosCliente = new ModelAndView(datosClienteActual);
			
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			
			mavDatosCliente.addObject("usuario",usuariosImpl.buscarId(usuario.getId()));
			
			mavDatosCliente.addObject("clienteActual",usuario.getUsername().toUpperCase());
		}
			
		return mavDatosCliente;
	}
		
	// Método para exportar los datos del cliente a pdf
}
