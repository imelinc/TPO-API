package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.Categoria;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.Usuario;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.DescuentoProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.exceptions.RecursoNoEncontrado;
import com.example.uade.tpo.ecommerce_grupo10.service.descuentoProducto.DescuentoProductoService;

@Component
public class MapperProducto {

    @Autowired
    private DescuentoProductoService descuentoProductoService;

    // metodo que convierte un Producto a ProductoDTO (sin descuentos)
    public ProductoDTO toDTO(Producto p) {
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
                .vendedorId(p.getVendedor() != null ? p.getVendedor().getId() : null)
                .vendedorNombre(p.getVendedor() != null ? p.getVendedor().getNombre() : null)
                .tieneDescuento(false)
                .build();
    }

    // metodo que convierte un Producto a ProductoDTO (con información de
    // descuentos)
    public ProductoDTO toDTOConDescuentos(Producto p) {
        if (p == null)
            return null;

        ProductoDTO dto = toDTO(p);

        try {
            // Intentar obtener descuento activo
            DescuentoProductoDTO descuento = descuentoProductoService.obtenerPorProducto(p.getId());

            // Verificar si está activo y vigente
            if (Boolean.TRUE.equals(descuento.getActivo()) && estaVigente(descuento)) {
                double porcentaje = descuento.getPorcentajeDescuento();
                double montoDescuento = p.getPrecio() * (porcentaje / 100.0);
                double precioConDescuento = p.getPrecio() - montoDescuento;

                dto.setTieneDescuento(true);
                dto.setPorcentajeDescuento(porcentaje);
                dto.setMontoDescuento(montoDescuento);
                dto.setPrecioConDescuento(precioConDescuento);
            }
        } catch (RecursoNoEncontrado e) {
            // No hay descuento, ya está configurado como false
        }

        return dto;
    }

    // Método auxiliar para verificar si un descuento está vigente
    private boolean estaVigente(DescuentoProductoDTO descuento) {
        Date ahora = new Date();
        return descuento.getFechaInicio() != null &&
                descuento.getFechaFin() != null &&
                !ahora.before(descuento.getFechaInicio()) &&
                !ahora.after(descuento.getFechaFin());
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

    public void updateEntityFromDto(ProductoDTO dto, Producto entity, Categoria categoria, Usuario vendedor) {
        updateEntityFromDto(dto, entity, categoria);
        if (vendedor != null)
            entity.setVendedor(vendedor);
    }

    public Producto toEntity(ProductoDTO dto, Categoria categoria) {
        Producto p = new Producto();
        updateEntityFromDto(dto, p, categoria);
        return p;
    }

    public Producto toEntity(ProductoDTO dto, Categoria categoria, Usuario vendedor) {
        Producto p = new Producto();
        updateEntityFromDto(dto, p, categoria, vendedor);
        return p;
    }
}
