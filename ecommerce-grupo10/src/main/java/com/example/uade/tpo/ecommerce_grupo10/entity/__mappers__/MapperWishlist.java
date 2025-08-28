package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.WishlistDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.wishlist.Wishlist;

@Component
public class MapperWishlist {
    
    public WishlistDTO toDTO(Wishlist w) {
            if (w == null) return null;
            int count = (w.getItems() == null) ? 0 : w.getItems().size();
            Long usuarioId = (w.getUsuario() != null) ? w.getUsuario().getId() : null;

            return WishlistDTO.builder()
                    .id(w.getId())
                    .usuarioId(usuarioId)
                    .itemsTotales(count)
                    .build();
        }

}
