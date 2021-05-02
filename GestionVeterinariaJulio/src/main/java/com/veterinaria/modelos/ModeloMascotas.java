package com.veterinaria.modelos;

import java.sql.Date;

public class ModeloMascotas {
	private int id;
	private String nombre,tipo,raza;
	private Date fechaNacimiento;
	private String foto;
	private ModeloUsuarios cliente;
	
	
	public ModeloMascotas() {
		
	}

	public ModeloMascotas(int id, String nombre, String tipo, String raza, Date fechaNacimiento, String foto,
			ModeloUsuarios cliente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.raza = raza;
		this.fechaNacimiento = fechaNacimiento;
		this.foto = foto;
		this.cliente = cliente;
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

	public ModeloUsuarios getCliente() {
		return cliente;
	}

	public void setCliente(ModeloUsuarios cliente) {
		this.cliente = cliente;
	}
}
