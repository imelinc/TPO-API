package com.example.uade.tpo.ecommerce_grupo10.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__.MapperProducto;
import com.example.uade.tpo.ecommerce_grupo10.service.producto.ProductoService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController @RequiredArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final MapperProducto mapperProducto;

    // Catálogo disponible (stock > 0)
    @GetMapping
    public List<ProductoDTO> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Producto> result = productoService.listarDisponibles(PageRequest.of(page, size));
        return result.getContent().stream()
                .map(mapperProducto::toDTO)
                .toList();
    }

    // Buscar por titulo (stock > 0)
    @GetMapping("/buscar")
    public List<ProductoDTO> buscarPorTitulo(
            @RequestParam String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Producto> result = productoService.buscarPorTitulo(titulo, PageRequest.of(page, size));
        return result.getContent().stream()
                .map(mapperProducto::toDTO)
                .toList();
    }

    // Obtener detalle por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        Producto p = productoService.get(id); // lanza 404 si no existe
        return ResponseEntity.ok(mapperProducto.toDTO(p));
    }

    // Filtrar por categoria
    @GetMapping("/categoria/{id}")
    public List<ProductoDTO> porCategoria(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Page<Producto> result = productoService.buscarPorCategoria(id, PageRequest.of(page, size));
        return result.getContent().stream()
                .map(mapperProducto::toDTO)
                .toList();
    }

    // Crear
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto) {
        var creado = productoService.save(dto);
        return ResponseEntity.ok(mapperProducto.toDTO(creado));
    }

    /// Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        var actualizado = productoService.update(id, dto);
        return ResponseEntity.ok(mapperProducto.toDTO(actualizado));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
