package com.veterinaria.controladores;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.servicios.UsuariosService;
import com.veterinaria.servicios.Impl.ClientesImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Controller
@RequestMapping("/")
public class ClientesController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(ClientesController.class);
	private static final String menu = "menu", formulario = "registrarCliente", vista_perfil = "perfil_cliente",
			vista_clientes = "clientes/listadoClientes", formCliente = "clientes/formCliente", datosCliente = "clientes/mostrarCliente";
	private String txtCliente;
	
	
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuariosService;
	
	@Autowired
	@Qualifier("clientesImpl")
	private ClientesImpl clientes;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuarios;// para mostrar todos los clientes
	
	
	@GetMapping("/registrarCliente")
	public ModelAndView verFormularioRegistar() {
		LOG_VETERINARIA.info("Formulario de registrar cliente");
		ModelAndView mavCliente = new ModelAndView(formulario);
		mavCliente.addObject("usuarios",new Usuarios());
		return mavCliente;
	}

	@PostMapping("/auth/registrarCliente")
	public String registrarCliente(@Valid @ModelAttribute("usuarios") Usuarios usuario,
			BindingResult validacionCliente, RedirectAttributes mensajeFlash) {
		String error = "Error al registar nuevo cliente", registrado = usuario.getUsername()+" registrado correctamente";
		
		if(validacionCliente.hasErrors()) {
			LOG_VETERINARIA.info(error);
			mensajeFlash.addFlashAttribute("errorCliente",error);
			return formulario;
		}
		else {
			usuariosService.registrarUsuario(usuario);
			LOG_VETERINARIA.info(registrado);
			mensajeFlash.addFlashAttribute("registrar",registrado);
			return "redirect:/"+menu;
		}
	}
	
	/*-------------Métodos visualizados para un solo cliente-------------------------*/
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@RequestMapping(value="perfil_cliente/{id}",method=RequestMethod.GET)
	public ModelAndView verPerfilCliente(@ModelAttribute("usuario") ModeloUsuarios cliente, @PathVariable("id") Integer id) {		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usuario.getUsername();
		
		ModelAndView mavCliente = new ModelAndView(vista_perfil);
		mavCliente.addObject("cliente",username.toUpperCase());
		mavCliente.addObject("usuario",usuarios.buscarId(id));
		return mavCliente;
	}
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("editarPerfil")
	public String editarPerfilCliente(@Valid @ModelAttribute("usuario") ModeloUsuarios cliente,
			BindingResult validacionPerfil, RedirectAttributes mensajeFlash) {
		String error = "Error al registar nuevo cliente";
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String nombreUsuario = usuario.getUsername();
		
		if(validacionPerfil.hasErrors()) {
			LOG_VETERINARIA.info(error);
			mensajeFlash.addFlashAttribute("errorPerfil",error);
			return vista_perfil;
		}
		else {
			usuarios.editarPerfil(cliente);
			
			String perfil = "Perfil de "+nombreUsuario+" editado correctamente";
			LOG_VETERINARIA.info(perfil);
			mensajeFlash.addFlashAttribute("editado",perfil);

			return "redirect:/"+menu;
		}
	}
	
	/*---------------------Métodos para los clientes--------------------------------*/
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("clientes/listadoClientes")
	public ModelAndView verListadoClientes(@ModelAttribute("usuario") ModeloUsuarios modeloCliente, @RequestParam Map<String,Object> paginas) {
		LOG_VETERINARIA.info("Vista de listado de clientes");
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ModelAndView mavUsuarios = new ModelAndView(vista_clientes);
		mavUsuarios.addObject("clientesTxt","Clientes no encontrados en la base de datos");
		mavUsuarios.addObject("clientes",usuarios.listarUsuarios());
		mavUsuarios.addObject("nombre",usuario.getUsername().toUpperCase());
		
		/* Realizamos paginación para los usuarios clientes */
		int numPaginas = paginas.get("pagina") != null ? (Integer.valueOf(paginas.get("pagina").toString()) - 1) : 0;
		
		PageRequest res = PageRequest.of(numPaginas,9);
		Page<Usuarios> paginasCliente = usuarios.paginacionUsuarios(res);
		
		int totalClientes = paginasCliente.getTotalPages();// nº total de clientes
		
		if(totalClientes > 0) {	
			/* Para empezar desde la 1ª paginación hasta el final de las páginas */
			List<Integer> listadoClientes = IntStream.rangeClosed(1,totalClientes).boxed().collect(Collectors.toList());
			mavUsuarios.addObject("paginas",listadoClientes);
		}
		
		mavUsuarios.addObject("clientes",paginasCliente.getContent());
		
		mavUsuarios.addObject("anterior",numPaginas);
		mavUsuarios.addObject("actual",numPaginas + 1);
		mavUsuarios.addObject("siguiente",numPaginas + 2);
		mavUsuarios.addObject("ultima",totalClientes);
		return mavUsuarios;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("actived/{id}")
	public String gestionarCliente(@ModelAttribute("usuario") ModeloUsuarios usuario, @PathVariable("id") int id, @RequestParam(name="username",required=false) String username,
			@RequestParam(name="activado",required=false) boolean activado, BindingResult clienteValido, RedirectAttributes mensajeFlash) {
		
		if(activado == false) {
			txtCliente = "Cliente "+username+" activado correctamente";
			LOG_VETERINARIA.info(txtCliente);
			mensajeFlash.addFlashAttribute("activado",txtCliente);
		}
		else {
			txtCliente = "Cliente "+username+" desactivado correctamente";
			LOG_VETERINARIA.info(txtCliente);
			mensajeFlash.addFlashAttribute("desactivado",txtCliente);
		}
		
		usuarios.enabledCliente(id, usuario);
		return vista_clientes;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping({"clientes/formCliente","clientes/formCliente/{id}"})
	public String formularioCliente(@ModelAttribute("usuario") ModeloUsuarios cliente, @PathVariable(name="id",required=false) Integer id,
			Model modeloCliente) {
		LOG_VETERINARIA.info("Formulario de cliente");
		
		UserDetails usuario = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modeloCliente.addAttribute("nombre",usuario.getUsername().toUpperCase());
		
		if(id == null)
			modeloCliente.addAttribute("usuario",new ModeloUsuarios());
		else
			modeloCliente.addAttribute("usuario",usuarios.buscarId(id));
		
		return formCliente;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/clientes/saveCliente")
	public String saveCliente(@Valid @ModelAttribute("usuario") ModeloUsuarios cliente, BindingResult clienteValido,
			RedirectAttributes mensajeFlash) {
		
		if(clienteValido.hasErrors())
			return formCliente;
		else {
			if(cliente.getId() == 0) {
				clientes.aniadirCliente(cliente);
				
				txtCliente = "Cliente "+cliente.getUsername()+" añadido correctamente";
				LOG_VETERINARIA.info(txtCliente);
				mensajeFlash.addFlashAttribute("insertado",txtCliente);
			}
			else {
				clientes.editarCliente(cliente);
				
				txtCliente = "Cliente "+cliente.getUsername()+" editado correctamente";
				LOG_VETERINARIA.info(txtCliente);
				mensajeFlash.addFlashAttribute("editado",txtCliente);
			}
			
			return vista_clientes;
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/clientes/eliminarCliente/{id}")
	public String eliminarCliente(@ModelAttribute("usuario") ModeloUsuarios cliente, @PathVariable("id") int id,
			@RequestParam(name="username",required=false) String username, RedirectAttributes mensajeFlash) {	
		
		clientes.eliminarCliente(id);
		
		txtCliente = "Cliente "+username+" eliminado correctamente";
		LOG_VETERINARIA.info(txtCliente);
		mensajeFlash.addFlashAttribute("eliminado",txtCliente);
		return vista_clientes;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("clientes/mostrarCliente/{id}")
	public String mostrarDatosCliente(@PathVariable("id") int id, Model modeloCliente) {
		LOG_VETERINARIA.info("Vista de mostrar datos de cliente");
		modeloCliente.addAttribute("usuario",usuarios.buscarId(id));
		return datosCliente;
	}
}
