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
	public ResponseEntity<?> mostrarClienteActual(Map<String, Object> clienteJSON) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetails usuario = (UserDetails) auth.getPrincipal();
		
		Usuarios cliente = new Usuarios();
		cliente = this.usuariosRepository.findByUsername(usuario.getUsername());
		
		clienteJSON.put("id",cliente.getId());
		clienteJSON.put("nombre",cliente.getNombre());
		clienteJSON.put("apellidos",cliente.getApellidos());
		clienteJSON.put("telefono",cliente.getTelefono());
		clienteJSON.put("username",cliente.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body(clienteJSON);
	}
	
	
	/*--------------Después de pulsar el botón desde Ionic----------------------*/
	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@GetMapping("/cliente/citas")
	public ResponseEntity<?> mostrarHistorialCliente(Mascotas mascota, Citas cita, Map<String, Object> clienteJSON) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		boolean realizada = cita.isRealizada();
		realizada = true;
		
		txtFechaActual = anio+"-"+(mes+1)+"-"+dia;
	    Date fechaCita = Date.valueOf(txtFechaActual);// convertimos a fecha para la base de datos
		
	    List<Citas> modeloCita = citas.findCitasByMascotaCliente(mascota.getNombre(),fechaCita,realizada);
	    
	    if(modeloCita == null) {
	    	txtCitasEmpty = "Citas no encontradas para "+auth.getName();
	    	LOG_VETERINARIA.info(txtCitasEmpty);
	    	
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(txtCitasEmpty);
	    }
	    else {
	    	txtHistorialCitas = "Historial de citas de "+auth.getName()+" mostrado correctamente";
	    	LOG_VETERINARIA.info(txtHistorialCitas);
	    	
	    	UserDetails usuario = (UserDetails) auth.getPrincipal();
			
			Usuarios cliente = new Usuarios();
			cliente = this.usuariosRepository.findByUsername(usuario.getUsername());
			
			clienteJSON.put("id",cliente.getId());
			clienteJSON.put("nombre",cliente.getNombre());
			clienteJSON.put("apellidos",cliente.getApellidos());
			clienteJSON.put("telefono",cliente.getTelefono());
			clienteJSON.put("username",cliente.getUsername());
	    	
	    	return ResponseEntity.ok(txtHistorialCitas+"\n\n"+clienteJSON);
	    }
	}
}
