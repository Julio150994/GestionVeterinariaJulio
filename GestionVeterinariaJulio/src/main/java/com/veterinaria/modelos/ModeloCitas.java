package com.veterinaria.modelos;

import java.sql.Date;


public class ModeloCitas {
	private Integer id;
	private ModeloMascotas mascota;
	private ModeloUsuarios usuario;
	private Date fecha;
	private String motivo,informe;
	private boolean realizada;

	
	public ModeloCitas() {
		
	}

	public ModeloCitas(Integer id, ModeloMascotas mascota, ModeloUsuarios usuario, Date fecha, String motivo,
			String informe, boolean realizada) {
		super();
		this.id = id;
		this.mascota = mascota;
		this.usuario = usuario;
		this.fecha = fecha;
		this.motivo = motivo;
		this.informe = informe;
		this.realizada = realizada;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public ModeloMascotas getMascota() {
		return mascota;
	}

	public void setMascota(ModeloMascotas mascota) {
		this.mascota = mascota;
	}

	public ModeloUsuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(ModeloUsuarios usuario) {
		this.usuario = usuario;
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
