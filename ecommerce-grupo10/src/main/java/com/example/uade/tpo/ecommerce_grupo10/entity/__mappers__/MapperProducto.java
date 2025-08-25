package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ProductoDTO;

public class MapperProducto {
    private MapperProducto() {
        // Constructor privado para evitar instanciación
    }

    // metodo que convierte un Producto a ProductoDTO
    public static ProductoDTO toDTO(Producto p){
        Long categoriaId = null; //! hay que cambiar esto cuando tengamos categoria

        String imagenURL = null; //! hay que ver si las imagenes van aca

        return new ProductoDTO(
            p.getId(),
            p.getTitulo(),
            p.getDescripcion(),
            p.getPrecio(),
            imagenURL,
            categoriaId,
            p.getStock()
        );
    }
}
