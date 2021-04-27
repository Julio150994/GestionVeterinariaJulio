package com.veterinaria.controladores;

import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.servicios.Impl.CitasImpl;
import com.veterinaria.servicios.Impl.MascotasImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;


@Controller
@RequestMapping("/")
public class CitasController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(CitasController.class);
	private static final String formCita = "/citas/formCita";
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl veterinarios;
	
	@Autowired
	@Qualifier("mascotasImpl")
	private MascotasImpl mascotas;
	
	@Autowired
	@Qualifier("citasImpl")
	private CitasImpl citas;
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/citas/formCita")
	public ModelAndView formularioCita(ModeloUsuarios modeloUsuario, ModeloMascotas modeloMascota) {
		LOG_VETERINARIA.info("Formulario para pedir la cita");
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavCita = new ModelAndView(formCita);
		mavCita.addObject("cita", new Citas());
		mavCita.addObject("cliente",usuario.getUsername().toUpperCase());
		
		if(modeloUsuario.getRol() == "ROLE_VETERINARIO")
			mavCita.addObject("veterinarios",veterinarios.listarUsuarios());
		else {
			LOG_VETERINARIA.info("Veterinarios listados");
			mavCita.addObject("veterinarios","Veterinarios no encontrados");
		}
		
		if(modeloMascota.getId() == 0)
			mavCita.addObject("mascotas","Mascotas no encontradas");
		else {
			LOG_VETERINARIA.info("Mascotas listadas");
			mavCita.addObject("mascotas",mascotas.listarMascotas());
		}
		
		return mavCita;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/citas/saveCita")
	public String pedirCita(@Valid @ModelAttribute("cita") ModeloCitas modeloCita, ModeloUsuarios modeloUsuario, ModeloMascotas modeloMascota,
			BindingResult validarCita, RedirectAttributes mensajeFlash, Model cita) {
		if(validarCita.hasErrors()) {
			LOG_VETERINARIA.info("Error en la validación de la cita");
			cita.addAttribute("mascotas",mascotas.listarMascotas());
			cita.addAttribute("veterinarios",veterinarios.listarUsuarios());
		}
		else {
			citas.pedirCita(modeloCita, modeloMascota, modeloUsuario);
			
			String txtCita = "Esta cita se ha pedido correctamente";
			LOG_VETERINARIA.info(txtCita);
			mensajeFlash.addFlashAttribute("citaPedida",txtCita);
		}
		
		return "redirect:"+formCita;	
	}
}
