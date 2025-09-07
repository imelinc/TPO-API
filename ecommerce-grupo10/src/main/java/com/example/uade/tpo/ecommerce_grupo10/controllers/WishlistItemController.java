package com.example.uade.tpo.ecommerce_grupo10.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.WishlistItemDTO;
import com.example.uade.tpo.ecommerce_grupo10.service.wishlistItem.WishlistItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios/{usuarioId}/wishlist/items")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,
        RequestMethod.PATCH })
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    @GetMapping("/debug-auth") // endpoint para debug de autenticación
    public ResponseEntity<String> debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String debugInfo = "Usuario: " + (auth != null ? auth.getName() : "null") +
                ", Authorities: " + (auth != null ? auth.getAuthorities() : "null");
        System.out.println("DEBUG: " + debugInfo);
        return ResponseEntity.ok(debugInfo);
    }

    @GetMapping // obtener todos los items de la wishlist
    public ResponseEntity<List<WishlistItemDTO>> list(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(wishlistItemService.listItems(usuarioId));
    }

    @PostMapping("/{productoId}") // agregar un item a la wishlist
    public ResponseEntity<WishlistItemDTO> add(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(wishlistItemService.addItem(usuarioId, productoId));
    }

    @DeleteMapping("/{productoId}") // eliminar un item de la wishlist
    public ResponseEntity<Void> remove(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        System.out.println("DEBUG: DELETE wishlist item - Usuario: " + usuarioId + ", Producto: " + productoId);
        wishlistItemService.removeItem(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productoId}/exists") // verificar si un item existe en la wishlist
    public ResponseEntity<Boolean> exists(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(wishlistItemService.exists(usuarioId, productoId));
    }
}
