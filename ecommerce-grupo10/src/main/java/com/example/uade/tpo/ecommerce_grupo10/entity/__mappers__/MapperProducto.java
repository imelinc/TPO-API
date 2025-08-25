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

    // este metodo actualiza un Producto a partir de un ProductoDTO
    // sirve para mantener la entidad actualizada con los cambios del DTO
    public static void updateEntityFromDTO(Producto p, ProductoDTO dto) {
        if (dto.getTitulo() != null)
            p.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null)
            p.setDescripcion(dto.getDescripcion());
        if (dto.getPrecio() != null)
            p.setPrecio(dto.getPrecio());
        if (dto.getStock() != null)
            p.setStock(dto.getStock());
        // categoria e imagen mas adelante
    }
}
