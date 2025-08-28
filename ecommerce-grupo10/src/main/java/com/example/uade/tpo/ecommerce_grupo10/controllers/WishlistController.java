package com.example.uade.tpo.ecommerce_grupo10.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.WishlistDTO;
import com.example.uade.tpo.ecommerce_grupo10.service.wishlist.WishlistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios/{usuarioId}/wishlist")
public class WishlistController {
    
    private final WishlistService wishlistService;

     // Obtener o crear la wishlist del usuario
    @PostMapping("/create-if-not-exists")
    public ResponseEntity<WishlistDTO> createIfNotExists(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(wishlistService.getOrCreateByUsuario(usuarioId));
    }

    // Obtener la wishlist
    @GetMapping
    public ResponseEntity<WishlistDTO> get(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(wishlistService.getByUsuario(usuarioId));
    }

    // Vaciar la wishlist
    @DeleteMapping("/items")
    public ResponseEntity<Void> clear(@PathVariable Long usuarioId) {
        wishlistService.clearByUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
