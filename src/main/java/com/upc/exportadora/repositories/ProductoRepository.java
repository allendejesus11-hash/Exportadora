package com.upc.exportadora.repositories;

import com.upc.exportadora.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	boolean existsByCodigo(String codigo);
}
