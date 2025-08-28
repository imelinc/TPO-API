package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.WishlistItemDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.wishlist.WishlistItem;

@Component
public class MapperWishlistItem {
    
    public WishlistItemDTO toDTO(WishlistItem it) {
        if (it == null) return null;

        return WishlistItemDTO.builder()
                .id(it.getId())
                .wishlistId(it.getWishlist() != null ? it.getWishlist().getId() : null)
                .productoId(it.getProducto() != null ? it.getProducto().getId() : null)
                .productoTitulo(it.getProducto() != null ? it.getProducto().getTitulo() : null)
                .agregadoA(it.getAgregadoA())
                .build();
    }

}
