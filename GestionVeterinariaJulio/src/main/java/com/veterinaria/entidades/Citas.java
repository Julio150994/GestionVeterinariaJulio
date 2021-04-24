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
@Table(name="citas")
public class Citas {
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idMascota")
	private Mascotas idMascota;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idVeterinario")
	private Usuarios idVeterinario;

	@Column(name="fecha",nullable=false)
	private Date fecha;
	
	@Column(name="motivo",nullable=false,length=100)
	private String motivo;
	
	@Column(name="informe",nullable=false,length=100)
	private String informe;
	
	@Column(name="realizada",nullable=false)
	private boolean realizada;
	
	
	public Citas() {
		
	}	

	public Citas(int id, Mascotas idMascota, Usuarios idVeterinario, Date fecha, String motivo, String informe,
			boolean realizada) {
		super();
		this.id = id;
		this.idMascota = idMascota;
		this.idVeterinario = idVeterinario;
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

	public Mascotas getIdMascota() {
		return idMascota;
	}

	public void setIdMascota(Mascotas idMascota) {
		this.idMascota = idMascota;
	}	

	public Usuarios getIdVeterinario() {
		return idVeterinario;
	}

	public void setIdVeterinario(Usuarios idVeterinario) {
		this.idVeterinario = idVeterinario;
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
