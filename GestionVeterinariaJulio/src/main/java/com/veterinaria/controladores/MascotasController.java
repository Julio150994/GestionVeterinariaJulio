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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.servicios.Impl.MascotasImpl;


@Controller
@RequestMapping("/")
public class MascotasController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(MascotasController.class);
	private static final String vista_mascotas = "/mascotas/listadoMascotas",
			formMascota = "/mascotas/formMascota", datosMascota = "/mascotas/mostrarMascota";
	private String txtMascota;
	
	@Autowired
	@Qualifier("mascotasImpl")
	private MascotasImpl mascotas;
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/mascotas/listadoMascotas")
	public ModelAndView listarMascotas() {
		LOG_VETERINARIA.info("Vista de listado de mascotas");
		UserDetails usuarioClienteActual = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavMascotas = new ModelAndView(vista_mascotas);
		mavMascotas.addObject("cliente",usuarioClienteActual.getUsername());
		mavMascotas.addObject("mascotasTxt",usuarioClienteActual.getUsername()+" no tiene mascotas");
		mavMascotas.addObject("mascotas",mascotas.listarMascotas());
		return mavMascotas;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping({"/mascotas/formMascota","/mascotas/formMascota/{id}"})
	public String formularioMascota(@PathVariable(name="id",required=false) Integer id,
			Model modeloMascota) {
		
		LOG_VETERINARIA.info("Formulario de mascota");
		
		//modeloMascota.addAttribute("nombre",usuario.getUsername().toUpperCase());
		
		if(id == null)
			modeloMascota.addAttribute("mascota",new ModeloMascotas());
		else
			modeloMascota.addAttribute("mascota",mascotas.buscarIdMascota(id));
		return formMascota;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/mascotas/saveMascota")
	public String saveMascota(@Valid @ModelAttribute("mascota") ModeloMascotas mascota, BindingResult validarMascota,
			RedirectAttributes mensajeFlash) {
		
		if(validarMascota.hasErrors())
			return "redirect:"+formMascota;
		else {
			if(mascota.getId() == 0) {
				mascotas.aniadirMascota(mascota);
				
				txtMascota = "Mascota añadida correctamente";
				LOG_VETERINARIA.info(txtMascota);
				mensajeFlash.addFlashAttribute("insertado",txtMascota);
			}
			else {
				mascotas.editarMascota(mascota);
				
				txtMascota = "Mascota editada correctamente";
				LOG_VETERINARIA.info(txtMascota);
				mensajeFlash.addFlashAttribute("editado",txtMascota);
			}
			return "redirect:"+vista_mascotas;
		}
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/mascotas/eliminarMascota/{id}")
	public String eliminarMascota(@ModelAttribute("mascota") ModeloMascotas mascota, @PathVariable("id") int id,
			RedirectAttributes mensajeFlash) {
		
		mascotas.eliminarMascota(id);
		
		txtMascota = "Mascota eliminada correctamente";
		LOG_VETERINARIA.info(txtMascota);
		mensajeFlash.addFlashAttribute("eliminado",txtMascota);
		return "redirect:"+vista_mascotas;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/mascotas/mostrarMascota/{id}")
	public String mostrarDatosMascota(@PathVariable int id, Model modeloVeterinario) {
		LOG_VETERINARIA.info("Vista de mostrar datos de mascota");
		modeloVeterinario.addAttribute("mascota",mascotas.buscarIdMascota(id));
		return datosMascota;
	}
}
