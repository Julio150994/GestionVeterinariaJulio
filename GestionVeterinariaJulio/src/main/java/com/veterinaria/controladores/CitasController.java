package com.veterinaria.controladores;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.CitasImpl;
import com.veterinaria.servicios.Impl.MascotasImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import com.veterinaria.servicios.Impl.VeterinariosImpl;


@Controller
@RequestMapping("/")
public class CitasController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(CitasController.class);
	private static final String formCita = "/citas/formCita";
	private String txtCita;
	
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
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/formCita")
	public ModelAndView formularioCita(@ModelAttribute("veterinario") ModeloUsuarios modeloVeterinario,
			@ModelAttribute("mascota") ModeloMascotas modeloMascota) {
		LOG_VETERINARIA.info("Formulario para pedir la cita");
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavCita = new ModelAndView(formCita);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios clienteActual = usuariosRepository.findByUsername(auth.getName());
			mavCita.addObject("usuario",clienteActual.getId());
		
			mavCita.addObject("cita", new Citas());
			mavCita.addObject("clienteActual",usuario.getUsername().toUpperCase());
			
			if(modeloVeterinario == null) {
				txtCita = "Veterinarios no encontrados";
				LOG_VETERINARIA.info(txtCita);
				mavCita.addObject("veterinario",txtCita);
			}
			else {
				LOG_VETERINARIA.info("Veterinarios listados");
				mavCita.addObject("veterinarios",veterinarios.listarUsuarios());
			}
			
			if(modeloMascota == null) {
				txtCita = "Mascotas no encontradas";
				LOG_VETERINARIA.info(txtCita);
				mavCita.addObject("mascota",txtCita);
			}
			else {
				LOG_VETERINARIA.info("Mascotas listadas");
				mavCita.addObject("mascotas",mascotas.listarMascotas());
			}
		}
		
		return mavCita;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/citas/saveCita")
	public String pedirCita(@Valid @ModelAttribute("cita") ModeloCitas cita, BindingResult validarCita, ModeloMascotas mascota,
			ModeloUsuarios veterinario, RedirectAttributes mensajeFlash, Model modeloCita) {
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		long countCitas = citasRepository.countByFecha(cita.getFecha());
		LOG_VETERINARIA.info("Nº de citas con la fecha introducida: "+countCitas);
		
		if(countCitas < 3) {
			citas.pedirCita(cita);
			
			txtCita = usuario.getUsername()+", has pedido tu cita correctamente";
			LOG_VETERINARIA.info(txtCita);
			mensajeFlash.addFlashAttribute("citaPedida",txtCita);
		}
		else {
			txtCita = usuario.getUsername()+", no puedes añadir más citas para la fecha "+cita.getFecha();
			LOG_VETERINARIA.info(txtCita);
			mensajeFlash.addFlashAttribute("citaCancelada",txtCita);
		}
		
		return "redirect:"+formCita;
	}
}
