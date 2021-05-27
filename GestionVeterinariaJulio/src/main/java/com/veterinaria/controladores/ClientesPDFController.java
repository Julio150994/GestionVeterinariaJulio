package com.veterinaria.controladores;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lowagie.text.DocumentException;
import com.veterinaria.configuraciones.ExportarDatosCliente;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.UsuariosImpl;


@Controller
@RequestMapping("/")
public class ClientesPDFController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(ClientesPDFController.class);
	private static final String datosClienteActual = "/citas/datosCliente";
	
	private String txtFechaActual;
	
	private Calendar fecha = new GregorianCalendar();
	private int dia = fecha.get(Calendar.DAY_OF_MONTH);
    private int mes = fecha.get(Calendar.MONTH);
    private int anio = fecha.get(Calendar.YEAR);
		
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuariosImpl;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotas;
	
	@Autowired
	@Qualifier("citasRepository")
	private CitasRepository citas;
	
	
	/* Método para mostrar los datos del cliente actual */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/datosCliente/{id}")
	public ModelAndView mostrarDatosCliente(@ModelAttribute("cita") ModeloCitas modeloCita, ModeloMascotas modeloMascota) {
		LOG_VETERINARIA.info("Datos personales del cliente");
		ModelAndView mavDatosCliente = new ModelAndView(datosClienteActual);
			
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios cliente = usuariosRepository.findByUsername(auth.getName());
			
			mavDatosCliente.addObject("usuario",usuariosImpl.buscarId(cliente.getId()));
			
			mavDatosCliente.addObject("txtCitasMascota",cliente.getUsername()+" no tiene mascotas registradas en la base de datos");
			
			//---------Para mostrar en el select las mascotas que tengan citas tanto pendientes como realizadas---------
			boolean realizada = modeloCita.isRealizada();
			realizada = true;
			
			mavDatosCliente.addObject("citas",citas.findMascotasByCitasExistentes(cliente.getId(),realizada));
			
			mavDatosCliente.addObject("clienteActual",cliente.getUsername().toUpperCase());
		}
			
		return mavDatosCliente;
	}
		
	/* Método para exportar los datos del cliente a pdf */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/datosCliente/pdf/{id}")
	public void mostrarInformeCliente(HttpServletResponse resCliente, Model modelo, @Valid @ModelAttribute("mascota") ModeloMascotas mascota,
			ModeloCitas cita, RedirectAttributes mensajeFlash) throws DocumentException, IOException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios cliente = usuariosRepository.findByUsername(auth.getName());
			
			boolean realizada = cita.isRealizada();
			realizada = true;
			
			txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
		    Date fechaCita = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
			
			List<Citas> modeloCita = citas.findCitasByMascotaCliente(mascota.getNombre(),fechaCita,realizada);
			LOG_VETERINARIA.info(mascota.getNombre()+" seleccionado correctamente");
			
			resCliente.setContentType("application/pdf");
			
			String clientesPDF = "attachment; filename=informe_cliente_"+cliente.getUsername()+".pdf";
			
			resCliente.setHeader("Content-Disposition",clientesPDF);
		
			ModeloUsuarios modeloCliente = usuariosImpl.buscarId(cliente.getId());
			modelo.addAttribute("usuario",modeloCliente);
			
			ExportarDatosCliente pdfCliente = new ExportarDatosCliente(modeloCliente,modeloCita);
			pdfCliente.exportarDatosCliente(resCliente);
		}
	}
}
