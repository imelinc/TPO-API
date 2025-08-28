package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItemDTO {
    private Long id;
    private Long wishlistId;
    private Long productoId;
    private String productoTitulo;
    private LocalDateTime agregadoA;
}
