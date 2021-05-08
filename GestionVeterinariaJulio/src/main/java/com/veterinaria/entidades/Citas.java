package com.veterinaria.entidades;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="citas")
public class Citas {
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="idMascota",nullable=false)
	private Mascotas mascota;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="idVeterinario",nullable=false)
	private Usuarios veterinario;
	
	@NotNull(message="Debe introducir una fecha para su cita")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="fecha",nullable=false)
	private Date fecha;
	
	@NotNull(message="Debe escribir un motivo para su cita")
	@Size(min=1,max=100,message="Su motivo no debe tener más de 100 caracteres")
	@Pattern(regexp="^[A-Z]{1}[a-z].+$",message="El motivo de la cita debe empezar por mayúsculas")
	@Column(name="motivo",nullable=false,length=100)
	private String motivo;
	
	@NotNull(message="Debe redactar un informe para su cita")
	@Size(min=1,max=100,message="Su informe no debe tener más de 100 caracteres")
	@Pattern(regexp="^[A-Z]{1}[a-z].+$",message="El informe de la cita debe empezar por mayúsculas")
	@Column(name="informe",nullable=false,length=100)
	private String informe;
	
	@Column(name="realizada",nullable=false)
	private boolean realizada;
	
	
	public Citas() {
		
	}

	public Citas(int id, Mascotas mascota, Usuarios veterinario, @NotNull(message = "Debe introducir una fecha para su cita") Date fecha,
			@NotNull(message = "Debe escribir un motivo para su cita") @Size(min = 1, max = 100, message = "Su motivo no debe tener más de 100 caracteres") @Pattern(regexp = "^[A-Z]{1}[a-z].+$", message = "El motivo de la cita debe empezar por mayúsculas") String motivo,
			@NotNull(message = "Debe redactar un informe para su cita") @Size(min = 1, max = 100, message = "Su informe no debe tener más de 100 caracteres") @Pattern(regexp = "^[A-Z]{1}[a-z].+$", message = "El informe de la cita debe empezar por mayúsculas") String informe,
			boolean realizada) {
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

	public Mascotas getMascota() {
		return mascota;
	}

	public void setMascota(Mascotas mascota) {
		this.mascota = mascota;
	}	

	public Usuarios getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Usuarios veterinario) {
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
