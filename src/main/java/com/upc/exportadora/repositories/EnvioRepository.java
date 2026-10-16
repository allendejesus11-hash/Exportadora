package com.upc.exportadora.repositories;

import com.upc.exportadora.dtos.ReporteEnvioDTO;
import com.upc.exportadora.entities.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

	// HUB02: costo total de envios por producto y categoria (JPQL)
	@Query("SELECT new com.upc.exportadora.dtos.ReporteEnvioDTO(p.nombre, c.nombre, SUM(e.costo)) " +
			"FROM Envio e JOIN e.producto p JOIN e.categoria c " +
			"GROUP BY p.id, p.nombre, c.id, c.nombre " +
			"ORDER BY p.nombre")
	List<ReporteEnvioDTO> reporteCostoTotalPorProductoYCategoria();
}
