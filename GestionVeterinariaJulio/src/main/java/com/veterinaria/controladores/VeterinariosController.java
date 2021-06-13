package com.veterinaria.controladores;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import com.veterinaria.servicios.Impl.VeterinariosImpl;


@Controller
@RequestMapping("/veterinarios")
public class VeterinariosController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(VeterinariosController.class);
	private static final String vista_veterinarios = "veterinarios/listadoVeterinarios", formVeterinario = "veterinarios/formVeterinario",
			datosVeterinario = "veterinarios/mostrarVeterinario";
	private String txtVeterinario;
	
	@Autowired
	@Qualifier("veterinariosImpl")
	private VeterinariosImpl veterinarios;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuarios;// para mostrar todos los veterinarios
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("listadoVeterinarios")
	public ModelAndView verListadoVeterinarios(@ModelAttribute("usuario") ModeloUsuarios veterinario, @RequestParam Map<String,Object> paginas) {
		LOG_VETERINARIA.info("Vista de listado de veterinarios");
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavUsuarios = new ModelAndView(vista_veterinarios);
		mavUsuarios.addObject("veterinariosTxt","Veterinarios no encontrados en la base de datos");
		mavUsuarios.addObject("veterinarios",usuarios.listarUsuarios());
		mavUsuarios.addObject("nombre",usuario.getUsername().toUpperCase());
		
		/* Realizamos paginación para los usuarios veterinarios */
		int numPaginas = paginas.get("pagina") != null ? (Integer.valueOf(paginas.get("pagina").toString()) - 1) : 0;
		
		PageRequest res = PageRequest.of(numPaginas,9);
		Page<Usuarios> paginasVeterinario = usuarios.paginacionUsuarios(res);
		
		int totalVeterinarios = paginasVeterinario.getTotalPages();// nº total de veterinarios
		
		if(totalVeterinarios > 0) {	
			/* Para empezar desde la 1ª paginación hasta el final de las páginas */
			List<Integer> listadoVeterinarios = IntStream.rangeClosed(1,totalVeterinarios).boxed().collect(Collectors.toList());
			mavUsuarios.addObject("paginas",listadoVeterinarios);
		}
		
		mavUsuarios.addObject("veterinarios",paginasVeterinario.getContent());
		
		mavUsuarios.addObject("anterior",numPaginas);
		mavUsuarios.addObject("actual",numPaginas + 1);
		mavUsuarios.addObject("siguiente",numPaginas + 2);
		mavUsuarios.addObject("ultima",totalVeterinarios);
		return mavUsuarios;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping({"formVeterinario","formVeterinario/{id}"})
	public String formularioVeterinario(@PathVariable(name="id",required=false) Integer id,
			Model modeloVeterinario) {
		LOG_VETERINARIA.info("Formulario de veterinario");
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modeloVeterinario.addAttribute("nombre",usuario.getUsername().toUpperCase());
		
		if(id == null)
			modeloVeterinario.addAttribute("usuario",new ModeloUsuarios());
		else
			modeloVeterinario.addAttribute("usuario",usuarios.buscarId(id));
		return formVeterinario;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("saveVeterinario")
	public String saveVeterinario(@Valid @ModelAttribute("usuario") ModeloUsuarios veterinario, BindingResult veterinarioValidado,
			RedirectAttributes mensajeFlash) {
		
		if(veterinarioValidado.hasErrors())
			return "redirect:"+formVeterinario;
		else {
			if(veterinario.getId() == 0) {
				veterinarios.aniadirVeterinario(veterinario);
				
				txtVeterinario = "Veterinario "+veterinario.getUsername()+" añadido correctamente";
				LOG_VETERINARIA.info(txtVeterinario);
				mensajeFlash.addFlashAttribute("insertado",txtVeterinario);
			}
			else {
				veterinarios.editarVeterinario(veterinario);
				
				txtVeterinario = "Veterinario "+veterinario.getUsername()+" editado correctamente";
				LOG_VETERINARIA.info(txtVeterinario);
				mensajeFlash.addFlashAttribute("editado",txtVeterinario);
			}
			return "redirect:"+vista_veterinarios;
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("eliminarVeterinario/{id}")
	public String eliminarVeterinario(@ModelAttribute("usuario") ModeloUsuarios veterinario, @PathVariable("id") Integer id,
			@RequestParam(name="username",required=false) String username, RedirectAttributes mensajeFlash) {
		
		veterinarios.eliminarVeterinario(id);
		
		txtVeterinario = "Veterinario "+username+" eliminado correctamente";
		LOG_VETERINARIA.info(txtVeterinario);
		mensajeFlash.addFlashAttribute("eliminado",txtVeterinario);
		return "redirect:"+vista_veterinarios;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("mostrarVeterinario/{id}")
	public String mostrarDatosVeterinario(@PathVariable Integer id, Model modeloVeterinario) {
		LOG_VETERINARIA.info("Vista de mostrar datos de cliente");
		modeloVeterinario.addAttribute("usuario",usuarios.buscarId(id));
		return datosVeterinario;
	}
}
