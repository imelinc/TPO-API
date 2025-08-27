package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.CarritoDTO;
import com.example.uade.tpo.ecommerce_grupo10.entity.cart.Carrito;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MapperCarrito {
    
    private final MapperItemCarrito mapperItemCarrito;

    public CarritoDTO toDTO(Carrito c) {
        if (c == null) return null;
        var itemsDTO = c.getItems().stream()
                .map(mapperItemCarrito::toDTO)
                .collect(Collectors.toList());
        double total = itemsDTO.stream()
                .mapToDouble(i -> i.getSubtotal()) // esto lo que hace es obtener el subtotal de cada item
                .sum();

        return CarritoDTO.builder()
                .id(c.getId())
                .usuarioId(c.getUsuario() != null ? c.getUsuario().getId() : null)
                .items(itemsDTO)
                .total(total)
                .build();
    }
}
