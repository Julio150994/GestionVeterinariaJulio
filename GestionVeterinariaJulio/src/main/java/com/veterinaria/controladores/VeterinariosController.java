package com.veterinaria.controladores;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.servicios.Impl.VeterinariosImpl;


@Controller
@RequestMapping("/veterinarios")
public class VeterinariosController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(VeterinariosController.class);
	private static final String vista_veterinarios = "/veterinarios/listadoVeterinarios",
			formAniadir = "/veterinarios/formVeterinario";
	
	@Autowired
	@Qualifier("veterinariosImpl")
	private VeterinariosImpl veterinarios;
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listadoVeterinarios")
	public ModelAndView mostrarVeterinarios() {
		LOG_VETERINARIA.info("Listado de veterinarios mostrada");
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavUsuario = new ModelAndView(vista_veterinarios);
		mavUsuario.addObject("nombre",usuario.getUsername());
		mavUsuario.addObject("veterinarios",veterinarios.listarVeterinarios());
		return mavUsuario;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping({"/formVeterinario","/formVeterinario/{id}"})
	public ModelAndView formularioVeterinario(@PathVariable("id") int id) {
		LOG_VETERINARIA.info("Formulario de veterinario");
		ModelAndView mavVeterinario = new ModelAndView(formAniadir);
		mavVeterinario.addObject("veterinarios",new Usuarios());
		return mavVeterinario;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/saveVeterinario")
	public String guardarVeterinario(@ModelAttribute("veterinarios") ModeloUsuarios veterinario, RedirectAttributes mensajeFlash) {
		if(veterinario.getId() == 0) {
			veterinarios.aniadirVeterinario(veterinario);
			mensajeFlash.addFlashAttribute("aniadido","Veterinario "+veterinario.getUsername()+" añadido éxitosamente");
		}
		else {
			veterinarios.editarVeterinario(veterinario);
			mensajeFlash.addFlashAttribute("editado","Veterinario "+veterinario.getUsername()+" editado éxitosamente");
		}
		return "redirect:/"+vista_veterinarios;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/eliminarVeterinario/{id}")
	public String eliminarVeterinario(ModeloUsuarios veterinario, @PathVariable("id") int id, RedirectAttributes mensajeFlash) {
		if(id > 0) {
			veterinarios.eliminarVeterinario(veterinario, id);
			mensajeFlash.addFlashAttribute("eliminado","Veterinario "+veterinario.getUsername()+" eliminado éxitosamente");
		}
		
		return "redirect:/"+vista_veterinarios;
	}
}
