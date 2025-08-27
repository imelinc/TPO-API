package com.example.uade.tpo.ecommerce_grupo10.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.uade.tpo.ecommerce_grupo10.controllers.requests.AddItemRequest;
import com.example.uade.tpo.ecommerce_grupo10.controllers.requests.UpdateCantidadRequest;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.CarritoDTO;
import com.example.uade.tpo.ecommerce_grupo10.service.carrito.CarritoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;

     // crear carrito si no existe y devolverlo
    @PostMapping("/usuario/{usuarioId}") // asociado a un usuario obviamente
    public ResponseEntity<CarritoDTO> crearSiNoExiste(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.crearSiNoExiste(usuarioId));
    }

    // Obtener carrito del usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoDTO> obtener(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerPorUsuario(usuarioId));
    }

    // Agregar item al carrito del usuario
    @PostMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<CarritoDTO> agregarItem(
            @PathVariable Long usuarioId,
            @RequestBody AddItemRequest body) { // aca usamos la request que creamos
        return ResponseEntity.ok(
                carritoService.agregarItem(usuarioId, body.getProductoId(), body.getCantidad())
        );
    }

    // Actualizar cantidad
    @PutMapping("/usuario/{usuarioId}/items/{productoId}")
    public ResponseEntity<CarritoDTO> actualizarCantidad(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId,
            @RequestBody UpdateCantidadRequest body) { // aca usamos la otra request que creamos
        return ResponseEntity.ok(
                carritoService.actualizarCantidad(usuarioId, productoId, body.getCantidad())
        );
    }

    // Eliminar item
    @DeleteMapping("/usuario/{usuarioId}/items/{productoId}")
    public ResponseEntity<CarritoDTO> eliminarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(
                carritoService.eliminarItem(usuarioId, productoId)
        );
    }

    // Vaciar carrito
    @DeleteMapping("/usuario/{usuarioId}/vaciar")
    public ResponseEntity<CarritoDTO> vaciar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.vaciar(usuarioId));
    }

}
