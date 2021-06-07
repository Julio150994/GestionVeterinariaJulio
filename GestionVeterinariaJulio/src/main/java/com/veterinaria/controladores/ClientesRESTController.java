package com.veterinaria.controladores;

import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.veterinaria.entidades.Citas;
import com.veterinaria.entidades.Mascotas;
import com.veterinaria.entidades.Usuarios;
import com.veterinaria.modelos.ModeloCitas;
import com.veterinaria.repositorios.CitasRepository;
import com.veterinaria.repositorios.UsuariosRepository;
import com.veterinaria.servicios.Impl.CitasImpl;
import com.veterinaria.servicios.Impl.ClientesImpl;
import com.veterinaria.servicios.Impl.UsuariosImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@CrossOrigin(origins="http://localhost:8100", methods={RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/apiVeterinaria")
public class ClientesRESTController {
	private static final Log LOG_VETERINARIA = LogFactory.getLog(ClientesRESTController.class);
	private String txtFechaActual, usuarioEmpty, datosUsuario, txtLogin, txtCitasEmpty, txtHistorialCitas;
	
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
	@Qualifier("citasImpl")
	private CitasImpl citasImpl;
	
	@Autowired
	@Qualifier("citasRepository")
	private CitasRepository citasRepository;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginWithCiente(@RequestParam(name="username",required=false) String username, @RequestParam(name="password",required=false) String password,
			Map<String, Object> clienteJSON) {
		
		Usuarios usuario = new Usuarios();
		
		if((username == null && password == null) || (username.isEmpty() && password.isEmpty())) {
			usuarioEmpty = "Debe introducir datos de usuario cliente para iniciar sesión";
			
			LOG_VETERINARIA.info(usuarioEmpty);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usuarioEmpty);
		}
		else if((username == null || password == null) || (username.isEmpty() || password.isEmpty())) {
			datosUsuario = "Faltan datos por introducir";
			
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
				
				UserDetails cliente = (UserDetails) autenticacion.getPrincipal();
				
				usuario = this.usuariosRepository.findByUsername(cliente.getUsername());
				
				SecurityContextHolder.getContext().setAuthentication(autenticacion);
				
				String clienteToken = generarToken(username);
			
				txtLogin = "Cliente "+username+" logueado éxitosamente";
				LOG_VETERINARIA.info(txtLogin);
				
				clienteJSON.put("id",usuario.getId());
				clienteJSON.put("username",usuario.getUsername());
				clienteJSON.put("password",usuario.getPassword());
				clienteJSON.put("activado",usuario.isActivado());
				clienteJSON.put("rol",usuario.getRol());
				clienteJSON.put("token",clienteToken);
				return ResponseEntity.status(HttpStatus.OK).body(clienteJSON);
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
	@GetMapping("/cliente/listarCitas")
	public ResponseEntity<?> listarCitas(Map<String, Object> citasJSON) {
		List<ModeloCitas> citas = citasImpl.mostrarCitas();
		 
		
		if(citas.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citas no encontradas en la base de datos");
		else {
			for(ModeloCitas cita: citas) {
				citasJSON.put("id",cita.getId());
		    	citasJSON.put("dia de cita",cita.getFecha());
		    	citasJSON.put("nombre veterinario",cita.getUsuario().getNombre());
		    	citasJSON.put("apellidos veterinario",cita.getUsuario().getApellidos());
		    	citasJSON.put("telefono veterinario",cita.getUsuario().getTelefono());
		    	citasJSON.put("nombre mascota",cita.getMascota().getNombre());
		    	citasJSON.put("tipo mascota",cita.getMascota().getTipo());
		    	citasJSON.put("raza mascota",cita.getMascota().getRaza());
		    	citasJSON.put("fecha de nacimiento mascota",Date.valueOf(cita.getMascota().getFechaNacimiento().toString()));
		    	citasJSON.put("foto mascota",cita.getMascota().getFoto());
		    	citasJSON.put("informe de cita",cita.getInforme());
		    	return ResponseEntity.status(HttpStatus.OK).body(citasJSON);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(citasJSON);
	}
	
	
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/cliente/citas")
	public ResponseEntity<?> mostrarHistorialCliente(Citas cita, Mascotas mascota, Map<String, Object> historialCitasJSON) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetails usuario = (UserDetails) auth.getPrincipal();
		
		Usuarios cliente = new Usuarios();
		cliente = this.usuariosRepository.findByUsername(usuario.getUsername());
		
		boolean realizada = cita.isRealizada();
		realizada = true;
		
		txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
	    Date fechaCita = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
		
	    List<Citas> citasCliente = citasRepository.listarHistorialCitasByCliente(cliente.getId(), fechaCita, realizada);
	    
	    if(citasCliente == null) {
	    	txtCitasEmpty = "Citas no encontradas para "+auth.getName();
	    	LOG_VETERINARIA.info(txtCitasEmpty);
	    	
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(txtCitasEmpty);
	    }
	    else {
	    	SecurityContextHolder.getContext().setAuthentication(auth);
	    	
	    	txtHistorialCitas = "Historial de citas de "+auth.getName()+" mostrado correctamente";
	    	LOG_VETERINARIA.info(txtHistorialCitas);
	    	
	    	for(Citas citaRealizada: citasCliente) {
	    		historialCitasJSON.put("id",citaRealizada.getId());
	    		historialCitasJSON.put("dia de cita",citaRealizada.getFecha());
		    	historialCitasJSON.put("nombre veterinario",citaRealizada.getUsuario().getNombre());
		    	historialCitasJSON.put("apellidos veterinario",citaRealizada.getUsuario().getApellidos());
		    	historialCitasJSON.put("telefono veterinario",citaRealizada.getUsuario().getTelefono());
		    	historialCitasJSON.put("nombre mascota",citaRealizada.getMascota().getNombre());
		    	historialCitasJSON.put("tipo mascota",citaRealizada.getMascota().getTipo());
		    	historialCitasJSON.put("raza mascota",citaRealizada.getMascota().getRaza());
		    	historialCitasJSON.put("fecha de nacimiento mascota",citaRealizada.getMascota().getFechaNacimiento().toString());
		    	historialCitasJSON.put("foto mascota",citaRealizada.getMascota().getFoto());
		    	historialCitasJSON.put("informe de cita",citaRealizada.getInforme());
		    	return ResponseEntity.status(HttpStatus.OK).body(historialCitasJSON);
	    	}
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(historialCitasJSON);
	    }
	}
}
