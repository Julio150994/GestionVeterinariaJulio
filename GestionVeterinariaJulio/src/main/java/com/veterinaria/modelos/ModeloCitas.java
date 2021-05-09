package com.veterinaria.modelos;

import java.sql.Date;


public class ModeloCitas {
	private int id;
	private ModeloMascotas mascotas;
	private ModeloUsuarios usuarios;
	private Date fecha;
	private String motivo,informe;
	private boolean realizada;

	
	public ModeloCitas() {
		
	}

	public ModeloCitas(int id, ModeloMascotas mascotas, ModeloUsuarios usuarios, Date fecha, String motivo,
			String informe, boolean realizada) {
		super();
		this.id = id;
		this.mascotas = mascotas;
		this.usuarios = usuarios;
		this.fecha = fecha;
		this.motivo = motivo;
		this.informe = informe;
		this.realizada = realizada;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

	public ModeloMascotas getMascotas() {
		return mascotas;
	}

	public void setMascotas(ModeloMascotas mascotas) {
		this.mascotas = mascotas;
	}

	public ModeloUsuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ModeloUsuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
}
