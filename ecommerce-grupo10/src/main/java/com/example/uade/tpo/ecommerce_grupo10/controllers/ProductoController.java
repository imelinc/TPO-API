package com.example.uade.tpo.ecommerce_grupo10.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.service.ProductoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    // Catalogo disponible, stock > 0
    @GetMapping
    public Page<Producto> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productoService.listarDisponibles(PageRequest.of(page, size));
    }

    // Buscar por titulo
    @GetMapping("/buscar")
    public Page<Producto> buscarPorTitulo(@RequestParam String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productoService.buscarPorTitulo(titulo, PageRequest.of(page, size));
    }

    // metodo para obtener un producto por ID
    @GetMapping("/{id}") 
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> result = productoService.get(id);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }

    // Filtrar por categoria
    @GetMapping("/categoria/{id}")
    public Page<Producto> porCategoria(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return productoService.buscarPorCategoria(id, PageRequest.of(page, size));
    }

    // Crear un producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto creado = productoService.save(producto);
        return ResponseEntity.ok(creado); // devuelve si el producto fue creado
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        // se verifica si existe el producto
        if (!productoService.get(id).isPresent()) {
            return ResponseEntity.notFound().build(); // si no existe, 404
        }
        // si existe, se actualiza
        producto.setId(id);
        productoService.update(producto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        // este if verifica si el producto existe
        if (!productoService.get(id).isPresent()) {
            return ResponseEntity.notFound().build(); // si no existe, retorna 404
        }
        // sino, elimina el producto
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
