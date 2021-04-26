package com.veterinaria.entidades;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="mascotas")
public class Mascotas {	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;	
	
	@NotEmpty(message="Debe introducir un nombre para la mascota")
	@Size(min=1,max=30,message="El nombre de la mascota no debe tener más de 30 caracteres")
	@Pattern(regexp="^[A-Z]{1}[a-z]+{0,29}$",message="El nombre debe empezar por mayúscula")
	@Column(name="nombre",nullable=false,length=30)
	private String nombre;
	
	@NotEmpty(message="Debe introducir un tipo para la mascota")
	@Size(min=1,max=30,message="El tipo de mascota no debe tener más de 30 caracteres")
	@Pattern(regexp="^[a-zA-Z]+{0,29}$",message="El tipo debe empezar por mayúscula")
	@Column(name="tipo",nullable=false,length=30)
	private String tipo;
	
	@NotEmpty(message="Debe introducir una raza para la mascota")
	@Size(min=1,max=30,message="La raza no debe tener más de 30 caracteres")
	@Pattern(regexp="^[A-Z]{1}[a-z]+{0,29}$",message="La raza debe empezar por mayúscula")
	@Column(name="raza",nullable=false,length=30)
	private String raza;
	
	@NotEmpty(message="Debe especificar una fecha de nacimiento para la mascota")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name="fechaNacimiento",nullable=false)
	private Date fechaNacimiento;	
	
	@Column(name="foto",unique=true,nullable=false,length=100)
	private String foto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCliente")
	private Usuarios idCliente;
	
	
	public Mascotas() {
		
	}	
	
	public Mascotas(int id, @NotEmpty(message = "Debe introducir un nombre para la mascota") @Size(min = 1, max = 30, message = "El nombre de la mascota no debe tener más de 30 caracteres") @Pattern(regexp = "^[A-Z]{1}[a-z]+{0,29}$", message = "El nombre debe empezar por mayúscula") String nombre,
			@NotEmpty(message = "Debe introducir un tipo para la mascota") @Size(min = 1, max = 30, message = "El tipo de mascota no debe tener más de 30 caracteres") @Pattern(regexp = "^[a-zA-Z]+{0,29}$", message = "El tipo debe empezar por mayúscula") String tipo,
			@NotEmpty(message = "Debe introducir una raza para la mascota") @Size(min = 1, max = 30, message = "La raza no debe tener más de 30 caracteres") @Pattern(regexp = "^[A-Z]{1}[a-z]+{0,29}$", message = "La raza debe empezar por mayúscula") String raza,
			@NotEmpty(message = "Debe especificar una fecha de nacimiento para la mascota") Date fechaNacimiento, String foto, Usuarios idCliente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.raza = raza;
		this.fechaNacimiento = fechaNacimiento;
		this.foto = foto;
		this.idCliente = idCliente;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Usuarios getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Usuarios idCliente) {
		this.idCliente = idCliente;
	}
}
