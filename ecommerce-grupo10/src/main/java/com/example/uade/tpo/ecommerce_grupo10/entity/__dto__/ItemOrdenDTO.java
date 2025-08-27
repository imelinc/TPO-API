package com.example.uade.tpo.ecommerce_grupo10.entity.__dto__;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrdenDTO {
    private Long id;
    private Long idProducto;
    private String titulo;
    private Integer cantidad;
    private Double precioUnitario;
    private Double descuentoAplicado;

    private Long ordenId; // referencia a Orden
    private Double subtotal; // (precioUnitario - descuentoAplicado) * cantidad 
}
