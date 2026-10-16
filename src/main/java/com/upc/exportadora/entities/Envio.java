package com.upc.exportadora.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "envios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Envio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private LocalDate fecha;

	@Column(nullable = false)
	private double costo;

	private String destino;

	// Unidireccional: el envio conoce a sus "padres" (Producto 1-N Envio, Categoria 1-N Envio)
	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
}
