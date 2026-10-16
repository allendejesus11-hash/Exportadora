package com.upc.exportadora.controllers;

import com.upc.exportadora.dtos.ProductoDTO;
import com.upc.exportadora.dtos.ReporteEnvioDTO;
import com.upc.exportadora.services.EnvioService;
import com.upc.exportadora.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private EnvioService envioService;

    // HUB01 - solo BROKER
    @PostMapping("/registers")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<ProductoDTO> registers(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoRegistrado = productoService.registrar(productoDTO);
        return ResponseEntity.ok(productoRegistrado);
    }

    // HUB02 - solo CONTADOR
    @GetMapping("/reports")
    @PreAuthorize("hasRole('CONTADOR')")
    public ResponseEntity<List<ReporteEnvioDTO>> reports() {
        return ResponseEntity.ok(envioService.reporteCostoTotal());
    }
}
