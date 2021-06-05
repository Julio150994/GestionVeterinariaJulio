package com.veterinaria.controladores;

import java.util.Calendar;
import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lowagie.text.DocumentException;
import com.veterinaria.configuraciones.ExportarDatosCliente;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.modelos.ModeloMascotas;
import com.veterinaria.modelos.ModeloUsuarios;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.ClientesImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@CrossOrigin(origins="http://localhost:8080", methods={RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/apiVeterinaria")
public class ClientesRESTController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(ClientesRESTController.class);
	private String txtFechaActual, fechaFormatoNormal, usuarioEmpty, datosUsuario, txtLogin;
	
	private Calendar fecha = new GregorianCalendar();
	private int dia = fecha.get(Calendar.DAY_OF_MONTH);
    private int mes = fecha.get(Calendar.MONTH);
    private int anio = fecha.get(Calendar.YEAR);
	
    
	@Autowired
	private AuthenticationManager authCliente;
	
	@Autowired
	@Qualifier("clientesImpl")
	private ClientesImpl clientes;
	
	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuarios;
	
	@Autowired
	@Qualifier("usuariosImpl")
	private UsuariosImpl usuariosImpl;// para mostrar todos los clientes
	
	@Autowired
	@Qualifier("citasRepository")
	private CitasRepository citas;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginWithCiente(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		Usuarios usuario = new Usuarios();
		usuario.setUsername(username);
		usuario.setPassword(password);
		
		if((username == null && password == null) || (username.isEmpty() && password.isEmpty())) {
			usuarioEmpty = "Debe introducir datos de usuario cliente para iniciar sesión";
			
			LOG_VETERINARIA.info(usuarioEmpty);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarioEmpty);
		}
		else if((username == null || password == null) || (username.isEmpty() || password.isEmpty())) {
			datosUsuario = "Faltan datos por introducir para este usuario";
			
			LOG_VETERINARIA.info(datosUsuario);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(datosUsuario);
		}
		else {
			Authentication autenticacion = authCliente.authenticate(new UsernamePasswordAuthenticationToken(username,password));
			
			@SuppressWarnings("unchecked")
			List<GrantedAuthority> listaRoles = (List<GrantedAuthority>) autenticacion.getAuthorities();
			
			String rol = listaRoles.get(0).toString();
			
			if(rol.equals("ROLE_ADMIN"))
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario "+username+" no debe ser administrador");
			else if(rol.equals("ROLE_VETERINARIO"))
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario "+username+" no debe ser veterinario");
			else {
				SecurityContextHolder.getContext().setAuthentication(autenticacion);
				
				String clienteToken = generarToken(username);
				usuario.setToken(clienteToken);
			
				txtLogin = "Cliente "+username+" logueado éxitosamente";
				LOG_VETERINARIA.info(txtLogin);
			
				return ResponseEntity.ok(txtLogin+"\n"+clienteToken);
			}
		}
	}

	private String generarToken(String nombreUsuario) {
		String passwordCliente = "secret";
		List<GrantedAuthority> grantedAuths = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_CLIENTE");
		
		String clienteToken = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(nombreUsuario)
				.claim("authorities",grantedAuths.stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+600000))
				.signWith(SignatureAlgorithm.HS512, passwordCliente.getBytes()).compact();
		
		return "Bearer "+clienteToken;
	}
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/cliente")
	public Usuarios mostrarClienteActual() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		LOG_VETERINARIA.info("Datos de cliente "+auth.getName());
		
		Usuarios cliente = usuariosRepository.findByUsername(auth.getName());
		
		LOG_VETERINARIA.info("Datos de cliente: "+cliente);
		
		return null;
	}
	
	
	/*--------------Después de pulsar el botón desde Ionic----------------------*/
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/cliente/citas")
	public ResponseEntity<?> mostrarHistorialCliente(@PathVariable("id") Integer id, HttpServletResponse resCliente, Model modelo,
			@Valid @ModelAttribute("mascota") ModeloMascotas mascota, ModeloCitas cita, RedirectAttributes mensajeFlash) throws DocumentException, IOException {
		LOG_VETERINARIA.info("Historial de citas del cliente mostrado");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuarios c = usuariosRepository.findByUsername(auth.getName());
		if(auth.getPrincipal() != "anonymousUser") {
			Usuarios cliente = usuariosRepository.findByUsername(auth.getName());
			
			boolean realizada = cita.isRealizada();
			realizada = true;
			
			txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
			fechaFormatoNormal = dia+"/"+(mes+1)+"/"+anio;
		    Date fechaCita = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
			
			List<Citas> modeloCita = citas.findCitasByMascotaCliente(mascota.getNombre(),fechaCita,realizada);
			LOG_VETERINARIA.info(mascota.getNombre()+" seleccionado");
			
			if(modeloCita.isEmpty()) {
				String mensajeError = cliente.getUsername()+" debe tener citas realizadas con fecha anterior o igual a "+fechaFormatoNormal;
				LOG_VETERINARIA.info(mensajeError);
				
				mensajeFlash.addFlashAttribute("txtFechaPosterior",mensajeError);
			    mensajeFlash.addFlashAttribute("citasFechaPosterior",citas.findCitasRealizadasByFechaPosterior(mascota.getNombre(),fechaCita, realizada));
			    return null;
			}
			else {
				LOG_VETERINARIA.info("Informe descargado correctamente");
				
				resCliente.setContentType("application/pdf");
				
				String clientesPDF = "attachment; filename=informe_cliente_"+cliente.getUsername()+".pdf";
				
				resCliente.setHeader("Content-Disposition",clientesPDF);
			
				ModeloUsuarios modeloCliente = usuariosImpl.buscarId(cliente.getId());
				modelo.addAttribute("usuario",modeloCliente);
				
				ExportarDatosCliente pdfCliente = new ExportarDatosCliente(modeloCliente,modeloCita);
				pdfCliente.exportarDatosCliente(resCliente);
				return null;
			}
		}
		return ResponseEntity.ok("/citas/datosCliente/pdf/"+c.getId());
	}
}
