package com.example.uade.tpo.ecommerce_grupo10.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.DescuentoProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.service.descuentoProducto.DescuentoProductoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class DescuentoProductoController {

    private final DescuentoProductoService service;

    // endpoints anidados por producto

    @PostMapping("/productos/{productoId}/descuento")
    public ResponseEntity<DescuentoProductoDTO> crear(
            @PathVariable Long productoId,
            @RequestBody @Validated DescuentoProductoDTO body) {
        
        body.setProductoId(productoId);
        return ResponseEntity.ok(service.crear(productoId, body));
    }

    @GetMapping("/productos/{productoId}/descuento")
    public ResponseEntity<DescuentoProductoDTO> obtenerPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.obtenerPorProducto(productoId));
    }

    @PutMapping("/productos/{productoId}/descuento/{id}")
    public ResponseEntity<DescuentoProductoDTO> actualizar(
            @PathVariable Long productoId,
            @PathVariable Long id,
            @RequestBody @Validated DescuentoProductoDTO body) {
        
        body.setProductoId(productoId);
        return ResponseEntity.ok(service.actualizar(id, body));
    }

    @DeleteMapping("/productos/{productoId}/descuento/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long productoId, @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // endpoints por id
    @GetMapping("/descuentos/{id}")
    public ResponseEntity<DescuentoProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PatchMapping("/descuentos/{id}/activar")
    public ResponseEntity<DescuentoProductoDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(service.activar(id));
    }

    @PatchMapping("/descuentos/{id}/desactivar")
    public ResponseEntity<DescuentoProductoDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }

    @GetMapping("/descuentos")
    public ResponseEntity<Page<DescuentoProductoDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }
}
