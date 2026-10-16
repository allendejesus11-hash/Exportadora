package com.upc.exportadora.services;

import com.upc.exportadora.dtos.ProductoDTO;
import com.upc.exportadora.entities.Producto;
import com.upc.exportadora.repositories.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductoService {
	@Autowired
	private ProductoRepository e2_productoRepository;
	@Autowired
	private ModelMapper e2_modelMapper;

	// HUB01: registrar producto de exportacion
	public ProductoDTO registrar(ProductoDTO e2_productoDTO) {
		if (e2_productoRepository.existsByCodigo(e2_productoDTO.getCodigo())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"El producto '" + e2_productoDTO.getNombre() + "' con codigo '"
							+ e2_productoDTO.getCodigo() + "' ya se encuentra registrado");
		}
		Producto e2_producto = e2_modelMapper.map(e2_productoDTO, Producto.class);
		e2_producto.setId(null);
		Producto e2_guardado = e2_productoRepository.save(e2_producto);
		return e2_modelMapper.map(e2_guardado, ProductoDTO.class);
	}
}
