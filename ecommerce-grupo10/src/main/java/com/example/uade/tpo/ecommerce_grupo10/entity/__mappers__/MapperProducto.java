package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.Categoria;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ProductoDTO;

@Component
public class MapperProducto {
    private MapperProducto() {
        // Constructor privado para evitar instanciación
    }

    // metodo que convierte un Producto a ProductoDTO
    public ProductoDTO toDTO(Producto p){
        if (p == null)
            return null;
        return ProductoDTO.builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .imagenUrl(p.getImagenUrl())
                .categoriaId(p.getCategoria() != null ? p.getCategoria().getId() : null)
                .categoriaNombre(p.getCategoria() != null ? p.getCategoria().getNombre() : null)
                .build();
    }

    public void updateEntityFromDto(ProductoDTO dto, Producto entity, Categoria categoria) {
        if (dto.getTitulo() != null)
            entity.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null)
            entity.setDescripcion(dto.getDescripcion());
        if (dto.getPrecio() != null)
            entity.setPrecio(dto.getPrecio());
        if (dto.getStock() != null)
            entity.setStock(dto.getStock());
        if (dto.getImagenUrl() != null)
            entity.setImagenUrl(dto.getImagenUrl());
        if (categoria != null)
            entity.setCategoria(categoria);
    }

    public Producto toEntity(ProductoDTO dto, Categoria categoria) {
        Producto p = new Producto();
        updateEntityFromDto(dto, p, categoria);
        return p;
    }
}
