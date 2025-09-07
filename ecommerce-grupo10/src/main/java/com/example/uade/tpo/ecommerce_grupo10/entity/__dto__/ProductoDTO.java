package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private Long categoriaId;
    private String categoriaNombre;
    private Integer stock;
    private Long vendedorId;
    private String vendedorNombre;

    // Campos para descuentos
    private Boolean tieneDescuento;
    private Double porcentajeDescuento;
    private Double precioConDescuento;
    private Double montoDescuento;

}
