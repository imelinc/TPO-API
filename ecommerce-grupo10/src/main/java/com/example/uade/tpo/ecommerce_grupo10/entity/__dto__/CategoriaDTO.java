package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder 
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nombre;
}
