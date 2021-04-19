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


@Entity
@Table(name="mascotas")
public class Mascotas {	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;	
	
	@Column(name="nombre",nullable=false,length=30)
	private String nombre;
	
	@Column(name="tipo",nullable=false,length=30)
	private String tipo;
	
	@Column(name="raza",nullable=false,length=30)
	private String raza;
	
	@Column(name="fechaNacimiento",nullable=false)
	private Date fechaNacimiento;
	
	@Column(name="foto",nullable=false,length=100)
	private String foto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuario")
	private Usuarios idUsuario;
	
	
	public Mascotas() {
		
	}	
	
	public Mascotas(int id, String nombre, String tipo, String raza, Date fechaNacimiento, String foto,
			Usuarios idUsuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.raza = raza;
		this.fechaNacimiento = fechaNacimiento;
		this.foto = foto;
		this.idUsuario = idUsuario;
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

	public Usuarios getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuarios idUsuario) {
		this.idUsuario = idUsuario;
	}
}
