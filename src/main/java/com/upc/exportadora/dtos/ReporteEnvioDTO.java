package com.upc.exportadora.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEnvioDTO {
	private String producto;
	private String categoria;

	@JsonProperty("costo_total")
	private Double costoTotal;
}
