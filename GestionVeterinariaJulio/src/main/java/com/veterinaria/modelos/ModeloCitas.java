package com.veterinaria.modelos;

import java.sql.Date;


public class ModeloCitas {
	private int id;
	private ModeloMascotas idMascota;
	private ModeloUsuarios idUsuario;
	private Date fecha;
	private String motivo,informe;
	private boolean realizada;

	
	public ModeloCitas() {
		
	}	

	public ModeloCitas(int id, ModeloMascotas idMascota, ModeloUsuarios idUsuario, Date fecha, String motivo,
			String informe, boolean realizada) {
		super();
		this.id = id;
		this.idMascota = idMascota;
		this.idUsuario = idUsuario;
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

	public ModeloMascotas getIdMascota() {
		return idMascota;
	}

	public void setIdMascota(ModeloMascotas idMascota) {
		this.idMascota = idMascota;
	}

	public ModeloUsuarios getVeterinarios() {
		return idUsuario;
	}

	public void setVeterinarios(ModeloUsuarios idUsuario) {
		this.idUsuario = idUsuario;
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
