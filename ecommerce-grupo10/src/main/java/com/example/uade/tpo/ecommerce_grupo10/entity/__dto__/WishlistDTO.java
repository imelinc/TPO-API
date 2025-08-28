package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WishlistDTO {
    private Long id;
    private Long usuarioId;
    private int itemsTotales;
}
