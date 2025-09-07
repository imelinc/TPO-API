package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.Categoria;
import com.example.uade.tpo.ecommerce_grupo10.entity.Producto;
import com.example.uade.tpo.ecommerce_grupo10.entity.Usuario;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.ImagenProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.DescuentoProductoDTO;
import com.example.uade.tpo.ecommerce_grupo10.service.descuentoProducto.DescuentoProductoService;

@Component
public class MapperProducto {

    @Autowired
    private DescuentoProductoService descuentoProductoService;

    @Autowired
    private MapperImagenProducto mapperImagenProducto;

    // metodo que convierte un Producto a ProductoDTO (sin descuentos)
    public ProductoDTO toDTO(Producto p) {
        if (p == null)
            return null;

        // Convertir las imágenes a DTOs
        List<ImagenProductoDTO> imagenesDTO = p.getImagenes() != null ? p.getImagenes().stream()
                .map(mapperImagenProducto::toDTO)
                .collect(Collectors.toList()) : List.of();

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
                .imagenes(imagenesDTO)
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
            // Usar el método Optional para evitar excepción cuando no hay descuento
            Optional<DescuentoProductoDTO> descuentoOpt = descuentoProductoService
                    .obtenerPorProductoOptional(p.getId());

            if (descuentoOpt.isPresent()) {
                DescuentoProductoDTO descuento = descuentoOpt.get();

                // Verificar si está activo, vigente y tiene porcentaje válido
                if (Boolean.TRUE.equals(descuento.getActivo()) &&
                        estaVigente(descuento) &&
                        descuento.getPorcentajeDescuento() != null &&
                        descuento.getPorcentajeDescuento() > 0) {

                    double porcentaje = descuento.getPorcentajeDescuento();
                    double montoDescuento = p.getPrecio() * (porcentaje / 100.0);
                    double precioConDescuento = p.getPrecio() - montoDescuento;

                    dto.setTieneDescuento(true);
                    dto.setPorcentajeDescuento(porcentaje);
                    dto.setMontoDescuento(montoDescuento);
                    dto.setPrecioConDescuento(precioConDescuento);
                }
            }
        } catch (Exception e) {
            // Cualquier otro error, no mostrar descuento
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
