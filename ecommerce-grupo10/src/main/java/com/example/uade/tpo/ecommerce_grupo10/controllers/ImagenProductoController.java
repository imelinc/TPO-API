package com.example.uade.tpo.ecommerce_grupo10.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ImagenProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.service.imagenProducto.ImagenProductoService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController @RequiredArgsConstructor
@RequestMapping("/productos/{productoId}/imagenes")
public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;

    // crear imagen 
    @PostMapping
     public ResponseEntity<ImagenProductoDTO> crear(
            @PathVariable Long productoId,
            @RequestBody UrlRequest body) { // aca se usa ese UrlRequest
        ImagenProductoDTO dto = imagenProductoService.agregarImagen(productoId, body.getUrl());
        return ResponseEntity.ok(dto);
    }
    
    // listar imagenes
    @GetMapping
    public ResponseEntity<List<ImagenProductoDTO>> listar(@PathVariable Long productoId) {
        return ResponseEntity.ok(imagenProductoService.listarPorProducto(productoId));
    }

    // una imagen puntual por producto e imagen
    @GetMapping("/{imagenId}")
    public ResponseEntity<ImagenProductoDTO> obtener(
            @PathVariable Long productoId,
            @PathVariable Long imagenId) {
        return ResponseEntity.ok(imagenProductoService.obtener(productoId, imagenId));
    }

    // actualizar URL de una imagen
    @PutMapping("/{imagenId}")
    public ResponseEntity<ImagenProductoDTO> actualizarUrl(
            @PathVariable Long productoId,
            @PathVariable Long imagenId,
            @RequestBody UrlRequest body) {
        ImagenProductoDTO dto = imagenProductoService.actualizarUrl(productoId, imagenId, body.getUrl());
        return ResponseEntity.ok(dto);
    }

    // eliminar una imagen
    @DeleteMapping("/{imagenId}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long productoId,
            @PathVariable Long imagenId) {
        imagenProductoService.eliminarImagen(productoId, imagenId);
        return ResponseEntity.noContent().build();
    }

    // esta clase representa el cuerpo de la solicitud para crear una nueva imagen
    // sirve para devolver un JSON solo con la url de la imagen
    // del tipo {"url": "..."}
    @Data
    public static class UrlRequest {
        private String url;
    }
}
