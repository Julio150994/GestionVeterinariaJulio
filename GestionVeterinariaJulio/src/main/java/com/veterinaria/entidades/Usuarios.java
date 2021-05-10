package com.veterinaria.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name="usuarios")
public class Usuarios {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message="Debe introducir un nombre simple o compuesto")
	@Pattern(regexp="^\\D{1,30}$",message="El nombre simple o compuesto no puede contener números, también debe empezar en mayúscula y seguir todo en minúsculas")
	@Size(min=1,max=30,message="El nombre debe tener entre 1 y 30 caracteres de longitud máxima")
	@Column(name="nombre",nullable=false,length=30)
	private String nombre;
	
	@NotEmpty(message="Debe introducir dos apellidos")
	@Pattern(regexp="^\\D{1,50}$",message="Debe ser dos apellidos que no pueden contener números, también debe empezar en mayúscula y seguir todo en minúsculas")
	@Size(min=1,max=50,message="Entre los dos apellidos debe haber entre 1 y 50 caracteres de longitud máxima")
	@Column(name="apellidos",nullable=false,length=50)
	private String apellidos;
	
	@NotEmpty(message="Debe introducir un número de teléfono")
	@Pattern(regexp="^\\d{9,10}$",message="El teléfono no debe contener letras, espacios en blanco y debe tener entre 9 y 10 caracteres")
	@Min(value=9,message="No debe tener menos de 9 caracteres")
	@Column(name="telefono",unique=true,nullable=false,length=10)
	private String telefono;
	
	@NotEmpty(message="Debe introducir un nombre de usuario")
	@Pattern(regexp="^[a-zA-Z]{1}[a-zA-Z0-9]{0,29}$",message="El nombre de usuario no debe contener espacios en blanco y 30 caracteres de longitud máxima")
	@Column(name="username",unique=true,nullable=false,length=30)
	private String username;
	
	@NotEmpty(message="Debe introducir una contraseña de usuario")
	@Column(name="password",nullable=false,length=100)
	private String password;
	
	@Column(name="activado",nullable=false)
	private boolean activado;	
	
	@Column(name="rol",nullable=false,length=20)
	private String rol;
	
	@OneToMany(mappedBy="usuarios")
	private List<Mascotas> mascotas = new ArrayList<Mascotas>();
	
	@OneToMany(mappedBy="usuarios")
	private List<Citas> citas = new ArrayList<Citas>();
	
	
	public Usuarios() {
		
	}	

	public Usuarios(int id, @NotEmpty(message = "Debe introducir un nombre simple o compuesto") @Pattern(regexp = "^\\D{1,30}$", message = "El nombre simple o compuesto no puede contener números, también debe empezar en mayúscula y seguir todo en minúsculas") @Size(min = 1, max = 30, message = "El nombre debe tener entre 1 y 30 caracteres de longitud máxima") String nombre,
			@NotEmpty(message = "Debe introducir dos apellidos") @Pattern(regexp = "^\\D{1,50}$", message = "Debe ser dos apellidos que no pueden contener números, también debe empezar en mayúscula y seguir todo en minúsculas") @Size(min = 1, max = 50, message = "Entre los dos apellidos debe haber entre 1 y 50 caracteres de longitud máxima") String apellidos,
			@NotEmpty(message = "Debe introducir un número de teléfono") @Pattern(regexp = "^\\d{9,10}$", message = "El teléfono no debe contener letras, espacios en blanco y debe tener entre 9 y 10 caracteres") @Min(value = 9, message = "No debe tener menos de 9 caracteres") String telefono,
			@NotEmpty(message = "Debe introducir un nombre de usuario") @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9]{0,29}$", message = "El nombre de usuario no debe contener espacios en blanco y 30 caracteres de longitud máxima") String username,
			@NotEmpty(message = "Debe introducir una contraseña de usuario") String password, boolean activado,
			String rol, List<Mascotas> mascotas, List<Citas> citas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.username = username;
		this.password = password;
		this.activado = activado;
		this.rol = rol;
		this.mascotas = mascotas;
		this.citas = citas;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isActivado() {
		return activado;
	}


	public void setActivado(boolean activado) {
		this.activado = activado;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}


	public List<Mascotas> getMascotas() {
		return mascotas;
	}


	public void setMascotas(List<Mascotas> mascotas) {
		this.mascotas = mascotas;
	}
	

	public List<Citas> getCitas() {
		return citas;
	}


	public void setCitas(List<Citas> citas) {
		this.citas = citas;
	}
}
