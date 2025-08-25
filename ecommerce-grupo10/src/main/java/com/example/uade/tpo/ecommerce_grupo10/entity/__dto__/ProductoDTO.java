package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private Long categoriaId;
    private String categoriaNombre;
    private Integer stock;
    
}
