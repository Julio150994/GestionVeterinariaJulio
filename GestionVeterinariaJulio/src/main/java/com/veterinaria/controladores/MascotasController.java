package com.veterinaria.controladores;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.repositorios.MascotasRepository;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.ClientesImpl;
import com.veterinaria.servicios.Impl.MascotasImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import com.veterinaria.storage.StorageService;


@Controller
@RequestMapping("/")
public class MascotasController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(MascotasController.class);
	private static final String vista_mascotas = "/mascotas/listadoMascotas",
			formMascota = "/mascotas/formMascota", datosMascota = "/mascotas/mostrarMascota";
	private String txtMascota;
	
	@Autowired
	StorageService storage;
	
	@Autowired
	@Qualifier("mascotasImpl")
	private MascotasImpl mascotas;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuarioCliente;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	@Qualifier("mascotasRepository")
	private MascotasRepository mascotasRepository;
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/mascotas/listadoMascotas")
	public ModelAndView listarMascotas(Mascotas modeloMascota, @ModelAttribute("usuario") Usuarios modeloUsuario) {
		LOG_VETERINARIA.info("Vista de listado de mascotas");
		UserDetails usuarioClienteActual = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavMascotas = new ModelAndView(vista_mascotas);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios usuario = usuariosRepository.findByUsername(auth.getName());
			mavMascotas.addObject("usuario",usuario.getId());
		
			mavMascotas.addObject("clienteActual",usuarioClienteActual.getUsername().toUpperCase());
			
			mavMascotas.addObject("mascotasTxt",usuarioClienteActual.getUsername()+" no tiene mascotas registradas en la base de datos");
			
			mavMascotas.addObject("mascotas",mascotasRepository.findByIdUsuario(usuario));
		}
		
		return mavMascotas;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping({"/mascotas/formMascota","/mascotas/formMascota/{id}"})
	public String formularioMascota(@PathVariable(name="id",required=false) Integer id, Model modeloMascota,
			@ModelAttribute("mascota") Mascotas mascota) {
		
		LOG_VETERINARIA.info("Formulario de mascota");
		
		/* Para encontrar el id del cliente actual */
		Authentication authCliente = SecurityContextHolder.getContext().getAuthentication();
						
		if(authCliente.getPrincipal() != "anonymousUser") {
			Usuarios cliente = usuariosRepository.findByUsername(authCliente.getName());
			
			modeloMascota.addAttribute("clienteActual",cliente.getUsername().toUpperCase());
			
			modeloMascota.addAttribute("usuario",cliente.getId());
			
			if(id == null)
				modeloMascota.addAttribute("mascota",new Mascotas());
			else
				modeloMascota.addAttribute("mascota",mascotas.buscarIdMascota(id));
		}
		
		return formMascota;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/mascotas/saveMascota")
	public String saveMascota(@ModelAttribute("mascota") Mascotas modeloMascota, BindingResult validaMascota,
			RedirectAttributes mensajeFlash, Model cliente, @RequestParam(name="foto",required=false) MultipartFile foto,
			@RequestParam(name="id",required=false) Integer id) {
		
		if(modeloMascota.getId() == null) {
			modeloMascota = mascotas.aniadirMascota(modeloMascota);
			
			if(!foto.isEmpty()) {
				String fotoMascota = storage.store(foto);
				modeloMascota.setFoto(MvcUriComponentsBuilder.fromMethodName(FotoMascotaController.class,"servidorMascota", fotoMascota).
						build().toUriString());
				
				mascotas.editarMascota(modeloMascota);
			}
			
			txtMascota = "Mascota "+modeloMascota.getNombre()+" añadida correctamente";
			LOG_VETERINARIA.info(txtMascota);
			mensajeFlash.addFlashAttribute("insertado",txtMascota);
		}
		else {
			
			if(!foto.isEmpty()) {
				Mascotas mascota = mascotasRepository.findById(id).orElse(null);
				
				if(mascota.getFoto() != null)
					storage.delete(mascota.getFoto());
				
				String fotoMascota = storage.store(foto);
				modeloMascota.setFoto(MvcUriComponentsBuilder.fromMethodName(FotoMascotaController.class,"servidorMascota", fotoMascota).
						build().toUriString());
			}
			else {
				Mascotas mascotaAnterior = mascotas.buscarIdMascota(modeloMascota.getId());
				modeloMascota.setFoto(mascotaAnterior.getFoto());
			}
			
			mascotas.editarMascota(modeloMascota);
			
			txtMascota = "Mascota "+modeloMascota.getNombre()+" editada correctamente";
			LOG_VETERINARIA.info(txtMascota);
			mensajeFlash.addFlashAttribute("editado",txtMascota);
		}
		
		return "redirect:"+vista_mascotas;
	}	
	
	/* Eliminamos mascota con el uso de formulario modal */
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/mascotas/eliminarMascota/{id}")
	public String eliminarMascota(@ModelAttribute("mascota") Mascotas modeloMascota, @PathVariable("id") Integer id,
			RedirectAttributes mensajeFlash) {
		
		mascotas.eliminarMascota(id);
		
		return "redirect:"+vista_mascotas;
	}
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/mascotas/mostrarMascota/{id}")
	public String mostrarDatosMascota(@PathVariable Integer id, Model modeloMascota) {
		LOG_VETERINARIA.info("Vista de mostrar datos de mascota");		
		
		modeloMascota.addAttribute("mascota",mascotas.buscarIdMascota(id));
		return datosMascota;
	}
}
