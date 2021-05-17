package com.veterinaria.controladores;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.CitasImpl;
import com.veterinaria.servicios.Impl.MascotasImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import com.veterinaria.servicios.Impl.VeterinariosImpl;


@Controller
@RequestMapping("/")
public class CitasController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(CitasController.class);
	private static final String formCita = "/citas/formCita", historialCitas = "/citas/listadoCitas",
			fechasCitas = "/citas/citasPendientes", historialMascota = "/citas/historialMascota", citasDiaActual = "/citas/citaDia",
			citasDiasPosteriores = "/citas/citasPosteriores";
	private String txtCita, txtFechaActual, fechaFormatoNormal;
	
	private Calendar fecha = new GregorianCalendar();
	private int dia = fecha.get(Calendar.DAY_OF_MONTH);
    private int mes = fecha.get(Calendar.MONTH);
    private int anio = fecha.get(Calendar.YEAR);
	
    
	@Autowired
	@Qualifier("citasImpl")
	private CitasImpl citas;
	
	@Autowired
	@Qualifier("mascotasImpl")
	private MascotasImpl mascotas;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl veterinarios;
	
	@Autowired
	@Qualifier("veterinariosImpl")
	private VeterinariosImpl veterinariosImpl;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	@Qualifier("citasRepository")
	private CitasRepository citasRepository;
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotasRepository;
	
	/*------------------------------Métodos para los clientes (ROLE_CLIENTE)-------------------------------------------*/
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping({"/citas/formCita","/citas/formCita/{id}"})
	public ModelAndView formularioCita(@ModelAttribute("cita") ModeloCitas modeloCita, @PathVariable(name="id",required=false) Integer idCita) {
		LOG_VETERINARIA.info("Formulario para pedir la cita");
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavCita = new ModelAndView(formCita);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios clienteActual = usuariosRepository.findByUsername(auth.getName());
			mavCita.addObject("usuario",clienteActual.getId());
			
			//-------------------Para poder añadir cita o modificar cita------------------------
			if(idCita == null) {
				mavCita.addObject("cita", new Citas());
				mavCita.addObject("clienteActual",usuario.getUsername().toUpperCase());
				
				mavCita.addObject("txtVeterinario","Veterinarios no contemplados en la base de datos");
				
				LOG_VETERINARIA.info("Veterinarios listados");
				mavCita.addObject("usuarios",veterinarios.listarUsuarios());
				
				mavCita.addObject("txtMascota",clienteActual.getUsername()+" no tiene mascotas registradas en la base de datos");
				
				LOG_VETERINARIA.info("Mascotas listadas");
				mavCita.addObject("mascotas",mascotasRepository.findByMascotasIdUsuario(clienteActual));
			}
			else {
				mavCita.addObject("cita",citas.buscarIdCita(idCita));
				mavCita.addObject("clienteActual",usuario.getUsername().toUpperCase());
				
				mavCita.addObject("txtVeterinario","Veterinarios no contemplados en la base de datos");
				
				LOG_VETERINARIA.info("Veterinarios listados");
				mavCita.addObject("usuarios",veterinarios.listarUsuarios());
				
				mavCita.addObject("txtMascota",clienteActual.getUsername()+" no tiene mascotas registradas en la base de datos");
				
				LOG_VETERINARIA.info("Mascotas listadas");
				mavCita.addObject("mascotas",mascotasRepository.findByMascotasIdUsuario(clienteActual));
			}
		}
		
		return mavCita;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/citas/saveCita")
	public String pedirCita(@Valid @ModelAttribute("cita") Citas cita, BindingResult validarCita, RedirectAttributes mensajeFlash, Model modeloCita) {
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		int countCitas = citasRepository.countByCitasPendientesAndVeterinario(cita.getFecha(),cita.isRealizada(),cita.getUsuario().getId());
		LOG_VETERINARIA.info("Nº de citas con los datos introducidos: "+countCitas);
		
		if(countCitas < 3) {
			citas.pedirCita(cita);
			
			txtCita = usuario.getUsername()+", has pedido tu cita correctamente";
			LOG_VETERINARIA.info(txtCita);
			mensajeFlash.addFlashAttribute("citaPedida",txtCita);
		}
		else {
			txtCita = usuario.getUsername()+", ya existen más de "+countCitas+" citas pendientes para la fecha "+cita.getFecha()+" y con el veterinario "+cita.getUsuario().getUsername();
			LOG_VETERINARIA.info(txtCita);
			mensajeFlash.addFlashAttribute("citaCancelada",txtCita);
		}
		
		return "redirect:"+formCita;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/listadoCitas")
	public ModelAndView historialCitasMascota(@ModelAttribute("cita") ModeloCitas modeloCita, @ModelAttribute("usuario") ModeloUsuarios modeloUsuario) {
		LOG_VETERINARIA.info("Vista de historial para mascota");
		
		UserDetails usuarioClienteActual = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavCitas = new ModelAndView(historialCitas);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			mavCitas.addObject("usuario",usuario.getId());
			
			mavCitas.addObject("clienteActual",usuarioClienteActual.getUsername().toUpperCase());
			mavCitas.addObject("citasTxt",usuario.getUsername()+" no tiene mascotas registradas en la base de datos");
			
			mavCitas.addObject("mascotas",mascotasRepository.findByIdUsuario(usuario));
		}
		
		return mavCitas;
	}
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')") // Método para mostrar las citas pendientes o no de una determinada mascota
	@PostMapping("/citas/citasMascota")
	public String buscarIdMascota(@ModelAttribute("cita") ModeloCitas modeloCita, @RequestParam(name="nombre",required=false) String nombre, Model cita,
			ModeloMascotas modeloMascota, RedirectAttributes mensajeFlash) {
		UserDetails usuarioClienteActual = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			cita.addAttribute("usuario",usuario.getId());
			
			cita.addAttribute("mascotas",mascotasRepository.findByIdUsuario(usuario));
			
			cita.addAttribute("clienteActual",usuarioClienteActual.getUsername().toUpperCase());
			
			if(citasRepository.countByMascotasWithCita(nombre) == 0)
				cita.addAttribute("txtCita","La mascota "+nombre+" no tiene citas registradas en la base de datos");
			else
				cita.addAttribute("citas",citasRepository.fetchByCitasWithNombre(nombre));
		}
		
		return historialCitas;
	}
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/citasPendientes")
	public ModelAndView calendarioCitas(@ModelAttribute("cita") ModeloCitas modeloCita, Mascotas mascota,
			@RequestParam(name="fecha",required=false) Date fecha, @RequestParam(name="realizada",required=false) boolean realizada,
			@RequestParam(name="id",required=false) Integer idCita, BindingResult validacionCita) {
		LOG_VETERINARIA.info("Vista de calendario con citas pendientes");
		
		UserDetails usuarioClienteActual = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavCitas = new ModelAndView(fechasCitas);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			mavCitas.addObject("usuario",usuario.getId());
			
			mavCitas.addObject("citasTxt",usuarioClienteActual.getUsername()+" no tiene citas pendientes");
			
			mavCitas.addObject("clienteActual",usuarioClienteActual.getUsername().toUpperCase());
			
			mavCitas.addObject("citas",citasRepository.fetchFechasCita(usuario.getId(),realizada));
		}
		return mavCitas;
	}
	
	// Método para anular citas pendientes
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/anularCita/{id}")
	public String anularCita(@ModelAttribute("cita") Citas modeloCita, @PathVariable("id") int id,
			RedirectAttributes mensajeFlash) {
		
		citas.anularCitaPendiente(id);
		
		txtCita = "Cita anulada correctamente";
		LOG_VETERINARIA.info(txtCita);
		mensajeFlash.addFlashAttribute("citaAnulada",txtCita);
		return "redirect:"+fechasCitas;
	}
	
	
	/*----------------------------Métodos para los veterinarios (ROLE_VETERINARIO)---------------------------------------*/
	
	@PreAuthorize("hasRole('ROLE_VETERINARIO')")
	@GetMapping("/citas/historialMascota/{id}")
	public ModelAndView verHistorialMascota(@ModelAttribute("cita") ModeloCitas modeloCita,
			@RequestParam(name="nombre",required=false) String nombreMascota) {
		LOG_VETERINARIA.info("Historial de mascota seleccionada");
		
		ModelAndView mavCitas = new ModelAndView(historialMascota);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			mavCitas.addObject("usuario",usuario.getId());
			
			mavCitas.addObject("citasTxt",usuario.getUsername()+" no tiene mascotas en sus citas");
			
			mavCitas.addObject("citas",citasRepository.findMascotasByVeterinario(usuario.getId()));
		}
		
		return mavCitas;
	}	
	
	@PreAuthorize("hasRole('ROLE_VETERINARIO')")
	@PostMapping("/citas/nombreMascota")
	public String consultarHistorialMascota(@ModelAttribute("cita") ModeloCitas modeloCita, Model cita, ModeloMascotas modeloMascota,
			@RequestParam(name="nombre",required=false) String nombreMascota) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			cita.addAttribute("usuario",usuario.getId());
			
			cita.addAttribute("citas",citasRepository.findMascotasByVeterinario(usuario.getId()));// recargamos de nuevo las mascotas que tiene el veterinario
		
			cita.addAttribute("fechas",citasRepository.listHistorialCitasMascota(usuario.getId(),modeloMascota.getNombre()));
		}
		
		return historialMascota;
	}
	
	@PreAuthorize("hasRole('ROLE_VETERINARIO')")
	@GetMapping("/citas/citaDia/{id}")
	public ModelAndView verCitasDiariasVeterinario(@ModelAttribute("cita") ModeloCitas cita) {
		LOG_VETERINARIA.info("Vista de las citas del día actual");
		
		ModelAndView mavCitas = new ModelAndView(citasDiaActual);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			mavCitas.addObject("usuario",usuario.getId());
		    
		    txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
		    fechaFormatoNormal = dia+"/"+(mes+1)+"/"+anio;
		    
		    Date diaActual = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
		    
			mavCitas.addObject("citasTxt",usuario.getUsername()+" no tiene citas contempladas para el día actual");
			
			mavCitas.addObject("txtFechaActual",fechaFormatoNormal);
			
			mavCitas.addObject("citas",citasRepository.listarCitasDiaActual(diaActual,usuario.getId()));
		}
		
		return mavCitas;
	}
	
	@PreAuthorize("hasRole('ROLE_VETERINARIO')")
	@PostMapping("/realizada/{id}")
	public String realizarCita(@ModelAttribute("cita") ModeloCitas modeloCita, Model cita, ModeloMascotas modeloMascota, @PathVariable("id") Integer id,
			@RequestParam(name="realizada",required=false) boolean realizada, BindingResult clienteValido, RedirectAttributes mensajeFlash, @RequestParam(name="fecha",required=false) Date fecha,
			@RequestParam(name="nombre",required=false) String nombreMascota) {
		LOG_VETERINARIA.info("Historial de mascota seleccionada");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			cita.addAttribute("usuario",usuario.getId());
			
		    txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
		    fechaFormatoNormal = dia+"/"+(mes+1)+"/"+anio;
		    
		    Date diaActual = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
		    
			cita.addAttribute("citas",citasRepository.listarCitasDiaActual(diaActual,usuario.getId()));
		}
		
		cita.addAttribute("fechas",citas.realizarCita(modeloCita,id));
		
		txtCita = "Cita de mascota "+nombreMascota+" realizada correctamente";
		LOG_VETERINARIA.info(txtCita);
		cita.addAttribute("realizada",txtCita);
		
		
		cita.addAttribute("txtFechaActual",fechaFormatoNormal);
		
		return citasDiaActual;
	}
	
	
	@PreAuthorize("hasRole('ROLE_VETERINARIO')")
	@GetMapping("/citas/citasPosteriores/{id}")
	public ModelAndView visualizarCitasPosteriores(@ModelAttribute("cita") ModeloCitas cita) {
		LOG_VETERINARIA.info("Vista con las citas posteriores");
		
		ModelAndView mavCitas = new ModelAndView(citasDiasPosteriores);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			mavCitas.addObject("usuario",usuario.getId());
		    
		    txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
		    fechaFormatoNormal = dia+"/"+(mes+1)+"/"+anio;
		    
		    Date diaActual = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
		    
			mavCitas.addObject("citasTxt",usuario.getUsername()+" no tiene citas pendientes para fechas posteriores a "+fechaFormatoNormal);
			
			mavCitas.addObject("veterinarioActual",usuario.getUsername().toUpperCase());
			
			mavCitas.addObject("txtFechaActual",fechaFormatoNormal);
			
			
			mavCitas.addObject("citas",citasRepository.listarCitasDiasPosteriores(diaActual,cita.isRealizada(),usuario.getId()));
		}
		
		return mavCitas;
	}
}
