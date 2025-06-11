package com.cojali.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "valoraciones")
public class Valoracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Integer puntuacion;

	@Column(length = 500)
	private String mensaje;

	private LocalDateTime fecha;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	// Getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPuntuacion() {
	    return puntuacion;
	}


	public void setPuntuacion(Integer puntuacion) {
	    this.puntuacion = puntuacion;
	}


	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
