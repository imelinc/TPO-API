package com.example.uade.tpo.ecommerce_grupo10.entity.__mappers__;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.uade.tpo.ecommerce_grupo10.entity.ItemOrden;
import com.example.uade.tpo.ecommerce_grupo10.entity.Orden;
import com.example.uade.tpo.ecommerce_grupo10.entity.Usuario;
import com.example.uade.tpo.ecommerce_grupo10.entity.__dto__.OrdenDTO;

@Component
public class MapperOrden {
    
    public OrdenDTO toDTO(Orden o) {
        if (o == null) return null;

        Set<Long> itemIds = (o.getItems() == null)
                ? Collections.emptySet()
                : o.getItems().stream()
                    .map(ItemOrden::getId)
                    .collect(Collectors.toSet());

        Long usuarioId = (o.getUsuario() != null) ? o.getUsuario().getId() : null;

        return OrdenDTO.builder()
                .id(o.getId())
                .fechaCreacion(o.getFechaCreacion())
                .estado(o.getEstado())
                .total(o.getTotal())
                .usuarioId(usuarioId)
                .itemIds(itemIds)
                .build();
    }

    
    public Orden toEntity(OrdenDTO dto, Usuario usuario, Set<ItemOrden> items) {
        if (dto == null) return null;

        Orden o = new Orden();
        o.setId(dto.getId());
        o.setFechaCreacion(dto.getFechaCreacion());
        o.setEstado(dto.getEstado());
        o.setTotal(dto.getTotal());
        o.setUsuario(usuario);
        o.setItems(items != null ? items : Collections.emptySet());
        return o;
    }
}
