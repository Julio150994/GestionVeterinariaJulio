package com.veterinaria.modelos;

import java.sql.Date;


public class ModeloCitas {
	private int id;
	private ModeloMascotas mascota;
	private ModeloUsuarios veterinario;
	private Date fecha;
	private String motivo,informe;
	private boolean realizada;

	
	public ModeloCitas() {
		
	}	

	public ModeloCitas(int id, ModeloMascotas mascota, ModeloUsuarios veterinario, Date fecha, String motivo,
			String informe, boolean realizada) {
		super();
		this.id = id;
		this.mascota = mascota;
		this.veterinario = veterinario;
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

	public ModeloMascotas getMascota() {
		return mascota;
	}

	public void setMascota(ModeloMascotas mascota) {
		this.mascota = mascota;
	}	

	public ModeloUsuarios getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(ModeloUsuarios veterinario) {
		this.veterinario = veterinario;
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
