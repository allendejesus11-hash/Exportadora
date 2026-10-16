package com.upc.exportadora.services;

import com.upc.exportadora.dtos.ReporteEnvioDTO;
import com.upc.exportadora.repositories.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvioService {
	@Autowired
	private EnvioRepository e2_envioRepository;

	// HUB02: costo total de envios por producto y categoria
	public List<ReporteEnvioDTO> reporteCostoTotal() {
		List<ReporteEnvioDTO> e2_reporte = e2_envioRepository.reporteCostoTotalPorProductoYCategoria();
		return e2_reporte;
	}
}
