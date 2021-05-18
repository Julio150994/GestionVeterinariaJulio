package com.veterinaria.controladores;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.lowagie.text.DocumentException;
import com.veterinaria.configuraciones.ExportarDatosCliente;
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
	
	
	/* Método para mostrar los datos del cliente actual */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/datosCliente/{id}")
	public ModelAndView mostrarDatosCliente() {
		LOG_VETERINARIA.info("Datos personales del cliente");
		ModelAndView mavDatosCliente = new ModelAndView(datosClienteActual);
			
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios cliente = usuariosRepository.findByUsername(auth.getName());
			
			mavDatosCliente.addObject("usuario",usuariosImpl.buscarId(cliente.getId()));
			
			mavDatosCliente.addObject("clienteActual",cliente.getUsername().toUpperCase());
		}
			
		return mavDatosCliente;
	}
		
	/* Método para exportar los datos del cliente a pdf */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/datosCliente/pdf/{id}")
	public void mostrarInformeCliente(HttpServletResponse resCliente, Model modelo) throws DocumentException, IOException {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios cliente = usuariosRepository.findByUsername(auth.getName());
			
			resCliente.setContentType("application/pdf");
			
			String clientesPDF = "attachment; filename=informe_de_cliente_"+cliente.getUsername()+".pdf";
			
			resCliente.setHeader("Content-Disposition",clientesPDF);
		
			ModeloUsuarios modeloCliente = usuariosImpl.buscarId(cliente.getId());
			modelo.addAttribute("usuario",modeloCliente);
			
			ExportarDatosCliente pdfCliente = new ExportarDatosCliente(modeloCliente);
			pdfCliente.exportarDatosCliente(resCliente);
		}
	}
}
