package com.veterinaria.modelos;

import java.util.ArrayList;
import java.util.List;


public class ModeloUsuarios {
	private int id;
	private String nombre,apellidos,telefono,username,password;
	private boolean activado;
	private String rol;
	private List<ModeloMascotas> mascotas = new ArrayList<ModeloMascotas>();
	private List<ModeloCitas> citas = new ArrayList<ModeloCitas>();
	
	
	public ModeloUsuarios() {
		
	}

	public ModeloUsuarios(int id, String nombre, String apellidos, String telefono, String username, String password,
			boolean activado, String rol, List<ModeloMascotas> mascotas, List<ModeloCitas> citas) {
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

	public List<ModeloMascotas> getMascotas() {
		return mascotas;
	}

	public void setMascotas(List<ModeloMascotas> mascotas) {
		this.mascotas = mascotas;
	}

	public List<ModeloCitas> getCitas() {
		return citas;
	}

	public void setCitas(List<ModeloCitas> citas) {
		this.citas = citas;
	}
}
